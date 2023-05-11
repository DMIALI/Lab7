package Utils;

import CommandData.ClientData;
import CommandData.PrintType;
import CommandData.ServerData;
import Main.Server;

import java.io.IOException;

public class Printer {
    Server server;
    public Printer(Server serv){
        server = serv;
    }
    public void outPrintln(String out, Client client, ClientData clientData){
        server.send(new ServerData(clientData.getCounter(), out, PrintType.PRINTLN), client);
    }
    public void outPrint(String out, Client client, ClientData clientData){
        server.send(new ServerData(clientData.getCounter(), out, PrintType.PRINT), client);
    }
    public void errPrintln(String out, Client client, ClientData clientData){
        server.send(new ServerData(clientData.getCounter(), out, PrintType.ERRPRINTLN), client);
    }
    public void exit(Client client){
        server.getClientManager().delClient(client);
    }
}
