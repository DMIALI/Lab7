package ServerModules;

import CommandData.ClientData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Handler {
    public static ClientData handle(DatagramPacket datagramPacket) throws IOException {
        InetAddress inetAddress = datagramPacket.getAddress();

        InputStream inputStream = new ByteArrayInputStream(datagramPacket.getData());
        TypeReference<ClientData> mapType = new TypeReference<>() {};
        ClientData clientData = (new ObjectMapper()).readValue(inputStream, mapType);
        return clientData;
    }
}
