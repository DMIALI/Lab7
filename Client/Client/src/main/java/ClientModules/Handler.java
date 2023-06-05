package ClientModules;

import ManagerOfCommands.CommandData.ServerData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;

public class Handler {
    byte[] message;
    int messagesCounter;
    public ServerData handle(DatagramPacket datagramPacket, int chunkSize) throws IOException {
        byte[] data = datagramPacket.getData();
        int offset = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        datagramPacket.setData(Arrays.copyOfRange(data, 4, 4 + chunkSize));
        message = new byte[(-1)*offset];
        messagesCounter = (-1)*offset/chunkSize - 1;
        System.arraycopy(datagramPacket.getData(),0,message,0,chunkSize);
        return createObject();
    }
    public ServerData add(DatagramPacket datagramPacket, int chunkSize) throws IOException {
        byte[] data = datagramPacket.getData();
        int offset = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        datagramPacket.setData(Arrays.copyOfRange(data, 4, 4 + chunkSize));
        System.arraycopy(datagramPacket.getData(),0,message,offset,chunkSize);
        messagesCounter--;
        return createObject();

    }
    private ServerData createObject() throws IOException {
        if (messagesCounter == 0){
            InputStream inputStream = new ByteArrayInputStream(message);
            TypeReference<ServerData> mapType = new TypeReference<>() {};
            return (new ObjectMapper()).readValue(inputStream, mapType);
        }
        return null;
    }
}
