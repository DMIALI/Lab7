package ManagerOfCommands.CommandData;

import ManagerOfCommands.Commands.Command;
import Utils.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public record InputCommandData(Scanner scanner, Printer printer, ArrayList<String> args, HashMap<String, Command> commandMap) {
}
