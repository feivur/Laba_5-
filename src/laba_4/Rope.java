package laba_4;

import exceptions.UnitializedException;

public class Rope implements Nameable {

    String name = "Веревка";
    AHuman end;

    @Override
    public void setName(String name) throws UnitializedException {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
