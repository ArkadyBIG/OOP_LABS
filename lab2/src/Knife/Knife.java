package Knife;

/**
 * Холодное оружие можно структурировать по следующей схеме:
 * • Type – тип (нож, кинжал, сабля и т.д.).
 * • Handy – одно или двуручное.
 * • Origin – страна производства.
 * • Visual (должно быть несколько) – визуальные характеристики: клинок (длина клинка [10 – n см], ширина клинка [10 – n мм]), материал (клинок [сталь, чугун, медь и т.д.]), рукоять (деревянная [если да, то указать тип дерева], пластик, металл), наличие кровостока (есть либо нет).
 * • Value – коллекционный либо нет.
 * Корневой элемент назвать Knife.
 */
public class Knife implements Comparable{
    public String id;
    public KnifeType type;
    public int handy;
    public String origin;
    public Visual visual;
    public boolean value;

    public Knife() {
        visual = new Visual();
    }

    @Override
    public int compareTo(Object o) {
        return id.compareTo(((Knife) o).id);
    }

    @Override
    public String toString(){
        return toString("");
    }

    public String toString(String offset) {
        return offset + "steel_arm: {\n" +
               offset + "  id: " + id + ",\n" +
               offset + "  type: " + type + ",\n" +
               offset + "  handy: " + handy + ",\n" +
               offset + "  origin: " + origin + ",\n" +
               visual.toString(offset + "  ") + ",\n" +
               offset + "  value: " + value + "\n" +
               offset + "}";

    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Knife )) return false;
        Knife knife = (Knife) o;
        return this.id.equals(knife.id) &&
               this.type.equals(knife.type) &&
               this.handy == knife.handy &&
               this.origin.equals(knife.origin) &&
               this.visual.equals(knife.visual) &&
               this.value == knife.value;
    }
}
