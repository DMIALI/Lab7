package Commands;

import CommandData.InputCommandData;
import ServerModules.CollectionManager;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffle extends Command {
    public Shuffle() {
        this.setFunctionality("перемешать элементы коллекции в случайном порядке");
    }
    @Override
    public void execute(InputCommandData input) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM collection WHERE username IS NULL OR username = '" + input.client().getLogin() + "'");

            ArrayList<String> ans  = new ArrayList<>();
            while (res.next()) {
                StringBuilder answer = new StringBuilder(new String());
                // получение и обработка данных
                answer.append("username: ").append(res.getString("username"));
                answer.append(" band_name_id: ").append(res.getString("band_name_id"));
                answer.append(" band_name: ").append(res.getString("band_name"));
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
                ans.add(String.valueOf(answer));
            }
            Collections.shuffle(ans);
            CollectionManager collectionManager = input.collectionManager();
            Collections.shuffle(collectionManager.getMusicBands());
            input.printer().outPrintln("Элементы успешно перемешались =)", input.client(), input.clientData());
            //input.printer().outPrintln(ans.toString(), input.client(), input.clientData());
        }catch (SQLException e){
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        }
    }
}
