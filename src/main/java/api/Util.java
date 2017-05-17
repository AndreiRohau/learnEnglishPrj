package api;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class Util {
    private static final Logger log = Logger.getLogger(Util.class.getName());

    public static String scannr(){
        Scanner sc = new Scanner(System.in);
        String scanned = sc.next();
        return scanned;
    }

    public static void outWrite(String smth) {
        log.info("\n\t"+smth);
    }

    public static void pressToContinue(){
        outWrite("Press any button to continue...");
        scannr();
    }

}
