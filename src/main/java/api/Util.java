package api;

import java.util.Scanner;
import java.util.logging.Logger;


public class Util {
    private static final Logger log = Logger.getLogger(Util.class.getName());

    static String scannr(){
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    public static void outWrite(String smth) {
        log.info("\n\t"+smth);
    }

    static void pressToContinue(){
        outWrite("Press any button to continue...");
        scannr();
    }

}
