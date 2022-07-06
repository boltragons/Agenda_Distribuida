package server;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class ServerConnection extends Thread {
    private DatagramPacket packet;
    private DatagramSocket socket;
    private Despachante despachante;

    public ServerConnection(DatagramSocket socket) {
        this.socket = socket;
        this.despachante = new Despachante();
    }

    public byte[] empacotaMensagem(String objectRef, String method, String args) {
		JSONObject mensagem = new JSONObject();
		mensagem.put("type", 1);
        mensagem.put("id", 0);
		mensagem.put("objReference", objectRef);
		mensagem.put("methodId", method);
		mensagem.put("arguments", args);

        return mensagem.toString().getBytes();
    }
    
    public static JSONObject desempacotaMensagem(byte[] resposta) {
		String resposta_string = new String(resposta, StandardCharsets.UTF_8);
        return new JSONObject(resposta_string);
    }

    public void run() {
        while(true) {
            byte[] m = receive();

            // =============== Mensagem Recebida ====================
            System.out.println("RECEBIDA:" + (new String(m)));

            JSONObject mensagem = desempacotaMensagem(m);
            String resultado = despachante.selecionaEsqueleto(mensagem);

            byte[] buf = empacotaMensagem((String) mensagem.get("objReference"),
                                          (String) mensagem.get("methodId"),
                                          resultado);
            // ============== Mensagem Enviada ==================
            System.out.println("ENVIADA: " + (new String(buf)));
            send(buf);
        }
    }

    public byte[] receive(){
        byte[] buf = new byte[1024];
        packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Limapr o lixo
        byte[] aux = new byte[packet.getLength()];
        for (int i = 0; i < packet.getLength(); i++) {
            aux[i] = packet.getData()[i];
        }
        return aux;
    }

    public void send(byte[] buf) {
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        
        try {
            socket.send(packet);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}