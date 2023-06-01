package Utils;

import ManagerOfCommands.CommandData.PrintType;

/**
 * Class that print information in console
 */
public class Printer {
    /**
     * Print info and move next line
     * @param out information
     */
    public void outPrintln(String out){
        System.out.println(out);
    }
    public void out(String out, PrintType printType){
        if (printType == PrintType.PRINT){
            outPrint(out);
        }
        else if (printType == PrintType.PRINTLN){
            outPrintln(out);
        }
        else if (printType == PrintType.ERRPRINTLN){
            errPrintln(out);
        }
    }

    /**
     * Print info and don't move next line
     * @param out information
     */
    public void outPrint(String out){
        System.out.print(out);
    }

    /**
     * Print error
     * @param out error
     */
    public void errPrintln(String out){
        System.out.println("\u001B[31m" + out + "\u001B[0m");
    }

    /**
     * Stop printing
     * @param status
     */
    public void exit(int status){
        System.exit(status);
    }
}
