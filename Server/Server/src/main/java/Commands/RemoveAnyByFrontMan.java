package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveAnyByFrontMan extends Command {
    public RemoveAnyByFrontMan() {
        this.setFunctionality("удалить из коллекции один элемент, значение поля frontMan которого эквивалентно заданному");
    }

    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.collection WHERE  username = '" + input.client().getLogin() + "'");
            while (resultSet.next()) {
                if (arg.equals(resultSet.getString("personName"))) {
                    statement.executeUpdate("Delete  from collection where  band_name_id = " + resultSet.getLong("band_name_id"));
                    collectionManager.getIdManager().remove(resultSet.getLong("band_name_id"));
                    input.printer().outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                    return;
                }
            }
            throw new NullPointerException();
        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        } catch (NullPointerException | ClassCastException e) {
            printer.errPrintln("Фронт мена с таким именем нет", input.client(), input.clientData());
        }

    }
}
