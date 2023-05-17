package Utils;

import lombok.Getter;
import DataTypes.MusicBand;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Control id for music band
 */
public class IdManager {
    @Getter

    private LinkedList<Integer> ids = new LinkedList<>();
    private Long maxid = 0L;

    public IdManager(LinkedList<MusicBand> musicBands) {
        LinkedList<MusicBand> copy = new LinkedList<MusicBand>(musicBands);
        Collections.sort(copy);
        maxid = copy.getLast().getId();
        for (MusicBand i : copy) {
            ids.add(Math.toIntExact(i.getId()));
        }
    }

    public Long add() {
        if (!maxid.equals(Integer.MAX_VALUE)) {
            maxid += 1;
            return maxid;
        } else {
            Long counter = 0L;
            for (Integer i : ids) {
                counter++;
                if (i > counter + 1) {
                    ids.add(ids.indexOf(i), i - 1);
                    return Long.valueOf(i - 1);
                }
            }
        }
        return 0L;
    }
    public void resetToZero(){
        maxid = 0L;
        ids.clear();
    }

    public void remove(Long id) {
        ids.remove(id);
    }
}
