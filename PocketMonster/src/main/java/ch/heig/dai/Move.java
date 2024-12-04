package ch.heig.dai;

public class Move {
    private String name;
    private int pp;
    private int precision;
    private Type type;
    private int actualPp;
    private int power;
    private boolean spe;


    public Move(String name, int pp,int precision, Type type, int power, boolean spe) {
        this.name = name;
        this.pp = pp;
        this.precision = precision;
        this.type = type;
        this.actualPp = pp;
        this.power = power;
        this.spe = spe;
    }

    public Move parseMove(String data){
        System.out.println("MOVES : "+ data);

        return null;
    }

    public void printMove(){
        System.out.println(this.name + " - " + this.type + " \n" + this.pp + "PP - Precision : " + this.precision + "/100\n");
    }

    public String getName(){
        return name;
    }

    public int getPp(){
        return pp;
    }

    public int getPrecision(){
        return precision;
    }

    public Type getType(){
        return type;
    }

    public int getActualPp(){
        return actualPp;
    }

    public void setActualPp(){
        this.actualPp--;
    }

    public int getPower(){
        return power;
    }

    public boolean isSpe(){
        return spe;
    }
}
