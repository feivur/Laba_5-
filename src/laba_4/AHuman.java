package laba_4;

import exceptions.FeelingException;
import exceptions.UnitializedException;
import moves.Move;

public abstract class AHuman implements Nameable, Movable {
    private String name;
    private Feeling feeling = Feeling.MIDDLE;
    APlace lastPlace;
    int weight;

    public AHuman(String name, int weight) throws UnitializedException {
        setName(name);
        setWeight(weight);
    }

    @Override
    public String where() {
        return lastPlace.getName();
    }

    @Override
    public void move(Move method, Nameable subject, APlace destination) {
        lastPlace = destination;
        System.out.printf("[%s] %s по [%s] -> [%s]\n", getName(), method.actionName(), subject.getName(), lastPlace.getName());
    }

    @Override
    public void move(Move method, APlace destination) {
        lastPlace = destination;
        System.out.printf("[%s] %s -> [%s]\n", getName(), method.actionName(), lastPlace.getName());
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPlace(APlace lastPlace) {
        this.lastPlace = lastPlace;
    }

    @Override
    public void setName(String name) throws UnitializedException {
        if (name == null || name.isEmpty())
            throw new UnitializedException(String.format("%s name must be set", getClass().getSimpleName()));
        this.name = name;
    }

    public void setFeeling(Feeling newFeeling) throws FeelingException {
        //локальный
        class Feelinghecker {
            boolean checkChange(Feeling lastFeeling, Feeling newFeeling) {
                int different = Math.abs(lastFeeling.ordinal() - newFeeling.ordinal());
                return different < 2;
            }
        }
        Feelinghecker checker = new Feelinghecker();
        boolean canChange = checker.checkChange(this.feeling, newFeeling);
        if (canChange)
            this.feeling = newFeeling;
        else
            throw new FeelingException();
    }

    @Override
    public String getName() {
        return name;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + feeling.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return String.format("laba_4.AHuman name: %s, feeling: %s\n", name, feeling);
    }

    // вложенный static
    public static enum Feeling {
        NICE, MIDDLE, BAD;

        @Override
        public String toString() {
            switch (this) {
                case NICE:
                    return "Хорошее";
                case MIDDLE:
                    return "Равнодушное";
                case BAD:
                    return "Плохое";
                default:
                    return "";
            }
        }
    }

}
