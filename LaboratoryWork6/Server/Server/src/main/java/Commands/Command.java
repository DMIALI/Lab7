package Commands;

import lombok.Getter;
import lombok.Setter;

public abstract class Command {
    @Getter
    @Setter
    private String functionality;
    public Command(String function){
        this.functionality = function;
    }
    public abstract void execute(InputCommandData input);

}
