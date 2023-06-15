package Commands;

import CommandData.InputCommandData;
import ServerModules.DataBase;
import lombok.Getter;
import lombok.Setter;

import java.sql.Statement;

public abstract class Command {
    @Getter
    @Setter
    private String functionality;
    protected Statement statement = DataBase.getStatement();
    public abstract void execute(InputCommandData input);

}
