package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

public class Show extends Command {
    public Show() {
        this.setFunctionality("вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        printer.outPrint(collectionManager.toString(), input.client(), input.clientData());
    }
}
