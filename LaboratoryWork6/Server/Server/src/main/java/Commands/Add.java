package Commands;

import CommandData.ClientData;
import CommandData.InputCommandData;
import DataTypes.*;
import Main.CollectionManager;
import Utils.Client;
import Utils.IdManager;
import Utils.Printer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Add extends Command {
    @Override
    public void execute(InputCommandData input) {
        Client client = input.client();
        ClientData clientData = input.clientData();
        Printer printer = input.printer();
        ArrayList<Object> args = input.clientData().getArgs();
        CollectionManager collectionManager = input.collectionManager();
        IdManager idManager = collectionManager.getIdManager();
        LinkedList<MusicBand> musicBands = collectionManager.getMusicBands();
        Long id = idManager.add();
        MusicBand musicBand = new MusicBand(id,
                (String) args.get(0),
                new Coordinates(Long.parseLong((String) args.get(1)), Double.parseDouble((String) args.get(2))),
                new Date(),
                Long.parseLong((String) args.get(4)),
                Long.parseLong((String) args.get(5)),
                MusicGenre.valueOf((String) args.get(6)),
                new Person((String) args.get(7), (String) args.get(8), Color.valueOf((String) args.get(9)), Country.valueOf((String) args.get(10)), new Location(Integer.parseInt((String) args.get(11)), Float.parseFloat((String) args.get(12)), Long.parseLong((String) args.get(13)), (String) args.get(14))));
        musicBands.add(musicBand);
        printer.outPrintln("Элемент успешно добавлен", client, clientData);
    }
}
