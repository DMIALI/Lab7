package Main;

import Utils.Client;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.stream.Stream;

public class ClientManager {
    private LinkedList <Client> clients;

    public ClientManager() {
        clients = new LinkedList();
    }
    public Client getClient(InetAddress inetAddress, int port){
        LinkedList <Client> result = new LinkedList();
        clients.stream()
                .filter(x-> x.getInetAddress().equals(inetAddress))
                .filter(x-> x.getPort()==port)
                .forEach(result::add);
        if (result.size() == 0){
            Client newClient = new Client(inetAddress, port);
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
