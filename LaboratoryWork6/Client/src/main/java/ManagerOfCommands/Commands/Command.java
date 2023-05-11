package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;

import java.util.ArrayList;

public abstract class Command {

    public Command() {
    }

//    public abstract void processing(InputCommandData input);
    public abstract ClientData processing(String title, ArrayList<String> args);
}
