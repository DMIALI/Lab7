package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import Utils.PrimitiveTypeAsserter;

import java.util.ArrayList;

public class CountByNumberOfParticipants extends Command{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("count_by_number_of_participants");
        if(args.size() == 1 && PrimitiveTypeAsserter.checkInteger(args.get(0))) {
            clientData.setArg(args.get(0));
            return clientData;
        }else{
            printer.errPrintln("Введены некорректные данные");
            ClientData.setNumber(clientData.getCounter() - 1);
            return null;
        }
    }
}
