package Commands;

import CommandData.ClientData;
import CommandData.InputCommandData;
import DataTypes.*;
import Main.CollectionManager;
import Utils.Client;
import Utils.IdManager;
import Utils.Printer;

import java.util.LinkedList;

public class Add extends Command {
    @Override
    public void execute(InputCommandData input) {
        Client client = input.client();
        ClientData clientData = input.clientData();
        Printer printer = input.printer();
        MusicBand musicBand = input.clientData().getMusicBand();
        CollectionManager collectionManager = input.collectionManager();
        IdManager idManager = collectionManager.getIdManager();
        LinkedList<MusicBand> musicBands = collectionManager.getMusicBands();
        Long id = idManager.add();
        musicBand.setId(id);
        musicBands.add(musicBand);
        printer.outPrintln("Элемент успешно добавлен", client, clientData);
    }
}