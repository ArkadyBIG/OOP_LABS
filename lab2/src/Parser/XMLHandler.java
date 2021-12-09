package Parser;

import Knife.Knifes;
import Knife.Knife;
import Knife.KnifeType;
import Knife.BladeMaterial;
import Knife.HandleMaterial;
import Knife.WoodType;


public class XMLHandler {
    private Knife knife;
    private Knifes knifes;
    public String name;

    public XMLHandler() {
        knife = new Knife();
        knifes = new Knifes();
        name = "Knife";
    }

    public Knife getKnife(){
        return knife;
    }

    public Knifes getKnifes(){
        return knifes;
    }

    public void setTag( String element, String value){
        switch (element.toLowerCase()) {
            case "knife" : knife = new Knife();
            case "id" : knife.id = value;
            
            case "type" : knife.type = KnifeType.valueOf(value);
            case "handy" : knife.handy = Integer.parseInt(value);
            case "origin" : knife.origin = value;
            case "blade_length_cm" : knife.visual.blade_length_cm = Integer.parseInt(value);
            case "blade_width_mm" : knife.visual.blade_width_mm = Integer.parseInt(value);
            case "blade_material" : knife.visual.blade_material = BladeMaterial.valueOf(value);
            case "handle_material" : knife.visual.handle_material = HandleMaterial.valueOf(value);
            case "wood_type" : knife.visual.wood_type = WoodType.valueOf(value);
            case "bloodstream" : knife.visual.bloodstream = Boolean.parseBoolean(value);
            case "value" : knife.value = Boolean.parseBoolean(value);
            default : {}
        }
    }

    public void endTag(String element){
        if(element.equalsIgnoreCase("knife")) {
            knifes.add(knife);
            knife = new Knife();
        }
    }
}


