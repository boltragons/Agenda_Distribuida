package client;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import contato.Contato;

public class AgendaProxy{

	private int requestId = 0;

	private UDPClient client;

	// O ideal seria solicitar os dados de conexao ao cliente
	// através de um nome de domínio (ex: www.ufc.br)
	
	public AgendaProxy() throws SocketException, UnknownHostException{
		client = new UDPClient();
	}

	public boolean adicionarContato(Contato contato) {
		JSONObject contatoJson = new JSONObject(contato.toString());
		boolean resultado = false;
		try {
			resultado = Boolean.parseBoolean(doOperation("Agenda", "adicionarContato", contatoJson.toString()));
		}
		catch (IOException e) {
			//
		}
		return resultado;
	}

	public String listarContatos() {

		try {
			return doOperation("Agenda", "listarContatos", "");
		}
		catch (IOException e) {
			return "Erro!";
		}
	}

	public String doOperation(String objectRef, String method, String args) throws IOException {
		byte[] mensagem = empacotaMensagem(objectRef, method, args);

		// ============== Mensagem Enviada ==================
		System.out.println("ENVIADA: " + (new String(mensagem)));
		// envio
		client.sendEcho(mensagem);
		// recebimento
		byte[] serverMessage = client.receiveEcho();
		JSONObject obj = desempacotaMensagem(serverMessage);

		// =============== Mensagem Recebida ====================
		System.out.println("RECEBIDA:" + obj.toString());

		return obj.get("arguments").toString();
	}

	public void finaliza() {
		client.close();
	}

    	public byte[] empacotaMensagem(String objectRef, String method, String args) {
		JSONObject mensagem = new JSONObject();
		mensagem.put("type", 0);
		mensagem.put("id", requestId++);
		mensagem.put("objReference", objectRef);
		mensagem.put("methodId", method);
		mensagem.put("arguments", args);

        	return mensagem.toString().getBytes();
    }

	public static JSONObject desempacotaMensagem(byte[] resposta) {
		String resposta_string = new String(resposta, StandardCharsets.UTF_8);
		return new JSONObject(resposta_string);
    }

}