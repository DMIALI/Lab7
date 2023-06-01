package Commands;

import CommandData.InputCommandData;
import lombok.Getter;
import lombok.Setter;

public abstract class Command {
    @Getter
    @Setter
    private String functionality;
    public abstract void execute(InputCommandData input);

}
