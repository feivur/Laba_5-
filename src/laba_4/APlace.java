package laba_4;

import exceptions.UnitializedException;

public abstract class APlace implements Nameable {
    private String name;

    public APlace(String name) throws UnitializedException {
        setName(name);
        int weight;
    }

    //private List<laba_4.AHuman> humans = new ArrayList<>();

    public void setName(String name) throws UnitializedException {
        if (name==null || name.isEmpty())
            throw new UnitializedException( String.format("%s name must be set", getClass().getSimpleName()));
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    //public void putHuman(laba_4.AHuman human) {
    //    humans.add(human);
    // }

    // public boolean removeHuman(laba_4.AHuman human) {
    //    return humans.remove(human);
    //}

    // public void existHuman(laba_4.AHuman human) {
    //    boolean exist = humans.contains(human);
    //    System.out.printf("[%s] %s находится в [%s]\n", human.getName(), exist?"":"не", getName());
    // }

}
