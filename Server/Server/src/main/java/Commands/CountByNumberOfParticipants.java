package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

import java.util.ArrayList;

public class CountByNumberOfParticipants extends Commands.Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        ArrayList<Object> args = input.clientData().getArgs();
        int numberOfParticipants = (int) args.get(0);
        int countByNumberOfParticipants = 0;
        for (MusicBand musicBand : collectionManager.getMusicBands()) {
            if (musicBand.getNumberOfParticipants() == numberOfParticipants) {
                countByNumberOfParticipants++;
            }
        }
        printer.outPrintln("Количество элементов = " + countByNumberOfParticipants, input.client(), input.clientData());
    }
}
