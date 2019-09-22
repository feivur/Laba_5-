import java.util.Comparator;

public class AreaComparator implements Comparator<House> {

    public int compare(House h1, House h2) {
        return Integer.compare(h1.area, h2.area);
    }
}
