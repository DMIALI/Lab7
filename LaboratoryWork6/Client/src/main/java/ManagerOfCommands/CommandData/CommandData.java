package ManagerOfCommands.CommandData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class CommandData implements Serializable {


    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String args;
    @Getter
    private static long number = 0;

    public CommandData() {
        number++;
    }

    public static String getNumber() {
        return Long.toString(number);
    }
}
