package laba_4;

import moves.Pool;

public class Shorty extends AHuman {

    public Shorty(String name, int weight) {
        super(name, weight);
    }

    public void pool(Rope rope) {
        if (rope.end != null) {
            System.out.printf("[%s] предметом [%s] потянул [%s] к месту [%s]\n", getName(), rope.name, rope.end.getName(), lastPlace.getName());
            rope.end.move(new Pool(), this.lastPlace);
        }
    }

    public void take(Rope rope) {
        rope.end = this;
        System.out.printf("[%s] взялся за [%s]\n", getName(), rope.getName());
    }

}
