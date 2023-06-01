package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import DataTypes.MusicBand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class JsonReader {
    public LinkedList<MusicBand> read(File json) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(json));
        TypeReference<LinkedList<MusicBand>> mapType = new TypeReference<LinkedList<MusicBand>>() {};
        LinkedList<MusicBand> musicBands = (new ObjectMapper()).readValue(inputStreamReader, mapType);
        return musicBands;
    }
}
