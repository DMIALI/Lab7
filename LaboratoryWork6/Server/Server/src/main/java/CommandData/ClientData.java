package CommandData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
public class ClientData implements Comparable {
    private String name;
    private ArrayList<String> args;
    private static Long number = 0L;
    private final Long counter;
    public ClientData() {
        number++;
        counter = number;
    }

    @Override
    public int compareTo(Object o) {
        return this.counter - ((ClientData) o).getCounter() > 0 ? 1 : (this.counter - ((ClientData) o).getCounter() < 0 ? -1 : 0);
    }
}
