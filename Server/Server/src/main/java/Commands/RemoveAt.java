package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import ServerModules.ControlCenter;
import Utils.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveAt extends Command {
    public RemoveAt() {
        this.setFunctionality("удалить элемент, находящийся в заданной позиции коллекции (index)");
    }
    @Override
    public void execute(InputCommandData input) {
        CollectionManager collectionManager = input.collectionManager();
        Printer printer = input.printer();
        String arg = input.clientData().getArg();
        int index = Integer.parseInt(arg);
        int dataBaseIndex = 1;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.collection");
            while (resultSet.next()) {
                if (index == dataBaseIndex){
                    if (input.client().getLogin().equals(resultSet.getString("username"))){
                        statement.executeUpdate("Delete  from collection where username = '" + input.client().getLogin() + "' and band_name_id = " +resultSet.getLong("band_name_id"));
                        input.printer().outPrintln("Элемент успешно удален =)", input.client(), input.clientData());
                        return;
                    }
                    throw new SQLException();
                } else if (index < dataBaseIndex) {
                    throw new NullPointerException();
                }
                dataBaseIndex += 1;
            }
            throw new NullPointerException();

        }catch (SQLException e){
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }catch (NullPointerException|ClassCastException e) {
            printer.errPrintln("Элемента с таким id нет", input.client(), input.clientData());
        }


    }

}
