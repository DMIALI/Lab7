import ManagerOfCommands.CommandData.ClientData;
import Utils.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;

public class Authorizer {
    private Printer printer;

    public Authorizer(Printer printer) {

        this.printer = printer;
    }

    public void authorization(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            printer.outPrintln("Вы хотите зарегистрироваться или войти в существующий аккаунт?");
            printer.outPrintln("LogIn - войти \t Create - зарегистрироваться");
            String input = scanner.nextLine();
            ClientData clientData = null;
            if (input.toLowerCase().equals("LogIn".toLowerCase())){
                System.out.println("Введите имя пользователя");
                String newUsername = System.console().readLine();
                clientData = ClientData.builder()
                        .name("clientEntry")
                        .login(newUsername).build();

            } else if (input.toLowerCase().equals("Create".toLowerCase())){
                System.out.println("Введите имя пользователя");
                //String newUsername = System.console().readLine();
                String newUsername = scanner.nextLine();
                System.out.println("Введите пароль");
                //char[] newPassword = System.console().readPassword(); - хороший ввод пароля, без отображения
                char[] newPassword = scanner.nextLine().toCharArray();
                clientData = ClientData.builder()
                        .name("createNewClient")
                        .login(newUsername)
                        .passwd(newPassword).build();

                //printer.errPrintln(new String(newPassword));
            }
            try{
                //ArrayList ans = sendThenReceive(clientData);
                //outputAnswers(ans);
                Exception IOException = null;
                throw IOException ;
            } catch (Exception e){
                printer.errPrintln(String.valueOf(e));
            }
        }
    }

    public void sendClientData (ClientData clientData){

    }
}
