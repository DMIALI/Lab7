package CommandData;

import Utils.Client;
import Main.CollectionManager;
import Utils.Printer;

public record InputCommandData(CollectionManager collectionManager, Client client, Printer printer, ClientData clientData) {
}
