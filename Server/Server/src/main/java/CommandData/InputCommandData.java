package CommandData;

import Commands.Command;
import Utils.Client;
import Main.CollectionManager;
import Utils.Printer;

import java.util.HashMap;

public record InputCommandData(CollectionManager collectionManager, Client client, Printer printer, ClientData clientData, HashMap<String, Command> commandMap) {
}
