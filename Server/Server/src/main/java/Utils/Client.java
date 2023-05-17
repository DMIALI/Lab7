package Utils;

import CommandData.ClientData;
import CommandData.PrintType;
import CommandData.ServerData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@EqualsAndHashCode
public class Client{
    InetAddress inetAddress;
    int port;
    long datagramCounter;
    //PriorityQueue<CommandData> commandsQueue = new PriorityQueue<>();
    @Setter
    ServerData latestServerData;
    public Client(InetAddress InetAddress, int Port){
        this.inetAddress = InetAddress;
        this.port = Port;
        datagramCounter = 1;
        latestServerData = new ServerData(0L, "", PrintType.PRINT);
    }
    public void increaseCounter(){
        datagramCounter++;
    }
}
