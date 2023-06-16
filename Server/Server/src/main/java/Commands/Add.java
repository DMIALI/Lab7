package Commands;

import CommandData.ClientData;
import CommandData.InputCommandData;
import DataTypes.*;
import ServerModules.CollectionManager;
import ServerModules.Client;
import Utils.IdManager;
import Utils.Printer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class Add extends Command {
    public Add() {
        this.setFunctionality("добавить новый элемент в коллекцию");
    }
    @Override
    public void execute(InputCommandData input) {
        try {
            Long id = input.collectionManager().getIdManager().add();
            MusicBand musicBand = input.clientData().getMusicBand();
            String sql = "INSERT INTO public.collection (" +
                    "band_name_id," +
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
                    "LocationName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Client client = input.client();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, input.client().getLogin());
            preparedStatement.setString(3, musicBand.getName());
            preparedStatement.setLong(4, musicBand.getCoordinates().getX());
            preparedStatement.setDouble(5, musicBand.getCoordinates().getY());
            preparedStatement.setLong(6, musicBand.getNumberOfParticipants());
            preparedStatement.setInt(7, (int) musicBand.getAlbumsCount());
            preparedStatement.setString(8, musicBand.getGenre().toString().toUpperCase());
            preparedStatement.setString(9, musicBand.getFrontMan().getName());
            preparedStatement.setString(10,  musicBand.getFrontMan().getPassportID());
            preparedStatement.setString(11, musicBand.getFrontMan().getHairColor().toString().toUpperCase());
            preparedStatement.setString(12, musicBand.getFrontMan().getNationality().toString().substring(0, 1).toUpperCase() + musicBand.getFrontMan().getNationality().toString().substring(1).toLowerCase());
            preparedStatement.setInt(13, musicBand.getFrontMan().getLocation().getX());
            preparedStatement.setFloat(14, musicBand.getFrontMan().getLocation().getY());
            preparedStatement.setLong(15, musicBand.getFrontMan().getLocation().getZ());
            preparedStatement.setString(16, musicBand.getFrontMan().getLocation().getName());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                input.printer().outPrintln("Элемент успешно добавлен", client, input.clientData());
            }
            throw new NullPointerException();

        } catch (SQLException e) {
            input.printer().errPrintln("Не удалось осуществить выборку", input.client(), input.clientData());
        } catch (NullPointerException e){
            input.printer().errPrintln("Элемент не может быть добавлен =(", input.client(), input.clientData());

        }
    }
}
