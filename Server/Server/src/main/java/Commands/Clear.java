package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;

public class Clear extends Command {
    public Clear() {
        this.setFunctionality("очистить коллекцию");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        collectionManager.getMusicBands().clear();
        collectionManager.getIdManager().resetToZero();
        input.printer().outPrintln("Коллекция успешно очищена =)", input.client(), input.clientData());
    }
}
