package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.PrimitiveTypeAsserter;

import java.util.ArrayList;

public class RemoveAnyByFrontMan extends Command{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("remove_any_by_front_man");
        if(args.size() == 1 ) {
            ArrayList<Object> argument = new ArrayList<>();
            argument.add((Object)args.get(0));
            clientData.setArgs(argument);
            return clientData;
        }else{
            printer.errPrintln("Введены некоректные данные");
            return null;
        }
    }

}
