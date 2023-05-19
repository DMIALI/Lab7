package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

public class FilterByAlbumsCount extends Command {
    public FilterByAlbumsCount() {
        this.setFunctionality("вывести элементы, значение поля albumsCount которых равно заданном");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();
        int albumsCount = Integer.parseInt(arg);
        for(MusicBand musicBand : collectionManager.getMusicBands()){
            if (musicBand.getAlbumsCount() == albumsCount){
                printer.outPrintln(musicBand.toString(), input.client(), input.clientData());
            }
        }
    }
}

