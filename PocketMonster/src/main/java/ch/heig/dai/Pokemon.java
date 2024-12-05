package ch.heig.dai;

import java.util.ArrayList;
import java.util.Scanner;

public class Pokemon {
    private int pokedexID;
    private String name;
    private int level;
    private Elements element;
    private ArrayList<Move> moveset;
    private Stats stats;
    public int actualHealth;

    public Pokemon(int PokedexID, String name, int level, Elements element, ArrayList<Move> moveset, Stats stats) {
        this.pokedexID = PokedexID;
        this.name = name;
        this.level = level;
        this.element = element;
        this.moveset = moveset;
        this.stats = stats;
        this.actualHealth = stats.getHp();
    }

    public int getPokedexID() {
        return pokedexID;
    }

    public Pokemon createPokemon() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Pokemon ID:");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        System.out.println("Enter Pokemon Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Pokemon Level:");
        int level = scanner.nextInt();

        System.out.println("Choose a Type:");
        element.printMoves();
        scanner.nextLine();
        Elements elements = new Elements(scanner.nextLine());

        System.out.println("How many moves ? [1-4]");
        int nbMoves = scanner.nextInt();
        Move move = moveset.get(nbMoves);

        for(int i = 1; i <= nbMoves; i++){
            System.out.println("Enter the move n°" + i + " : ");
            System.out.println("Name : ");
            String moveName = scanner.nextLine();

            System.out.println("Type : ");
            element.printMoves();
            String moveTypeName = scanner.nextLine();
            Type moveType = element.getType(moveTypeName);

            System.out.println("Number of PPs : ");
            int movePP = scanner.nextInt();

            System.out.println("Precison [5 - 100] : ");
            int movePrecision = scanner.nextInt();

            System.out.println("Damage : ");
            int moveDamage = scanner.nextInt();

            System.out.println("Is your move using attack spe?: ");
            boolean moveIsung = scanner.nextBoolean();

            moveset.add(new Move(moveName,movePP,movePrecision,moveType,moveDamage,moveIsung));
        }

        Pokemon newPokemon = new Pokemon(id, name, level, elements, moveset, stats);
        System.out.println("Pokemon created successfully!");

        return newPokemon;
    }

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

    public String showPokemon(){
        String temp = "";
        temp +="----------------------------------------------\n";
        temp +=this.name + " #" + this.pokedexID +"\n";
        temp +="Type : " + this.element.typeToString()+"\n";
        temp +="Level : " + this.level+"\n";
        temp +="Stats : " + this.stats.printStats() + "\n";
        temp +="Moveset : \n";
        for (Move move : this.moveset) {
            temp += move.printMove()+"\n";
        }
        temp += "\n";
        temp +="----------------------------------------------\n";
        return temp;
    }

    public String getName(){
        return name;
    }

    public Stats getStats(){
        return stats;
    }

    public ArrayList<Move> getMoveset(){
        return moveset;
    }

    public Elements getType(){
        return element;
    }
}
