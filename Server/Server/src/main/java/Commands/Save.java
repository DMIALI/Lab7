package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Save extends Command {
    private static final Logger logger = LogManager.getLogger();
    public Save() {
        this.setFunctionality("сохранить коллекцию в файл");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        try {
            collectionManager.save();
            logger.info("Коллекция успешно сохранена");
            printer.outPrintln("Коллекция успешно сохранена!", input.client(), input.clientData());
        }
        catch (IOException e){
            logger.error("Не удалось сохранить в файл: " + String.valueOf(e));
            printer.errPrintln("Не удалось сохранить в файл: " + String.valueOf(e), input.client(), input.clientData());
        }
    }
}
