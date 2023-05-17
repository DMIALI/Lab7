package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import Main.CollectionManager;
import Utils.Printer;

public class CountByNumberOfParticipants extends Commands.Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();
        int numberOfParticipants = Integer.parseInt(arg);
        int countByNumberOfParticipants = 0;
        for (MusicBand musicBand : collectionManager.getMusicBands()) {
            if (musicBand.getNumberOfParticipants() == numberOfParticipants) {
                countByNumberOfParticipants++;
            }
        }
        printer.outPrintln("Количество элементов = " + countByNumberOfParticipants, input.client(), input.clientData());
    }
}
