package Commands;

import CommandData.InputCommandData;

public class Exit extends Command {
    @Override
    public void execute(InputCommandData input) {
        input.commandMap().get("save").execute(input);
        input.printer().exit(input.client());
    }
}
