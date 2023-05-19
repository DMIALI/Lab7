package Commands;

import CommandData.ClientData;
import CommandData.InputCommandData;
import DataTypes.Coordinates;
import DataTypes.MusicBand;
import DataTypes.MusicGenre;
import DataTypes.Person;
import Main.CollectionManager;
import Utils.Client;
import Utils.IdManager;
import Utils.Printer;

public class Update extends Command {
    public Update() {
        this.setFunctionality("обновить значение элемента коллекции, id которого равен заданному");
    }
    @Override
    public void execute(InputCommandData input) {
        Client client = input.client();
        ClientData clientData = input.clientData();
        Printer printer = input.printer();
        MusicBand update = input.clientData().getMusicBand();
        Integer id = Integer.parseInt(input.clientData().getArg());
        CollectionManager collectionManager = input.collectionManager();
        IdManager idManager = collectionManager.getIdManager();
        if (idManager.getIds().contains(id)){
            MusicBand musicBand = collectionManager.getMusicBandById(id);
            musicBand.setName(update.getName());
            musicBand.setCoordinates(update.getCoordinates());
            musicBand.setNumberOfParticipants(update.getNumberOfParticipants());
            musicBand.setAlbumsCount(update.getAlbumsCount());
            musicBand.setGenre(update.getGenre());
            musicBand.setFrontMan(update.getFrontMan());
            printer.outPrintln("Элемент успешно обновлен", client, clientData);
        }
        else{
            printer.errPrintln("Элемента с таким id не существует", client, clientData);
        }
    }
}
