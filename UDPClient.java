import java.io.IOException;
import java.net.*;

public class UDPClient{
    private DatagramSocket socket;
    private InetAddress address;

	//construtor
    public UDPClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public void sendEcho(byte[] buf) throws IOException {
		//enviou
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
		socket.send(packet);
    }

    public byte[] receiveEcho(byte[] buf) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return packet.getData();
    }

    public void close() {
        socket.close();
    }

    /*public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        try {
            UDPClient cliente = new UDPClient();
            String msg = "";

        
            do {
                
                msg = input.nextLine();
                msg = cliente.sendEcho(msg);
                System.out.println(msg);

            } while(!msg.equals("end"));
        
            cliente.close();
            input.close();

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }*/

}