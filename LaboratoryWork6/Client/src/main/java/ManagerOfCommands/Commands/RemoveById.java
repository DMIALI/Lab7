package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.PrimitiveTypeAsserter;

import java.util.ArrayList;

public class RemoveById extends Command{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("remove_by_id");
        if(args.size() == 1 && PrimitiveTypeAsserter.checkLong(args.get(0))) {
            clientData.setArg(args.get(0));
            return clientData;
        }else{
            printer.errPrintln("Введены некорректные данные");
            return null;
        }

    }
}
