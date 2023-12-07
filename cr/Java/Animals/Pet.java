package cr.Java.Animals;

import java.sql.Date;

public abstract class Pet extends Animal{
    public Pet(String name, Date birthday, String commands) {
        super(name, birthday, commands);
    }
}
