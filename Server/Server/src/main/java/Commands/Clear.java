package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;

import java.sql.SQLException;

public class Clear extends Command {
    public Clear() {
        this.setFunctionality("очистить коллекцию");
    }
    @Override
    public void execute(InputCommandData input) {
        try {
            statement.executeUpdate("Delete  from collection where username = '" + input.client().getLogin() + "'");
            CollectionManager collectionManager = input.collectionManager();
            collectionManager.getIdManager().resetToZero();
            collectionManager.getMusicBands().clear();
            collectionManager.getIdManager().resetToZero();
            input.printer().outPrintln("Коллекция успешно очищена =)", input.client(), input.clientData());
        } catch (SQLException e){
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }
    }
}
