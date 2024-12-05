package ch.heig.dai;

/**
 * La classe {@code Stats} représente les statistiques d'un Pokémon, comprenant la vie, l'attaque, la défense,
 * la précision, le spécial et la vitesse.
 */
public class Stats {
    private int hp;
    private int attack;
    private int defense;
    private int precision;
    private int special;
    private int speed;

    /**
     * Constructeur pour initialiser les statistiques d'un Pokémon.
     *
     * @param hp       La santé (points de vie) du Pokémon.
     * @param attack   Le niveau d'attaque du Pokémon.
     * @param defense  Le niveau de défense du Pokémon.
     * @param precision Le niveau de précision du Pokémon.
     * @param special  Le niveau spécial du Pokémon.
     * @param speed    La vitesse du Pokémon.
     */
    public Stats(int hp, int attack, int defense, int precision, int special, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.precision = precision;
        this.special = special;
        this.speed = speed;
    }

    /**
     * Obtient la valeur des points de vie du Pokémon.
     *
     * @return La valeur des points de vie.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Obtient la vitesse du Pokémon.
     *
     * @return La vitesse du Pokémon.
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Obtient la valeur de l'attaque du Pokémon.
     *
     * @return La valeur de l'attaque.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Obtient la valeur de la défense du Pokémon.
     *
     * @return La valeur de la défense.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Obtient la valeur de la statistique spéciale du Pokémon.
     *
     * @return La valeur de la statistique spéciale.
     */
    public int getSpecial() {
        return special;
    }

    /**
     * Imprime un résumé de toutes les statistiques du Pokémon sous forme de chaîne de caractères.
     *
     * @return Une chaîne représentant les statistiques du Pokémon.
     */
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
