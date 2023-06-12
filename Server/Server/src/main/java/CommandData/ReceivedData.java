package CommandData;

import ServerModules.Client;
import Utils.ClientConnection;

public record ReceivedData(ClientData clientData, ClientConnection clientConnection) {
}
