package CommandData;

import ServerModules.Client;

public record ReceivedData(ClientData clientData, Client client) {
}
