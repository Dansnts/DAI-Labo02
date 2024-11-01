package ch.heig.dai;

public class Move {
    private String name;
    private int pp;
    private int precision;
    private Type type;


    public Move(String name, int pp,int precision, Type type) {
        this.name = name;
        this.pp = pp;
        this.precision = precision;
        this.type = type;
    }


    public void printMove(){
        System.out.println(this.name + " - " + this.type + " \n" + this.pp + "PP - Precision : " + this.precision + "/100");
    }
}
