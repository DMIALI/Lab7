package ClientModules;

import ManagerOfCommands.CommandData.ClientData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Sender {
    public static void send(ClientData clientData, DatagramSocket datagramSocket, int chunkSize) throws IOException {
        byte[] dataBuffer = serialize(clientData).getBytes();
        splitToChunksAndSend(dataBuffer, chunkSize, datagramSocket);
    }
    private static String serialize(ClientData clientData) throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(clientData);
    }
    private static void splitToChunksAndSend(byte[] dataBuffer, int chunkSize, DatagramSocket datagramSocket) throws IOException {
        //System.out.println(Arrays.toString(dataBuffer));
        int len = (dataBuffer.length);
        int chunksNumber = (int) Math.ceil(((float) len) / chunkSize);
        for (int i = 0; i < chunksNumber; i++) {
            byte[] chunk = new byte[chunkSize + 4];
            int counter = chunkSize * i;
            if (i == 0) {
                counter = -chunkSize * chunksNumber;
            }
            chunk[0] = (byte) (counter >> 24);
            chunk[1] = (byte) (counter >> 16);
            chunk[2] = (byte) (counter >> 8);
            chunk[3] = (byte) (counter);
            System.arraycopy(dataBuffer, chunkSize*i, chunk, 4, i == chunksNumber -1 ? len - chunkSize*i : chunkSize);
            sendDatagram(datagramSocket, new DatagramPacket(chunk, chunkSize+4));
        }
    }
    private static void sendDatagram(DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws IOException {
        datagramSocket.send(datagramPacket);
    }
}

