package DataTypes;

import lombok.*;
@ToString(includeFieldNames = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Coordinates {
    private Long x;
    private double y;
}
