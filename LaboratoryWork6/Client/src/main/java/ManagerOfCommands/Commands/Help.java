package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;

import java.util.ArrayList;

public class Help extends Command{

//    @Override
//    public void processing(InputCommandData input) {
//
//    }


    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("help");
        clientData.setArgs(null);
        return clientData;
    }
}
