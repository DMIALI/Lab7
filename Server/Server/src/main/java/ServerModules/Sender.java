package ServerModules;

import CommandData.ServerData;
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
        splitToChunksAndSend(dataBuffer, chunkSize, clientInetAddress, clientPort, logger, datagramSocket);
        /*byte[][] sendingDataBuffer = splitToChunksAndSend(dataBuffer, chunkSize);
        for (byte[] chunk : sendingDataBuffer){
            sendDatagram(datagramSocket, new DatagramPacket(chunk, chunkSize, clientInetAddress, clientPort), logger);
        }*/
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
    private static void splitToChunksAndSend(byte[] dataBuffer, int chunkSize, InetAddress clientInetAddress, int clientPort, Logger logger, DatagramSocket datagramSocket){
        int len = (dataBuffer.length);
        int chunksNumber = (int) Math.ceil(((float)len)/chunkSize);
        for (int i = 0; i < chunksNumber; i++){
            byte[] chunk = new byte[chunkSize + 4];
            int counter = chunkSize*i;
            if (i==0){
                counter = -chunkSize*chunksNumber;
            }
            chunk[0] = (byte) (counter >> 24);
            chunk[1] = (byte) (counter >> 16);
            chunk[2] = (byte) (counter >> 8);
            chunk[3] = (byte) (counter);
            System.arraycopy(dataBuffer, chunkSize*i, chunk, 4, i == chunksNumber-1 ? len - chunkSize*i : chunkSize);
            sendDatagram(datagramSocket, new DatagramPacket(chunk, chunkSize + 4, clientInetAddress, clientPort), logger);
            logger.trace("Отправлен чанк номер " + (i+1) + " из "+ (chunksNumber) +" чанков");
        }
        /*int len = (dataBuffer.length);
        int chunksNumber = (int) Math.ceil(((float)len)/chunkSize);
        for (int i = 0; i < chunksNumber; i++){
            byte[] chunk = new byte[chunkSize];
            System.arraycopy(dataBuffer, chunkSize*i, chunk, 0, len);
            if (i==0){
                sendDatagram(datagramSocket, new DatagramPacket(chunk, -chunkSize*chunksNumber, chunkSize, clientInetAddress, clientPort), logger);
            }
            else{
                sendDatagram(datagramSocket, new DatagramPacket(chunk, chunkSize*i, chunkSize, clientInetAddress, clientPort), logger);
            }
        }*/
    }
    private static void sendDatagram(DatagramSocket datagramSocket, DatagramPacket datagramPacket, Logger logger){
        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
