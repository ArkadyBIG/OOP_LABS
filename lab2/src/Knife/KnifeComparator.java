package Knife;

import java.util.Comparator;

public class KnifeComparator implements Comparator<Knife> {

    @Override
    public int compare(Knife o1, Knife o2) {
        return o1.compareTo(o2);
    }
}
