package ManagerOfCommands.CommandData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class CommandData implements Serializable, Comparable {

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String args;
    @Getter
    private static Long number = 0L;
    private final Long counter;
    public CommandData() {
        number++;
        counter = number;
    }

    public static Long getNumber() {
        return number;
    }
    public Long getCounter() {
        return number;
    }
    @Override
    public int compareTo(Object o) {
        return this.counter - ((CommandData) o).getCounter() > 0 ? 1 : (this.counter - ((CommandData) o).getCounter() < 0 ? -1 : 0);
    }
}
