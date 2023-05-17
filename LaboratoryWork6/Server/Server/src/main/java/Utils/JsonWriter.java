package Utils;

import DataTypes.MusicBand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonWriter {
    public void write(File json, LinkedList<MusicBand> musicBands) throws IOException {
        FileWriter fileWriter = new FileWriter(json);
        String newjson = (new ObjectMapper()).writeValueAsString(musicBands);
        fileWriter.write(newjson);
        fileWriter.flush();
        fileWriter.close();
    }
}

