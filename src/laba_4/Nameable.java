package laba_4;

import exceptions.UnitializedException;

public interface Nameable {
    void setName(String name) throws UnitializedException;
    String getName();
}
