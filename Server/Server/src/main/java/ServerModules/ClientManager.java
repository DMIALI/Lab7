package ServerModules;

import CommandData.ClientData;
import Utils.ClientConnection;
import Utils.SHA_512;
import lombok.Getter;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientManager {
    @Getter
    private LinkedList <Client> clients;
    @Getter
    private HashMap <ClientConnection, Handler> handlers;
    private static final Logger logger = LogManager.getLogger();
    private final SHA_512 hashMaker;
    public ClientManager() {
        clients = new LinkedList<Client>();
        hashMaker = new SHA_512();
    }
    public Client getClient(String login, char[] pwd){
        char[] passwd = hashMaker.get_SHA_512_Password(String.valueOf(pwd), "weLoveProgramming", logger).toCharArray();
        LinkedList <Client> result = new LinkedList();
        clients.stream()
                .filter(x-> x.getLogin().equals(login))
                .filter(x-> Arrays.equals(x.getPasswd(), passwd))
                .forEach(result::add);
        if (result.size() == 0){
            logger.info("Новый клиент: адрес " + inetAddress + " порт " + port);
            Client newClient = new Client(login, passwd, inetAddress, port);
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

    public void checkIfClientNew(Client client, ClientData clientData){
        if (client.getDatagramCounter() == 0){
            client.setDatagramCounter(clientData.getCounter());
        }
    }

    public boolean checkLoginAndPasswd(String login, char[] pwd) {
        char[] passwd = hashMaker.get_SHA_512_Password(String.valueOf(pwd), "weLoveProgramming", logger).toCharArray();
        for (Client client:clients){
            if (client.getLogin().equals(login)){
                return Arrays.equals(client.getPasswd(), passwd);
            }
        }
        return true;
    }
}
