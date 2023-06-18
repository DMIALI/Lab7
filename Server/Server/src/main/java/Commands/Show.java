package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import Utils.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Show extends Command {
    public Show() {
        this.setFunctionality("вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public void execute(InputCommandData input) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM collection");
            StringBuilder answer = new StringBuilder(new String());
            while (res.next()) {
                // получение и обработка данных
                answer.append("username: ").append(res.getString("username"));
                answer.append(" band_name: ").append(res.getString("band_name"));
                answer.append(" band_name_id: ").append(res.getString("band_name_id"));
                answer.append(" Coordinates_x: ").append(res.getString("Coordinates_x"));
                answer.append(" Coordinates_y: ").append(res.getString("Coordinates_y"));
                answer.append(" numberOfParticipants: ").append(res.getString("numberOfParticipants"));
                answer.append(" albumsCount: ").append(res.getString("albumsCount"));
                answer.append(" MusicGenre_name: ").append(res.getString("MusicGenre_name"));
                answer.append(" personName: ").append(res.getString("personName"));
                answer.append(" passportID: ").append(res.getString("passportID"));
                answer.append(" hairColor: ").append(res.getString("hairColor"));
                answer.append(" Country_name: ").append(res.getString("Country_name"));
                answer.append(" location_x: ").append(res.getString("location_x"));
                answer.append(" location_y: ").append(res.getString("location_y"));
                answer.append(" location_z: ").append(res.getString("location_z"));
                answer.append(" LocationName: ").append(res.getString("LocationName"));
                answer.append("\n");
            }

//            CollectionManager collectionManager = input.collectionManager();
            Printer printer = input.printer();
            CollectionManager collectionManager = input.collectionManager();
            //printer.outPrint(collectionManager.toString(), input.client(), input.clientData());
            printer.outPrint(String.valueOf(answer), input.client(), input.clientData());

        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }
    }
}
