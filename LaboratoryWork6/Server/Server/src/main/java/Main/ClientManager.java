package Main;

import Utils.Client;
import lombok.Getter;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.stream.Stream;

public class ClientManager {
    @Getter
    private LinkedList <Client> clients;

    public ClientManager() {
        clients = new LinkedList();
    }
    public Client getClient(InetAddress inetAddress, int port, long datagramCounter){
        LinkedList <Client> result = new LinkedList();
        clients.stream()
                .filter(x-> x.getInetAddress().equals(inetAddress))
                .filter(x-> x.getPort()==port)
                .forEach(result::add);
        if (result.size() == 0){
            System.out.println("New client!");
            Client newClient = new Client(inetAddress, port, datagramCounter);
            addClient(newClient);
            return newClient;
        }
        return result.getFirst();
    }
    public void addClient(Client client){
        clients.add(client);
    }

    public void delClient(Client client){
        clients.remove(client);
    }
}
