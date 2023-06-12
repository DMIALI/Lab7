package ServerModules;

import CommandData.PrintType;
import CommandData.ServerData;
import Utils.ClientConnection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@EqualsAndHashCode
public class Client{
    private final String login;
    private final char[] passwd;
    @Setter
    private ClientConnection clientConnection;
    @Setter
    private Handler handler = new Handler();
    @Setter
    private long datagramCounter = 0;
    //PriorityQueue<CommandData> commandsQueue = new PriorityQueue<>();
    @Setter
    private ServerData latestServerData;
    public Client(String login, char[] passwd){
        this.login = login;
        this.passwd = passwd;
        latestServerData = new ServerData(0L, "", PrintType.PRINT);
    }
    public void increaseCounter(){
        datagramCounter++;
    }
    public String toString(){
        return login;
    }
}
