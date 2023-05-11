package Main;

import CommandData.InputCommandData;
import Commands.*;
import Utils.Printer;

import java.io.IOException;
import java.util.*;
public class ControlCenter {

    private final HashMap<String, Command> commandMap = new HashMap<>();

    public ControlCenter() {
        commandMap.put("add", new Add());
        commandMap.put("clear", new Clear());
        commandMap.put("count_by_number_of_participants", new CountByNumberOfParticipants());
        commandMap.put("execute_script", new ExecuteScript());
        commandMap.put("exit", new Exit());
        commandMap.put("filter_by_albums_count", new FilterByAlbumsCount());
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("remove_any_by_front_man", new RemoveAnyByFrontMan());
        commandMap.put("remove_at", new RemoveAt());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("reorder", new Reorder());
        commandMap.put("save", new Save());
        commandMap.put("show", new Show());
        commandMap.put("shuffle", new Shuffle());
        commandMap.put("update", new Update());
    }

    public void Start(Printer printer) throws IOException {
        /*Scanner scanner = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager(path, scanner,printer);
        printer.outPrint("Введите команду: ");
        String command = scanner.nextLine();
        while (true) {
            try {
                ArrayList<String> listOfCommand = new ArrayList<String>();
                Collections.addAll(listOfCommand, command.split(" "));
                String name = listOfCommand.remove(0);
                commandMap.get(inputHandler(name)).execute(new InputCommandData(collectionManager,scanner, printer, listOfCommand, commandMap));
            } catch (NullPointerException e) {
                printer.errPrintln("Команда не найдена");
            }
            printer.outPrint("Введите команду: ");
            command = scanner.nextLine();

        }*/
    }

    private String inputHandler(String input) {
        StringBuilder name = new StringBuilder();
        for (String word : input.split("_")) {
            name.append(word.toLowerCase());
            name.append('_');
        }
        name.deleteCharAt(name.lastIndexOf("_"));
        return name.toString();
    }
}
