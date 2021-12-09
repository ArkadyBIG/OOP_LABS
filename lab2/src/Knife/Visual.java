package Knife;

public class Visual {
    public int blade_length_cm;
    public int blade_width_mm;
    public BladeMaterial blade_material;
    public HandleMaterial handle_material;
    public WoodType wood_type;
    public boolean bloodstream;

    public Visual() {
    }

    public Visual(int blade_length_cm, int blade_width_mm, BladeMaterial blade_material, HandleMaterial handle_material, WoodType wood_type, boolean bloodstream) {
        this.blade_length_cm = blade_length_cm;
        this.blade_width_mm = blade_width_mm;
        this.blade_material = blade_material;
        this.handle_material = handle_material;
        this.wood_type = wood_type;
        this.bloodstream = bloodstream;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String offset) {
        return offset + "visual: {\n" +
               offset + "  blade_length_cm: " + blade_length_cm + ",\n" +
               offset + "  blade_width_mm: " + blade_width_mm + ",\n" +
               offset + "  blade_material: " + blade_material + ",\n" +
               offset + "  handle_material: " + handle_material + ",\n" +
               offset + "  wood_type: " + wood_type + ",\n" +
               offset + "  bloodstream: " + bloodstream +"\n" +
               offset + "}";
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Visual)) {
            return false;
        }
        Visual visual = (Visual) o;
        return this.blade_length_cm == visual.blade_length_cm &&
               this.blade_width_mm == visual.blade_width_mm &&
               this.blade_material.equals(visual.blade_material) &&
               this.handle_material.equals(visual.handle_material) &&
               this.wood_type.equals(visual.wood_type) &&
               this.bloodstream == visual.bloodstream;
    }
}
