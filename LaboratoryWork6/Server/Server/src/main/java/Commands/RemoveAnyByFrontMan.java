package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

public class RemoveAnyByFrontMan extends Command {
    public RemoveAnyByFrontMan() {
        this.setFunctionality("удалить из коллекции один элемент, значение поля frontMan которого эквивалентно заданному");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();
        for(MusicBand musicBand: collectionManager.getMusicBands()){
            if(musicBand.getFrontMan().getName().equals(arg)){
                collectionManager.getMusicBands().remove(musicBand);
                collectionManager.getIdManager().remove(musicBand.getId());
                printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                return;
            }
        }
        printer.errPrintln("frontMan с таким именем не найден =(", input.client(), input.clientData());
    }
}
