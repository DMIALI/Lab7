package Utils;


import java.util.Scanner;

/**
 * Repeat input if input is wrong
 */
public class RepeatInput {
    public static String go(String value, String message, String errMessage, Operation func, Scanner scanner, Printer printer) {
        while (!func.execute(value)) {
            printer.errPrintln(errMessage);
            printer.outPrint(message);
            value = scanner.nextLine();
        }
        return value;
    }
}