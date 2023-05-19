package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;

import java.util.LinkedList;

public class Reorder extends Command {
    public Reorder() {
        this.setFunctionality("отсортировать коллекцию в порядке, обратном нынешнему");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        LinkedList<MusicBand> musicBandLinkedList = new LinkedList<MusicBand>();
        for(MusicBand musicBand: collectionManager.getMusicBands()){
            musicBandLinkedList.add(0, musicBand);
        }
        collectionManager.setMusicBands(musicBandLinkedList);
        input.printer().outPrintln("Коллекция успешно отсортирована в обратном порядке =)", input.client(), input.clientData());
    }
}
