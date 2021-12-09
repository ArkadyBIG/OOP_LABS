package Knife;

import java.util.ArrayList;

public class Knifes extends ArrayList<Knife> {

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String offset) {
        StringBuilder string = new StringBuilder();
        string.append(offset).append("[\n");
        for (Knife knife : this) {
            string.append(knife.toString(offset + "  ")).append(",\n");
        }
        string.append(offset).append("]");
        return string.toString();
    }
}
