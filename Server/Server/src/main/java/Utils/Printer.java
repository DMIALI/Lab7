package Utils;

import CommandData.ClientData;
import CommandData.PrintType;
import CommandData.ServerData;
import Main.Server;
import ServerModules.Client;

public class Printer {
    Server server;
    public Printer(Server serv){
        server = serv;
    }
    public void outPrintln(String out, Client client, ClientData clientData){
        ServerData serverData = new ServerData(clientData.getCounter(), out, PrintType.PRINTLN);
        client.setLatestServerData(serverData);
        server.send(serverData, client.getClientConnection());
    }
    public void outPrint(String out, Client client, ClientData clientData){
        ServerData serverData = new ServerData(clientData.getCounter(), out, PrintType.PRINT);
        client.setLatestServerData(serverData);
        server.send(serverData, client.getClientConnection());
    }
    public void errPrintln(String out, Client client, ClientData clientData){
        ServerData serverData = new ServerData(clientData.getCounter(), out, PrintType.ERRPRINTLN);
        client.setLatestServerData(serverData);
        server.send(serverData, client.getClientConnection());
    }
    public void exit(Client client){
        server.getClientManager().delClient(client);
    }
}
