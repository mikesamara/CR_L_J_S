package cr.Java.Actions;

import cr.Java.Animals.Animal;
import cr.Java.Service.ConnectionData;
import cr.Java.Service.Validate;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Train extends Mode {

    public Train() {
        super("4", "Обучение новой команде");
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите имя (прим:Мурзик) -> ");
        String name = scanner.next().strip().trim();
        List<Animal> animals = new ConnectionData().getAnimalData("SELECT * FROM (", ") AS U WHERE U.name LIKE \"" + name + "\";");

        System.out.println(animals.toString().replaceAll("^\\[|\\]$", ""));
        System.out.print("Введите номер строки (прим:1) -> ");
        int in = scanner.nextInt() - 1;

        String commandsString = String.valueOf(Validate.setCommands(scanner));
        List<String> commands = Arrays.asList(commandsString.split("\\s*,\\s*"));
        animals.get(in).setCommands(commands);
        new ConnectionData().updateAnimalData(animals.get(in), "UPDATE " + animals.get(in).getClass().getSimpleName() + "s ");
        System.out.println("-> обновлено успешно");
    }
}
