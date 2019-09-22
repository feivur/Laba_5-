package laba_4;

import exceptions.PowerException;
import moves.Carry;
import moves.Move;

public class Wind extends APhenomenon {

    private APlace lastPlace;

    public Wind(String name, int power) {
        super(name, power);
    }

    @Override
    public void move(Move method, APlace destination) {
        lastPlace = destination;
        System.out.printf("[%s] переместился -> [%s]\n", getName(), lastPlace.getName());

    }

    @Override
    public void move(Move method, Nameable subject, APlace destination) {
        // не делает так
    }

    @Override
    public String where() {
        return lastPlace.getName();
    }

    public void carry(AHuman who, APlace where) throws PowerException {
        if (who.weight <= this.power) {
            lastPlace = where;
            System.out.printf("[%s] неожиданно понес [%s] -> [%s]\n", getName(), who.getName(), lastPlace.getName());
            who.move(new Carry(), where);
        } else {
            throw new PowerException();
        }
    }

}
