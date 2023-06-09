package ManagerOfCommands.CommandData;

import ManagerOfCommands.DataTypes.MusicBand;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString(includeFieldNames = true)
public class ClientData implements Comparable {
    private String login;
    private char[] passwd;
    private String name;
    private String arg;
    private MusicBand musicBand;
    @Setter
    private static Long number = 0L;
    private Long counter;
    public ClientData() {
        if (number.equals(Long.MAX_VALUE)){
            number = 0L;
        }
        number++;
        counter = number;
    }

    @Override
    public int compareTo(Object o) {
        return this.counter - ((ClientData) o).getCounter() > 0 ? 1 : (this.counter - ((ClientData) o).getCounter() < 0 ? -1 : 0);
    }
}
