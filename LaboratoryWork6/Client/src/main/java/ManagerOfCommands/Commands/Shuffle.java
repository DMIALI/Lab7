package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;

import java.util.ArrayList;

public class Shuffle extends Command{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        ClientData clientData = new ClientData();
        clientData.setName("shuffle");
        return clientData;
    }
}
