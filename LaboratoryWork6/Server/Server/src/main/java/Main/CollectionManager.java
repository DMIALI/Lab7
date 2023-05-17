package Main;

import Utils.IdManager;
import Utils.JsonReader;
import Utils.JsonWriter;
import Utils.Printer;
import DataTypes.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class CollectionManager {
    @Getter
    @Setter
    private LinkedList<MusicBand> musicBands;
    private File file;
    @Getter
    private Date creationDate;
    @Getter
    private IdManager idManager;
    private JsonWriter jsonWriter = new JsonWriter();

    public CollectionManager(String path, Scanner scanner) throws IOException {
        this.file = receiveFile(path, scanner);
        System.out.println("Файл считан успешно");
        JsonReader jsonReader = new JsonReader();
        this.musicBands = jsonReader.read(file);
        this.creationDate = new Date();
        this.idManager = new IdManager(musicBands);
    }
    public void save() throws IOException {
        jsonWriter.write(file, musicBands);
    }

    public File receiveFile(String path, Scanner scanner){
        File file;
        while (true) {
            if (path.equals("exit")){
                System.out.println("Приложение сейчас закроется....");
                System.exit(0);
            }
            file = new File(path);
            if (file.isFile()&&file.canRead()&&file.canWrite()){
                break;
            }
            if (!file.isFile()) {
                System.err.println("Файл не существует, введите другой путь к файлу:");
            } else if (!file.canRead()) {
                System.err.println("Отсутствуют права на чтение, измените права или введите путь к другому файлу:");
            } else if (!file.canWrite()) {
                System.err.println("Отсутствуют права на запись, измените права или введите путь к другому файлу:");
            }
            path = scanner.nextLine();
        }
        return file;
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
