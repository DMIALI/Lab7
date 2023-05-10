package ManagerOfCommands.CommandData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientData implements Serializable, Comparable {

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private ArrayList<String> args;
    @Getter
    private static Long number = 0L;
    @Getter
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
