package cr.Java.Actions;

import java.util.List;
import java.util.Scanner;

import cr.Java.Service.ConnectionData;

public class Count extends Mode {

    public Count() {
        super("2", "колличество животных");
    }

    @Override
    public void execute(Scanner scanner) {
        List<Integer> counter = new ConnectionData().countAnimalData();
        System.out.format("Колличество: домашних животных - %d, вьючных животных - %d\n", counter.get(0), counter.get(1));
    }
}
