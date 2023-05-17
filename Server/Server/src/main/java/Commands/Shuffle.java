package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;

import java.util.Collections;

public class Shuffle extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Collections.shuffle(collectionManager.getMusicBands());
        input.printer().outPrintln("Элементы успешно перемешались =)", input.client(), input.clientData());
    }
}
