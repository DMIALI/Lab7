package ServerModules;

import CommandData.ClientData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.util.Arrays;

public class Handler {
    byte[] message;
    int messagesCounter;
    public synchronized ClientData handle(DatagramPacket datagramPacket, int chunkSize) throws IOException {
        byte[] data = datagramPacket.getData();
        int offset = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        datagramPacket.setData(Arrays.copyOfRange(data, 4, 4 + chunkSize));
        message = new byte[(-1)*offset];
        messagesCounter = (-1)*offset/chunkSize - 1;
        System.arraycopy(datagramPacket.getData(),0,message,0,chunkSize);
        return createObject();
    }
    public synchronized ClientData add(DatagramPacket datagramPacket, int chunkSize) throws IOException {
        byte[] data = datagramPacket.getData();
        int offset = ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8 ) | ((data[3] & 0xFF));
        datagramPacket.setData(Arrays.copyOfRange(data, 4, 4 + chunkSize));
        System.arraycopy(datagramPacket.getData(),0,message,offset,chunkSize);
        messagesCounter--;
        return createObject();

    }
    private synchronized ClientData createObject() throws IOException {
        if (messagesCounter == 0){
            //System.out.println(Arrays.toString(message));
            InputStream inputStream = new ByteArrayInputStream(message);
            TypeReference<ClientData> mapType = new TypeReference<>() {};
            return (new ObjectMapper()).readValue(inputStream, mapType);
        }
        return null;
    }
}
