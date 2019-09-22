import com.google.gson.Gson;
import laba_4.APlace;

public class House extends APlace {

    int area = 0;
    Roof roof;
    Pipe pipe;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals((obj.toString()));
    }

    House(String name) {
        super(name);
        roof = new Roof("крыша");
        pipe = new Pipe("труба");
    }

    House(String name, String roofName, String pipeName) {
        super(name);
        roof = new Roof(roofName);
        pipe = new Pipe(pipeName);
    }

    static abstract class HousePart extends APlace {
        HousePart(String name) {
            super(name);
        }
    }

    public class Pipe extends HousePart {
        Pipe(String name) {
            super(name);
        }
    }

    public class Roof extends HousePart {
        Roof(String name) {
            super(name);
        }
        int color = 0;
    }
}
