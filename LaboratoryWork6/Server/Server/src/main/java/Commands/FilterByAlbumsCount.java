package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

import java.util.ArrayList;

public class FilterByAlbumsCount extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        ArrayList<Object> args = input.clientData().getArgs();
        int albumsCount = Integer.parseInt((String) args.get(0));
        for(MusicBand musicBand : collectionManager.getMusicBands()){
            if (musicBand.getAlbumsCount() == albumsCount){
                printer.outPrintln(musicBand.toString(), input.client(), input.clientData());
            }
        }
    }
}

