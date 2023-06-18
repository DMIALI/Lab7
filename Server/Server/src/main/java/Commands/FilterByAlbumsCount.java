package Commands;

import CommandData.InputCommandData;
import DataTypes.MusicBand;
import ServerModules.CollectionManager;
import Utils.Printer;

import java.sql.ResultSet;

import java.sql.SQLException;

public class FilterByAlbumsCount extends Command {
    public FilterByAlbumsCount() {
        this.setFunctionality("вывести элементы, значение поля albumsCount которых равно заданному");
    }
    @Override
    public void execute(InputCommandData input) {
        int arg = Integer.parseInt( input.clientData().getArg());
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM collection");
            StringBuilder answer = new StringBuilder(new String());
            
            while (resultSet.next()) {
                if(arg == resultSet.getInt("albumsCount")){
                    answer.append("username: ").append(resultSet.getString("username"));
                    answer.append(" band_name: ").append(resultSet.getString("band_name"));
                    answer.append(" band_name_id: ").append(resultSet.getString("band_name_id"));
                    answer.append(" Coordinates_x: ").append(resultSet.getString("Coordinates_x"));
                    answer.append(" Coordinates_y: ").append(resultSet.getString("Coordinates_y"));
                    answer.append(" numberOfParticipants: ").append(resultSet.getString("numberOfParticipants"));
                    answer.append(" albumsCount: ").append(resultSet.getString("albumsCount"));
                    answer.append(" MusicGenre_name: ").append(resultSet.getString("MusicGenre_name"));
                    answer.append(" personName: ").append(resultSet.getString("personName"));
                    answer.append(" passportID: ").append(resultSet.getString("passportID"));
                    answer.append(" hairColor: ").append(resultSet.getString("hairColor"));
                    answer.append(" Country_name: ").append(resultSet.getString("Country_name"));
                    answer.append(" location_x: ").append(resultSet.getString("location_x"));
                    answer.append(" location_y: ").append(resultSet.getString("location_y"));
                    answer.append(" location_z: ").append(resultSet.getString("location_z"));
                    answer.append(" LocationName: ").append(resultSet.getString("LocationName"));
                    answer.append("\n");
                }
            }
            input.printer().outPrintln(String.valueOf(answer), input.client(), input.clientData());
        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }

    }
}

