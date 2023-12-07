package cr.Java.Actions;

import java.util.Scanner;

public class Exit extends Mode {

    public Exit() {
        super("5", "выход из системы");
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.println("-> выход...");
        System.exit(0);
    }
}
