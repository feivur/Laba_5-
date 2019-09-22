package laba_4;

public abstract class APhenomenon implements Nameable, Movable {
    private String name;
    int power;

    public APhenomenon(String name, int power) {
        setName(name);
        setPower(power);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String getName() {
        return name;
    }

}
