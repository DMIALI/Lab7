package Utils;

import ServerModules.Client;
import lombok.*;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Clients {
    private LinkedList<Client> clients;
}
