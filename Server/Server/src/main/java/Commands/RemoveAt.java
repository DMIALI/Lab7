package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

public class RemoveAt extends Command {
    public RemoveAt() {
        this.setFunctionality("удалить элемент, находящийся в заданной позиции коллекции (index)");
    }
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
