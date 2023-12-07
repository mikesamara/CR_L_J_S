package cr.Java.Service;

import cr.Java.Animals.*;

import java.sql.Date;
import java.util.Scanner;

public class Validate {
    public static Animal getTypeAnimal(String type, String name, Date birthday, String commands) {
        switch (type.toUpperCase()) {
            case "Кошка" -> {
                return new Cat(name, birthday, commands);
            }
            case "Собака" -> {
                return new Dog(name, birthday, commands);
            }
            case "Хомяк" -> {
                return new Hamster(name, birthday, commands);
            }
            case "Лошадь" -> {
                return new Horse(name, birthday, commands);
            }
            case "Осел" -> {
                return new Donkey(name, birthday, commands);
            }
            case "Верблюд" -> {
                return new Camel(name, birthday, commands);
            }
            default ->
                throw new IllegalStateException("Нет такого животного");
        }
    }

    public static StringBuilder setCommands(Scanner scanner) {
        StringBuilder commands = new StringBuilder();

        while (true) {
            System.out.print("Введите комманду (прим:Прыжок) -> ");
            String command = scanner.next().strip().trim();
            commands.append(command);
            
           
                break;
            }
          return commands;  
        }
        
    }

