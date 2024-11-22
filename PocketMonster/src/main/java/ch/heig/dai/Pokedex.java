package ch.heig.dai;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Pokedex {
    private ArrayList<Pokemon> pokemons;


    public void sortPokedex() {
        Collections.sort(this.pokemons, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return Integer.compare(p1.getPokedexID(), p2.getPokedexID());
            }
        });
    }

    public Pokedex(String pokedex) {
        // Initialiser la liste
        pokemons = new ArrayList<Pokemon>();
        try {
            // Créer un BufferedReader pour lire le fichier
            BufferedReader reader = new BufferedReader(new FileReader(pokedex));
            String line;

            // Lire le fichier ligne par ligne
            while ((line = reader.readLine()) != null) {

                System.out.println(line);
                // Split by ';' to get primary fields of the Pokemon data
                String[] info = line.split(";");

                // Parse ID, Name, Level
                int id = Integer.parseInt(info[0].trim());
                String name = info[1].trim();
                int level = Integer.parseInt(info[2].trim());

                // Parse Elements from the 4th field, trimming outer {}
                String elementsData = info[3].trim();
                elementsData = elementsData.substring(1, elementsData.length() - 1);
                Elements element = new Elements(elementsData);


                // Parse Moveset from the 5th field, trimming outer {}
                String movesData = info[4].trim();
                movesData = movesData.substring(1, movesData.length());
                String[] moveParts = movesData.split("}"); // Split by semicolon
                ArrayList<Move> moveset = new ArrayList<>();

                int moveSize = 4;
                if (moveParts.length == moveSize) {
                    // Create the Move object using the split parts
                    moveset.add(new Move(moveParts[0],
                            Integer.parseInt(moveParts[1]),
                            Integer.parseInt(moveParts[2]),
                            element.getType(moveParts[3])));

                    // Use the move object as needed
                } else {
                    System.out.println("Invalid move data.");
                }

                // Parse Stats from the 6th field, trimming outer {}
                String statsData = info[5].trim();
                //Stats stats = parseStats(statsData);
                Stats stats = null;

                // Create a new Pokemon object and add it to the list
                Pokemon pokemon = new Pokemon(id, name, level, element, moveset, stats);
                System.out.println("Pokemon : ");
                pokemon.printPokemon();
                System.out.println();
                pokemons.add(pokemon);
            }

            // Fermer le fichier après lecture
            reader.close();

        } catch (IOException e) {
            // Gérer les exceptions si le fichier n'est pas trouvé ou s'il y a une erreur d'entrée/sortie
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
        this.sortPokedex();
    }

    public Pokemon getPokemon(int index) {
        if (index > 0 && index <= this.pokemons.size()) {
            return pokemons.get(index);
        }

        System.out.println("Error this pokemon does not exist in this Pokedex");
        return null;
    }

    public void printPokedex() {
        for (int i = 1; i <= this.pokemons.size(); i++) {
            pokemons.get(i).printPokemon();}
    }

}
