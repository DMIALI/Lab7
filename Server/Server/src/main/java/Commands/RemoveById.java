package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class RemoveById extends Command {
    public RemoveById() {
        this.setFunctionality("удалить элемент из коллекции по его id");
    }
    @Override
    public void execute(InputCommandData input) {
        Printer printer = input.printer();

        try {
            String arg = input.clientData().getArg();
            long id = Integer.parseInt(arg);
            CollectionManager collectionManager = input.collectionManager();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM collection WHERE  username = '" + input.client().getLogin()+ "'");
            while (resultSet.next()) {
                if (id == resultSet.getLong("band_name_id") && input.client().getLogin().equals(resultSet.getString("username"))){
                    statement.executeUpdate("Delete  from collection where  and band_name_id = " +id);
                    collectionManager.getMusicBands().remove(collectionManager.getMusicBandById(id));
                    collectionManager.getIdManager().remove(id);
                    printer.outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                    input.printer().outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                    input.collectionManager().getIdManager().remove((long) id);
                    return;
                }
            }
            throw new NullPointerException();

        }catch (SQLException e){
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }catch (NullPointerException|ClassCastException e) {
             printer.errPrintln("Элемента с таким id нет", input.client(), input.clientData());
        }
    }
}
