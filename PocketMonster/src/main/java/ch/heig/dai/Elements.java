package ch.heig.dai;

import java.awt.*;

enum Type {
    GRASS,
    FIRE,
    WATER,
    NORMAL,
    //PSY,
    //POISON,
    //ELEC,
    //FLY,
    //GHOST,
    //DRAGON,
    NONE;
}



public class Elements {
    Type type;
    Type weakness;
    public static final String[] TYPES = {"GRASS", "FIRE", "WATER", "NORMAL", "PSY", "POISON", "ELEC", "FLY", "GHOST", "DRAGON", "NONE"};


    public Elements(String type){
        this.type = getType(type);
        this.weakness = getWeakness(this.type);
    }

    public Type getWeakness(Type type){
        Type typeWeakness;
        switch (type){
            case GRASS:
                typeWeakness = Type.FIRE;
                break;

            case FIRE:
                typeWeakness = Type.WATER;
                break;

            case WATER:
                typeWeakness = Type.GRASS;
                break;

            case NORMAL:
            default:
                typeWeakness = Type.NONE;
        }
        return typeWeakness;
    }

    public String typeToString(){
        return this.TYPES[this.type.ordinal()];
    }

    public Type getType(String type) {
        return switch (type.toUpperCase()) {
            case "GRASS" -> Type.GRASS;
            case "WATER" -> Type.WATER;
            case "FIRE" -> Type.FIRE;
            case "NORMAL" -> Type.NORMAL;
            default -> Type.NONE;
        };
    }

    public void printMoves(){
        for(int i = 0; i < TYPES.length; i++){
            if(i != TYPES.length - 1)
                System.out.print(", ");
            System.out.print(TYPES[i]);
        }
    }

}
