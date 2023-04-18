import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    public static void main(String[] args){
        SendMessage sender = new SendMessage("se.ifmo.ru", 2222);
        String message = "Hello";
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sender.sendMessage(message);
            }
        }, 1000, 1000);
    }
}
