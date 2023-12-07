package cr.Java.Actions;

import cr.Java.Animals.Animal;
import cr.Java.Service.ConnectionData;
import cr.Java.Service.Validate;

import java.sql.Date;

import java.util.Scanner;

public class Add extends Mode {

    public Add() {
        super("1", "добавить новое животное");
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите тип питомца (прим:Кошка) -> ");
        String type = scanner.next().strip().trim();

        System.out.print("Введите имя (прим:Мурзик) -> ");
        String name = scanner.next().strip().trim();

        System.out.print("Введите дату рождения (прим:yyyy-MM-dd) -> ");
        String birthdayString = scanner.next().strip().trim();
        Date birthday = Date.valueOf(birthdayString);

        String commands = String.valueOf(Validate.setCommands(scanner));

        Animal animal = Validate.getTypeAnimal(type, name, birthday, commands);
        new ConnectionData().setAnimalData(animal);
        System.out.println("-> животное добавлено успешно");
    }
}
