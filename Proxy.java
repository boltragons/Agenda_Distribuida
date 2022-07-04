import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Proxy{
	private int requestId = 0;
	private UDPClient client;
	// O ideal seria solicitar os dados de conexao ao cliente
	// através de um nome de domínio (ex: www.ufc.br)
	
	Proxy() throws SocketException, UnknownHostException{
		client = new UDPClient();
	}

	public String doOperation(String msg) throws IOException {

		byte[] data = empacotaMensagem(msg);

		// envio
		client.sendEcho(data);

		// recebimento
		String resposta = desempacotaMensagem(client.receiveEcho(data));

		return resposta;

	}

	public void finaliza() {
		client.close();
	}

    public byte[] empacotaMensagem(String objectRef, String method, ByteString args) {
        Message.Builder message = Message.newBuilder()
	    		.setType(0)
	    		.setId(requestId++)
	    		.setObjReference(objectRef)
	    		.setMethodId(method)
	    		.setArgs(args);
        return message.build().toByteArray();
    }

    public static Message desempacotaMenssagem(byte[] resposta) {
    	Message message = null;
		try {
			message = Message.parseFrom(resposta);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException client.Proxy: " + e.getMessage());
		}
        return message;
    }

}