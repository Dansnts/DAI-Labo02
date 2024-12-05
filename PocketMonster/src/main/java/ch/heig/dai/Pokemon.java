package ch.heig.dai;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Pokemon with various attributes such as ID, name, level, type, moveset, and stats.
 */
public class Pokemon {
    private int pokedexID; // Unique ID of the Pokemon in the Pokedex
    private String name; // Name of the Pokemon
    private int level; // Level of the Pokemon
    private Elements element; // Element type of the Pokemon
    private ArrayList<Move> moveset; // List of moves the Pokemon can use
    private Stats stats; // Stats of the Pokemon (HP, attack, defense, etc.)
    public int actualHealth; // Current health of the Pokemon

    /**
     * Constructs a new Pokemon with the specified attributes.
     *
     * @param PokedexID The unique ID of the Pokemon.
     * @param name      The name of the Pokemon.
     * @param level     The level of the Pokemon.
     * @param element   The element type of the Pokemon.
     * @param moveset   The list of moves the Pokemon can use.
     * @param stats     The stats of the Pokemon.
     */
    public Pokemon(int PokedexID, String name, int level, Elements element, ArrayList<Move> moveset, Stats stats) {
        this.pokedexID = PokedexID;
        this.name = name;
        this.level = level;
        this.element = element;
        this.moveset = moveset;
        this.stats = stats;
        this.actualHealth = stats.getHp();
    }

    /**
     * Gets the Pokedex ID of the Pokemon.
     *
     * @return The Pokedex ID.
     */
    public int getPokedexID() {
        return pokedexID;
    }

    /**
     * Creates a new Pokemon interactively using user input.
     *
     * @return A new Pokemon instance.
     */
    public Pokemon createPokemon() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Pokemon ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        System.out.println("Enter Pokemon Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Pokemon Level:");
        int level = scanner.nextInt();

        System.out.println("Choose a Type:");
        element.printMoves();
        scanner.nextLine();
        Elements elements = new Elements(scanner.nextLine());

        System.out.println("How many moves? [1-4]");
        int nbMoves = scanner.nextInt();
        Move move = moveset.get(nbMoves);

        for (int i = 1; i <= nbMoves; i++) {
            System.out.println("Enter the move n°" + i + " : ");
            System.out.println("Name : ");
            String moveName = scanner.nextLine();

            System.out.println("Type : ");
            element.printMoves();
            String moveTypeName = scanner.nextLine();
            Type moveType = element.getType(moveTypeName);

            System.out.println("Number of PPs : ");
            int movePP = scanner.nextInt();

            System.out.println("Precision [5 - 100] : ");
            int movePrecision = scanner.nextInt();

            System.out.println("Damage : ");
            int moveDamage = scanner.nextInt();

            System.out.println("Is your move using special attack? ");
            boolean moveIsUsing = scanner.nextBoolean();

            moveset.add(new Move(moveName, movePP, movePrecision, moveType, moveDamage, moveIsUsing));
        }

        Pokemon newPokemon = new Pokemon(id, name, level, elements, moveset, stats);
        System.out.println("Pokemon created successfully!");

        return newPokemon;
    }

    /**
     * Prints the details of the Pokemon to the console.
     */
    public void printPokemon() {
        System.out.println("----------------------------------------------");
        System.out.println(this.name + " #" + this.pokedexID);
        System.out.println("Type : " + this.element.typeToString());
        System.out.println("Level : " + this.level);
        System.out.println("Moveset : ");
        for (Move move : this.moveset) {
            System.out.println(move.printMove());
        }
        System.out.println();
        System.out.println("----------------------------------------------");
    }

    /**
     * Returns the details of the Pokemon as a formatted string.
     *
     * @return A string containing the Pokemon's details.
     */
    public String showPokemon() {
        String temp = "";
        temp += "----------------------------------------------\n";
        temp += this.name + " #" + this.pokedexID + "\n";
        temp += "Type : " + this.element.typeToString() + "\n";
        temp += "Level : " + this.level + "\n";
        temp += "Stats : " + this.stats.printStats() + "\n";
        temp += "Moveset : \n";
        for (Move move : this.moveset) {
            temp += move.printMove() + "\n";
        }
        temp += "\n";
        temp += "----------------------------------------------\n";
        return temp;
    }

    /**
     * Gets the name of the Pokemon.
     *
     * @return The name of the Pokemon.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the stats of the Pokemon.
     *
     * @return The stats of the Pokemon.
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Gets the moveset of the Pokemon.
     *
     * @return The moveset of the Pokemon.
     */
    public ArrayList<Move> getMoveset() {
        return moveset;
    }

    /**
     * Gets the element type of the Pokemon.
     *
     * @return The element type of the Pokemon.
     */
    public Elements getType() {
        return element;
    }
}
