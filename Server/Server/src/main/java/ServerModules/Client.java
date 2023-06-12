package ServerModules;

import CommandData.PrintType;
import CommandData.ServerData;
import Utils.ClientConnection;
import Utils.ClientLoginAndPasswd;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@EqualsAndHashCode
public class Client{
    private final ClientLoginAndPasswd loginAndPasswd;
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
        this.loginAndPasswd = new ClientLoginAndPasswd(login, passwd);
        latestServerData = new ServerData(0L, "", PrintType.PRINT);
    }
    public void increaseCounter(){
        datagramCounter++;
    }
    public String toString(){
        return loginAndPasswd.login();
    }
    public String getLogin(){
        return loginAndPasswd.login();
    }
    public char[] getPasswd(){
        return loginAndPasswd.passwd();
    }
}
