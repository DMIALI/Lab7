package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.Printer;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command {
    protected Printer printer = new Printer();
    protected Scanner scanner = new Scanner(System.in);
    public Command() {
    }

//    public abstract void processing(InputCommandData input);
    public abstract ClientData processing(String title, ArrayList<String> args);
}
