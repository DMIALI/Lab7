package DataTypes;

import lombok.*;

/**
 * Class of characteristic of people
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@Builder

public class Person {
    private String name;
    private String passportID;
    private Color hairColor;
    private Country nationality;
    private Location location; //Поле не может быть null
}
