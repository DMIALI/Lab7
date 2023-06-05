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
        server.getClientManager().getClient(client.getInetAddress(), client.getPort()).setLatestServerData(serverData);
        server.send(serverData, client);
    }
    public void outPrint(String out, Client client, ClientData clientData){
        ServerData serverData = new ServerData(clientData.getCounter(), out, PrintType.PRINT);
        server.getClientManager().getClient(client.getInetAddress(), client.getPort()).setLatestServerData(serverData);
        server.send(serverData, client);
    }
    public void errPrintln(String out, Client client, ClientData clientData){
        ServerData serverData = new ServerData(clientData.getCounter(), out, PrintType.ERRPRINTLN);
        server.getClientManager().getClient(client.getInetAddress(), client.getPort()).setLatestServerData(serverData);
        server.send(serverData, client);
    }
    public void exit(Client client){
        server.getClientManager().delClient(client);
    }
}
