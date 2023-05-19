package Commands;

import CommandData.InputCommandData;
import Utils.Printer;

import java.util.HashMap;
import java.util.Set;

public class Help extends Command {
    public Help() {
        this.setFunctionality("вывести справку по доступным командам");
    }
    @Override
    public void execute(InputCommandData input) {
        Printer printer = input.printer();
        HashMap<String, Command> commandMap = input.commandMap();
        StringBuilder out = new StringBuilder();
        Set<String> keys = commandMap.keySet();
        for (String key:keys){
            out.append(key).append(" - ").append(commandMap.get(key).getFunctionality()).append("\n");
        }
        printer.outPrint(out.toString(), input.client(), input.clientData());
    }
}
