package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.Printer;

import java.util.ArrayList;

public abstract class Command {
    protected Printer printer = new Printer();
    public Command() {
    }

//    public abstract void processing(InputCommandData input);
    public abstract ClientData processing(String title, ArrayList<String> args);
}
