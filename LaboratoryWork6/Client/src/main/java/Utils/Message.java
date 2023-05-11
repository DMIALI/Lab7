package Utils;

import ManagerOfCommands.CommandData.ClientData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Message {
    public StringBuilder getData(DatagramSocket datagramSocket){
        try{
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.setSoTimeout(100_000);
            datagramSocket.receive(datagramPacket);
            String serialisedData = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public void serialize (ClientData clientData) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(clientData);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
    }
}
