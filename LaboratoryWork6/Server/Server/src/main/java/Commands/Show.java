package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;
import Utils.Printer;

public class Show extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        printer.outPrint(collectionManager.toString(), input.client(), input.clientData());
    }
}
