package Utils;

public class Printer {
    public void outPrintln(String out){
        System.out.println(out);
    }
    public void outPrint(String out){
        System.out.print(out);
    }
    public void errPrintln(String out){
        System.out.println("\u001B[31m" + out + "\u001B[0m");
    }
    public void exit(int status){
        System.exit(status);
    }
}
