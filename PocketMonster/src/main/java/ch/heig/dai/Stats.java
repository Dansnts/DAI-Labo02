package ch.heig.dai;

public class Stats {
    private int hp;
    private int attack;
    private int defense;
    private int precision;
    private int special;
    private int speed;

    public Stats(int hp, int attack, int defense, int precision, int special, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.precision = precision;
        this.special = special;
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed(){
        return speed;
    }

    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getSpecial() {
        return special;
    }

    public String printStats() {
        String temp = "";
        temp += "HP: " + hp + "\n";
        temp += "ATTACK: " + attack + "\n";
        temp += "DEFENSE: " + defense + "\n";
        temp += "SPECIAL: " + special + "\n";
        temp += "SPEED: " + speed + "\n";
        return temp;
    }
}
