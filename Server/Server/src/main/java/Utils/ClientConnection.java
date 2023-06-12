package Utils;

import java.net.InetAddress;

public record ClientConnection(InetAddress inetAddress, int port) {
}
