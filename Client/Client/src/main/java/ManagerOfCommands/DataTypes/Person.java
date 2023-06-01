package ManagerOfCommands.DataTypes;

import lombok.*;

/**
 * Class of characteristic of people
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)

public class Person {
    private String name;
    private String passportID;
    private Color hairColor;
    private Country nationality;
    private Location location; //Поле не может быть null
}
