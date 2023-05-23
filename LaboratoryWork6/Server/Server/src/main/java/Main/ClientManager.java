package Main;

import Utils.Client;
import lombok.Getter;

import java.net.InetAddress;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientManager {
    @Getter
    private LinkedList <Client> clients;
    private static final Logger logger = LogManager.getLogger();
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
            logger.info("Новый клиент: адрес " + inetAddress + " порт " + port);
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
        logger.info("Клиент вышел: адрес " + client.getInetAddress() + " порт " + client.getPort());
        clients.remove(client);
    }
}
