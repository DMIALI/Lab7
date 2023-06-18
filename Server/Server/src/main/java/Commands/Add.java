package Commands;

import CommandData.ClientData;
import CommandData.InputCommandData;
import DataTypes.*;
import ServerModules.CollectionManager;
import ServerModules.Client;
import Utils.IdManager;
import Utils.Printer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Add extends Command {
    public Add() {
        this.setFunctionality("добавить новый элемент в коллекцию");
    }
    @Override
    public void execute(InputCommandData input) {
        try {
            //Long id = input.collectionManager().getIdManager().add();
            MusicBand musicBand = input.clientData().getMusicBand();
            String sql = "INSERT INTO collection (" +
                    "username," +
                    "band_name," +
                    "Coordinates_x," +
                    "Coordinates_y," +
                    "numberOfParticipants," +
                    "albumsCount," +
                    "MusicGenre_name," +
                    "personName," +
                    "passportID," +
                    "hairColor," +
                    "Country_name," +
                    "location_x," +
                    "location_y," +
                    "location_z," +
                    "LocationName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Client client = input.client();

            PreparedStatement preparedStatement = statement.getConnection().prepareStatement(sql);
//            preparedStatement.setLong(1, id);
            preparedStatement.setString(1, input.client().getLogin());
            preparedStatement.setString(2, musicBand.getName());
            preparedStatement.setLong(3, musicBand.getCoordinates().getX());
            preparedStatement.setDouble(4, musicBand.getCoordinates().getY());
            preparedStatement.setLong(5, musicBand.getNumberOfParticipants());
            preparedStatement.setInt(6, (int) musicBand.getAlbumsCount());
            preparedStatement.setString(7, musicBand.getGenre().toString().toUpperCase());
            preparedStatement.setString(8, musicBand.getFrontMan().getName());
            preparedStatement.setString(9,  musicBand.getFrontMan().getPassportID());
            preparedStatement.setString(10, musicBand.getFrontMan().getHairColor().toString().toUpperCase());
            preparedStatement.setString(11, musicBand.getFrontMan().getNationality().toString().toUpperCase());
            preparedStatement.setInt(12, musicBand.getFrontMan().getLocation().getX());
            preparedStatement.setFloat(13, musicBand.getFrontMan().getLocation().getY());
            preparedStatement.setLong(14, musicBand.getFrontMan().getLocation().getZ());
            preparedStatement.setString(15, musicBand.getFrontMan().getLocation().getName());

            int rowsInserted = preparedStatement.executeUpdate();
            //Client client = input.client();
            ClientData clientData = input.clientData();
            Printer printer = input.printer();
            //MusicBand musicBand = input.clientData().getMusicBand();
            CollectionManager collectionManager = input.collectionManager();

            LinkedList<MusicBand> musicBands = collectionManager.getMusicBands();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM collection Where  = passportID'" +musicBand.getFrontMan().getPassportID() +"'");
            Long id = null;
            while (resultSet.next()){
               id = resultSet.getLong("band_name_id");

            }

            musicBand.setId(id);
            musicBands.add(musicBand);
            //printer.outPrintln("Элемент успешно добавлен", client, clientData);
            input.printer().outPrintln("Элемент успешно добавлен", client, input.clientData());


        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        } catch (NullPointerException e){
            input.printer().errPrintln("Элемент не может быть добавлен =(", input.client(), input.clientData());

        }
    }
}
