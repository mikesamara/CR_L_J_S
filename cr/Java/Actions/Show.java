package cr.Java.Actions;

import cr.Java.Animals.Animal;
import cr.Java.Service.ConnectionData;

import java.util.List;
import java.util.Scanner;

public class Show extends Mode {

    public Show() {
        super("3", "Список животных");
    }

    @Override
    public void execute(Scanner scanner) {
        List<Animal> animals = new ConnectionData().getAnimalData("", "ORDER BY birthday;");
        System.out.println(animals.toString().replaceAll("^\\[|\\]$", ""));
    }
}
