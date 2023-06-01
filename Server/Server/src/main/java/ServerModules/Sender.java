package ServerModules;

import CommandData.ServerData;
import Utils.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    public static void send(ServerData serverData, Client client, DatagramSocket datagramSocket, int chunkSize, Logger logger) {
        InetAddress clientInetAddress = client.getInetAddress();
        int clientPort = client.getPort();
        byte[] dataBuffer = serialize(serverData, logger).getBytes();
        byte[][] sendingDataBuffer = splitToChunks(dataBuffer, chunkSize);
        for (byte[] chunk : sendingDataBuffer){
            sendDatagram(datagramSocket, new DatagramPacket(chunk, chunkSize, clientInetAddress, clientPort), logger);
        }
        logger.info("Ответ на запрос номер " + serverData.counter() + " отправлен");
        logger.debug(serverData.toString());
    }
    private static String serialize(ServerData serverData, Logger logger){
        try {
            return (new ObjectMapper()).writeValueAsString(serverData);
        }
        catch (IOException e){
            logger.error(e);
        }
        return null;
    }
    private static byte[][] splitToChunks(byte[] dataBuffer, int chunkSize){
        int chunksNumber = (int) Math.ceil(dataBuffer.length/chunkSize);
        byte[][] result = new byte[chunksNumber][chunkSize+4];
        int counter = -chunksNumber;
        for (byte[] chunk : result){
            counter++;
            chunk[0] = (byte) (counter >> 24);
            chunk[1] = (byte) (counter >> 16);
            chunk[2] = (byte) (counter >> 8);
            chunk[3] = (byte) (counter);
            for (int i = 0; i < chunkSize; i++){
                chunk[4+i] = dataBuffer[i+chunkSize*(chunksNumber+counter-1)];
            }
        }
        return result;
    }
    private static void sendDatagram(DatagramSocket datagramSocket, DatagramPacket datagramPacket, Logger logger){
        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
