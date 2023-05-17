package CommandData;

import DataTypes.MusicBand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientData implements Comparable {
    private String name;
    private String arg;
    private MusicBand musicBand;
    private static Long number = 0L;
    private final Long counter;
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
