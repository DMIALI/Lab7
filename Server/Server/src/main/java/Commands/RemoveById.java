package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;
import Utils.Printer;

import java.util.ArrayList;

public class RemoveById extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        ArrayList<Object> args = input.clientData().getArgs();
        try {
            long id = Integer.parseInt((String) args.get(0));
            collectionManager.getMusicBands().remove(collectionManager.getMusicBandById(id));
            collectionManager.getIdManager().remove(id);
            printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
        } catch (NullPointerException|ClassCastException e) {
            printer.errPrintln("Элемента с таким id нет", input.client(), input.clientData());
        }
    }
}
