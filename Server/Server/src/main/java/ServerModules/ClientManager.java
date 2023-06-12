package ServerModules;

import CommandData.ClientData;
import DataTypes.MusicBand;
import Utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.*;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientManager {
    private final String clientsPath = "C:\\Users\\фвьшт\\IdeaProjects\\Lab7\\Server\\Server\\src\\main\\java\\Main\\.clients.json";
    @Getter
    private Clients clients;
    @Getter
    private HashMap <ClientConnection, Handler> handlers;
    private static final Logger logger = LogManager.getLogger();
    private final SHA_512 hashMaker;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;

    public ClientManager() {
        hashMaker = new SHA_512();
        this.jsonWriter = new JsonWriter();
        this.jsonReader = new JsonReader();
        this.clients = readClients();
        this.handlers = new HashMap<ClientConnection,Handler>();
    }
    public Client getClient(String login, char[] pwd){
        char[] passwd = get_SHA_512_Password(pwd);
        LinkedList <Client> result = new LinkedList();
        clients.getClients().stream()
                .filter(x-> x.getLogin().equals(login))
                .filter(x-> Arrays.equals(x.getPasswd(), passwd))
                .forEach(result::add);
        if (result.size() == 0){
            Client newClient = new Client(login, passwd);
            addClient(newClient);
            return newClient;
        }
        return result.getFirst();
    }
    public synchronized void addClient(Client client){
        logger.info("Новый клиент: логин " + client.getLogin());
        clients.getClients().add(client);
        writeClients(clients);
    }

    public void delClient(Client client){
        logger.info("Клиент вышел: логин " + client.getLogin());
    }

    public void checkIfClientNew(Client client, ClientData clientData){
        if (client.getDatagramCounter() == 0){
            client.setDatagramCounter(clientData.getCounter());
        }
    }
    public char[] get_SHA_512_Password(char[] passwordToHash){
        return hashMaker.get_SHA_512_Password(String.valueOf(passwordToHash), "weLoveProgramming", logger).toCharArray();
    }

    public int checkLoginAndPasswd(String login, char[] pwd) {
        char[] passwd = get_SHA_512_Password(pwd);
        for (Client client:clients.getClients()){
            if (client.getLogin().equals(login)){
                if(Arrays.equals(client.getPasswd(), passwd)){
                    return 1;
                }
                else return -1;
            }
        }
        return 0;
    }
    private void writeClients(Clients clients){
        try {
            File json = new File(clientsPath);
            FileWriter fileWriter = new FileWriter(json);
            String newjson = (new ObjectMapper()).writeValueAsString(clients);
            fileWriter.write(newjson);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.fatal("Не удалось записать клиентов в файл");
            logger.fatal(e);
        }
    }
    private Clients readClients(){
        try {
            File json = new File(clientsPath);
            if (json.length() == 0){
                logger.info("Файл с пользователями пуст");
                return new Clients(new LinkedList<Client>());
            }
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(json));
            TypeReference<Clients> mapType = new TypeReference<>() {};
            logger.info("Файл считан успешно");
            return (new ObjectMapper()).readValue(inputStreamReader, mapType);
        }catch (IOException e) {
            logger.fatal(e);
            System.exit(-1);
        }
        return null;
    }
}
