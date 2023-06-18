package ServerModules;

import Utils.IdManager;
import Utils.JsonReader;
import Utils.JsonWriter;
import DataTypes.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CollectionManager {
    @Getter
    @Setter
    private LinkedList<MusicBand> musicBands;
    private File file;
    @Getter
    private Date creationDate;
    @Getter
    private IdManager idManager;

    protected Statement statement = DataBase.getStatement();
    private JsonWriter jsonWriter = new JsonWriter();
    private static final Logger logger = LogManager.getLogger();
    public CollectionManager( Scanner scanner) throws IOException {
        //this.file = receiveFile(path, scanner);
        logger.info("Файл считан успешно");
        JsonReader jsonReader = new JsonReader();
        this.musicBands = receiveFile(scanner);
        this.creationDate = new Date();
        //this.idManager = new IdManager(musicBands);
    }
    public void save() throws IOException {
        jsonWriter.write(file, musicBands);
    }
    private LinkedList<MusicBand> receiveFile(Scanner scanner){
        LinkedList<MusicBand> musicBand = new LinkedList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM collection");
            while (resultSet.next()) {
                MusicBand add = new MusicBand();
                add.setId(resultSet.getLong("band_name_id"));
                add.setName(resultSet.getString("band_name"));
                add.setCoordinates(new Coordinates(resultSet.getLong("Coordinates_x"), resultSet.getLong("Coordinates_y")));
                add.setNumberOfParticipants(resultSet.getInt("numberOfParticipants"));
                add.setAlbumsCount(resultSet.getInt("albumsCount"));
                add.setGenre(MusicGenre.valueOf(resultSet.getString("MusicGenre_name").toUpperCase()));
                Person person = new Person();
                Location location = new Location(resultSet.getInt("location_x"), resultSet.getFloat("location_y"), resultSet.getLong("location_z"), resultSet.getString("LocationName"));
                Person newPerson = new Person(resultSet.getString("personName"),resultSet.getString("passportID"), Color.valueOf(resultSet.getString("hairColor").toUpperCase()), Country.valueOf(resultSet.getString("Country_name").toUpperCase()), location );
                add.setFrontMan(person);
                musicBand.add(add);
//                if (path.equals("exit")) {
//                    System.out.println("Приложение сейчас закроется....");
//                    System.exit(0);
//                }
//                file = new File(path);
//                if (file.isFile() && file.canRead() && file.canWrite()) {
//                    break;
//                }
//                if (!file.isFile()) {
//                    System.err.println("Файл не существует, введите другой путь к файлу:");
//                } else if (!file.canRead()) {
//                    System.err.println("Отсутствуют права на чтение, измените права или введите путь к другому файлу:");
//                } else if (!file.canWrite()) {
//                    System.err.println("Отсутствуют права на запись, измените права или введите путь к другому файлу:");
//                }
//                path = scanner.nextLine();
            }
            return musicBand;
            //return file;
        }catch (SQLException e){
            return null;

        }
    }
    public MusicBand getMusicBandById (long id) throws NullPointerException {
        for(MusicBand musicBand: getMusicBands()){
            if (musicBand.getId() == id){
                return musicBand;
            }
        }
        throw new NullPointerException();
    }
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(MusicBand musicBand: musicBands){
            out.append(musicBand.toString()).append("\n");
        }
        return out.toString();
    }
}
