import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] arg){
        try {
            DatagramSocket ds = new DatagramSocket(2222);

            while (true) {
                DatagramPacket pack = new DatagramPacket(new byte[5], 5);
                ds.receive(pack);
                System.out.println(new String(pack.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
