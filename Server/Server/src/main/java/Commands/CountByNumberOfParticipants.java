package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import ServerModules.CollectionManager;
import Utils.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountByNumberOfParticipants extends Commands.Command {
    public CountByNumberOfParticipants() {
        this.setFunctionality("вывести количество элементов, значение поля numberOfParticipants которых равно заданному");
    }
    @Override
    public void execute(InputCommandData input) {
        int arg = Integer.parseInt( input.clientData().getArg());
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.collection");
            StringBuilder answer = new StringBuilder(new String());
            int countByNumberOfParticipants = 0;
            while (resultSet.next()) {
                if(arg == resultSet.getInt("numberOfParticipants")){
                    countByNumberOfParticipants += 1;
                }
            }
            input.printer().outPrintln("Количество элементов = " + countByNumberOfParticipants, input.client(), input.clientData());
        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }
    }
}
