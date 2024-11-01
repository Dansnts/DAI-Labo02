package ch.heig.dai;

import java.util.Scanner;

public class Pokemon {
    private int pokedexID;
    private String name;
    private int level;
    private Elements element;
    private Move[] moveset;
    private Stats stats;

    public Pokemon(int PokedexID, String name, int level,Elements element, Move[] moveset, Stats stats) {
        this.pokedexID = PokedexID;
        this.name = name;
        this.level = level;
        this.element = element;
        this.moveset = moveset;
        this.stats = stats;
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
        Elements elements = new Elements(element.getType(scanner.nextLine()));

        System.out.println("How many moves ? [1-4]");
        int nbMoves = scanner.nextInt();
        Move move = moveset[nbMoves];

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

            moveset[i-1] = new Move(moveName,movePP,movePrecision,moveType);
        }

        Pokemon newPokemon = new Pokemon(id, name, level, elements, moveset, stats);
        System.out.println("Pokemon created successfully!");

        return newPokemon;
    }

    public void printPokemon() {
        System.out.println(this.name + " #" + this.pokedexID);
        System.out.println("Type : " + this.element.typeToString());
        System.out.println("Moveset : ");
        for (Move move : this.moveset) {
            move.printMove();
        }
        System.out.println();
    }
}
