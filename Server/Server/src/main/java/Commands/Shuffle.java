package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;

import java.util.Collections;

public class Shuffle extends Command {
    public Shuffle() {
        this.setFunctionality("перемешать элементы коллекции в случайном порядке");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Collections.shuffle(collectionManager.getMusicBands());
        input.printer().outPrintln("Элементы успешно перемешались =)", input.client(), input.clientData());
    }
}
