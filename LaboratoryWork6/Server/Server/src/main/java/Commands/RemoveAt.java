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
        ArrayList<Object> args = input.clientData().getArgs();
        int index = Integer.parseInt((String) args.get(0));
        collectionManager.getMusicBands().remove(index);
        collectionManager.getIdManager().remove((long) index);
        printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
    }

}
