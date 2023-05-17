package Commands;

import CommandData.InputCommandData;
import Main.CollectionManager;
import Utils.Printer;

import java.io.IOException;

public class Save extends Command {
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        try {
            collectionManager.save();
            printer.outPrintln("Коллекция успешно сохранена!", input.client(), input.clientData());
        }
        catch (IOException e){
            printer.errPrintln("Не удалось сохранить в файл: " + String.valueOf(e), input.client(), input.clientData());
        }
    }
}
