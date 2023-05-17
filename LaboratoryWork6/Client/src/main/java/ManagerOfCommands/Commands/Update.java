package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import ManagerOfCommands.DataTypes.*;
import Utils.Operation;
import Utils.PrimitiveTypeAsserter;
import Utils.Printer;
import Utils.RepeatInput;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Update extends Add{
    @Override
    public ClientData processing(String title, ArrayList<String> args) {
        if (PrimitiveTypeAsserter.checkLong(args.get(0))){
            printer.outPrintln("Изменение данных музыкальной группы");
            MusicBand musicBand = new MusicBand();
            musicBand.setName(receiveName(scanner, printer));
            musicBand.setCoordinates(receiveCoordinates(scanner, printer));
            musicBand.setNumberOfParticipants(receiveNumberOfParticipants(scanner, printer));
            musicBand.setAlbumsCount(receiveAlbumsCount(scanner, printer));
            musicBand.setGenre(receiveMusicGenre(scanner, printer));
            musicBand.setFrontMan(receivePerson(scanner, printer));
            ClientData clientData = new ClientData();
            clientData.setName("update");
            clientData.setMusicBand(musicBand);
            clientData.setArg(args.get(0));
            return clientData;
        }
        else{
            printer.errPrintln("id элемента - число типа long");
            return null;
        }
    }
}

