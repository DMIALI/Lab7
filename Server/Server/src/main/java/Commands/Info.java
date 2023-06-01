package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

public class Info extends Command {
    public Info() {
        this.setFunctionality("вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }
    @Override
    public void execute(InputCommandData input) {
        Printer printer = input.printer();
        CollectionManager collectionManager = input.collectionManager();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Информация о коллекции: ");
        stringBuilder.append("Тип: LinkedList\n");
        stringBuilder.append("Дата инициализации: ");
        stringBuilder.append(String.valueOf(collectionManager.getCreationDate()));
        stringBuilder.append("\n");
        stringBuilder.append("Количество элементов: ");
        stringBuilder.append(String.valueOf(collectionManager.getMusicBands().size()));
        printer.outPrintln(stringBuilder.toString(), input.client(), input.clientData());
    }
}
