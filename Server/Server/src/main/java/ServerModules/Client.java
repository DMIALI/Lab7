package ServerModules;

import CommandData.PrintType;
import CommandData.ServerData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@EqualsAndHashCode
public class Client{
    private final String login;
    private final String passwd;

    private final InetAddress inetAddress;
    private final int port;
    @Setter
    private Handler handler = new Handler();
    @Setter
    private long datagramCounter = 0;
    //PriorityQueue<CommandData> commandsQueue = new PriorityQueue<>();
    @Setter
    private ServerData latestServerData;
    public Client(String login, String passwd, InetAddress InetAddress, int Port){
        this.login = login;
        this.passwd = passwd;
        this.inetAddress = InetAddress;
        this.port = Port;
        latestServerData = new ServerData(0L, "", PrintType.PRINT);
    }
    public void increaseCounter(){
        datagramCounter++;
    }
}
