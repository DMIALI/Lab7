package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.PrimitiveTypeAsserter;

import java.util.ArrayList;

public class ExecuteScript extends Command{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("execute_script");
        if(args.size() == 1 ) {
            ArrayList<Object> argument = new ArrayList<>();
            argument.add((Object)(args.get(0)));
            clientData.setArgs(argument);
            return clientData;
        }else{
            printer.errPrintln("Введены некоректные данные");
            return null;
        }
    }
}
