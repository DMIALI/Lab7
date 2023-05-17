package DataTypes;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@ToString(includeFieldNames = true)
@NoArgsConstructor


public class MusicBand implements Comparable<MusicBand>{
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private long numberOfParticipants;
    private long albumsCount;
    private MusicGenre genre;
    private Person frontMan;
    @Override
    public int compareTo(MusicBand musicBand) {
        return (int) (this.getId() - musicBand.getId());
    }
}
