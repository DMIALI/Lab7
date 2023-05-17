package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;
import Utils.Printer;

import java.util.ArrayList;

public class RemoveAt extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();
        int index = Integer.parseInt(arg);
        collectionManager.getMusicBands().remove(index);
        collectionManager.getIdManager().remove((long) index);
        printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
    }

}