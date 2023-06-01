package DataTypes;

import lombok.*;

@ToString(includeFieldNames = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Location {
    private int x;
    private Float y;
    private long z;
    private String name;
}
