package Utils;

import ServerModules.Client;
import lombok.*;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
@Getter
public class Clients {
    private LinkedList<Client> clients;
    public Clients(LinkedList<Client> cl){
        this.clients = cl;
    }
    public void createClientsFromLoginsAndPasswords(LinkedList<ClientLoginAndPasswd> loginAndPasswds){
        clients = new LinkedList<Client>();
        for (ClientLoginAndPasswd client:loginAndPasswds){
            clients.add(new Client(client.login(), client.passwd()));
        }
    }
    public LinkedList<ClientLoginAndPasswd> getLoginsAndPasswordsFromClients(){
        LinkedList<ClientLoginAndPasswd> loginAndPasswds = new LinkedList<>();
        for (Client client:clients){
            loginAndPasswds.add(client.getLoginAndPasswd());
        }
        return loginAndPasswds;
    }
}
