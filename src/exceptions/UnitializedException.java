package exceptions;

public class UnitializedException extends IllegalArgumentException {
    public UnitializedException(String what) {
        super(what);
    }
}
