package laba_4;

import moves.Move;

public interface Movable {
    void move(Move method, APlace destination);
    void move(Move method, Nameable subject, APlace destination);
    String where();
}
