package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

import java.util.ArrayList;

public class RemoveAnyByFrontMan extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        ArrayList<Object> args = input.clientData().getArgs();
        for(MusicBand musicBand: collectionManager.getMusicBands()){
            if(musicBand.getFrontMan().getName().equals(args.get(0))){
                collectionManager.getMusicBands().remove(musicBand);
                printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                return;
            }
        }
        printer.errPrintln("frontMan с таким именем не найден =(", input.client(), input.clientData());
    }
}
