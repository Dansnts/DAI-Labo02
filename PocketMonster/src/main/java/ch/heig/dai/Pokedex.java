package ch.heig.dai;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a Pokedex, which is a collection of Pokemon.
 * Provides functionality for managing and accessing the collection.
 */
public class Pokedex {
    private ArrayList<Pokemon> pokemons; // List of Pokemon in the Pokedex
    private String filePath = "resources/pokemons.txt"; // File path to the Pokemon data

    /**
     * Sorts the Pokedex by Pokemon ID in ascending order.
     */
    public void sortPokedex() {
        Collections.sort(this.pokemons, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return Integer.compare(p1.getPokedexID(), p2.getPokedexID());
            }
        });
    }

    /**
     * Loads the Pokedex from a given file path.
     *
     * @param pokedex The file path containing the Pokemon data.
     */
    public void aPokedex(String pokedex) {
        pokemons = new ArrayList<Pokemon>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pokedex));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] info = line.split(";");

                // Parse Pokemon data
                int id = Integer.parseInt(info[0].trim());
                String name = info[1].trim();
                int level = Integer.parseInt(info[2].trim());

                // Parse Elements from the 4th field
                String elementsData = info[3].trim();
                elementsData = elementsData.substring(1, elementsData.length() - 1);
                Elements element = new Elements(elementsData);

                // Additional parsing logic can go here
                String movesData = info[4].trim();
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    /**
     * Adds a new Pokemon to the Pokedex and sorts it.
     *
     * @param pokemon The Pokemon to add.
     */
    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
        this.sortPokedex();
    }

    /**
     * Retrieves a Pokemon by its index in the Pokedex.
     *
     * @param index The index of the Pokemon.
     * @return The Pokemon at the specified index, or null if invalid.
     */
    public Pokemon getPokemon(int index) {
        if (index > 0 && index <= this.pokemons.size()) {
            return pokemons.get(index);
        }

        System.out.println("Error this pokemon does not exist in this Pokedex");
        return null;
    }

    /**
     * Prints the entire Pokedex to the console.
     */
    public void printPokedex() {
        for (int i = 1; i <= this.pokemons.size(); i++) {
            pokemons.get(i).printPokemon();
        }
    }

    /**
     * Searches for a Pokemon by its name in the file.
     *
     * @param name The name of the Pokemon to find.
     * @return The Pokemon object if found, or null otherwise.
     */
    public Pokemon getPokemon(String name) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name)) {
                    System.out.println(line);
                    String[] info = line.split(";");

                    // Parse Pokemon data
                    int id = Integer.parseInt(info[0]);
                    String namePokemon = info[1];
                    int level = Integer.parseInt(info[2]);

                    String elementsData = info[3];
                    elementsData = elementsData.substring(1, elementsData.length() - 1);
                    Elements element = new Elements(elementsData);

                    String movesData = info[4];
                    movesData = movesData.substring(1, movesData.length() - 1);
                    String[] moveParts = movesData.split(";");

                    ArrayList<Move> moveset = new ArrayList<>();
                    moveset.add(new Move(info[4].substring(1),
                            Integer.parseInt(info[5]),
                            Integer.parseInt(info[6]),
                            element.getType(info[7]),
                            Integer.parseInt(info[8]),
                            Boolean.parseBoolean(info[9].substring(0, info[9].length() - 1))));

                    moveset.get(0).printMove();

                    String statsData = info[10];
                    System.out.println(statsData);
                    String statsDataTrimed = statsData.substring(1, statsData.length() - 1);
                    String[] statsParts = statsDataTrimed.split(",");

                    Stats stats = new Stats(Integer.parseInt(statsParts[0]), Integer.parseInt(statsParts[1]),
                            Integer.parseInt(statsParts[2]), Integer.parseInt(statsParts[3]), Integer.parseInt(statsParts[4]),
                            Integer.parseInt(statsParts[5]));

                    Pokemon pokemon = new Pokemon(id, name, level, element, moveset, stats);
                    System.out.println("Pokemon : ");
                    pokemon.printPokemon();
                    System.out.println();
                    return pokemon;
                }
            }
            if (!found) {
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return null;
    }

    /**
     * Adds a Pokemon to the database interactively via I/O streams.
     *
     * @param out BufferedWriter to send output to the user.
     * @param in  BufferedReader to receive input from the user.
     * @throws IOException If an I/O error occurs.
     */
    public void addPokedex(BufferedWriter out, BufferedReader in) throws IOException {
        try (BufferedWriter inPokedex = new BufferedWriter(new FileWriter(filePath, true))) {
            String pokemonData = "";

            // Gather data interactively
            out.write("Please enter the ID of the Pokemon: ");
            out.flush();
            String id = in.readLine();

            out.write("Please enter the name of the Pokemon: ");
            out.flush();
            String name = in.readLine();

            // Additional input collection logic...

            inPokedex.write(pokemonData + System.lineSeparator());
            System.out.println("Le Pokémon a été ajouté avec succès !");
            out.write("Pokemon was successfully added");
            out.flush();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du Pokémon : " + e.getMessage());
        }
    }

    /**
     * Prints all Pokemon in the Pokedex to the provided output stream.
     *
     * @param out BufferedWriter to send output to the user.
     * @param in  BufferedReader to receive input from the user.
     * @throws IOException If an I/O error occurs.
     */
    public void printPokedex(BufferedWriter out, BufferedReader in) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(";");
                out.write("  -" + info[1] + System.lineSeparator());
                out.write("\n");
                out.flush();
            }
        }
    }

    /**
     * Prints details of a specific Pokemon by name.
     *
     * @param name The name of the Pokemon.
     * @param out  BufferedWriter to send output to the user.
     * @throws IOException If an I/O error occurs.
     */
    public void printPokemon(String name, BufferedWriter out) throws IOException {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name)) {
                    found = true;
                    System.out.println(line);
                    String[] info = line.split(";");

                    // Parse and process the Pokemon data...
                    out.write("Details of Pokemon: " + info[1]);
                    out.write("\n");
                    out.flush();
                }
            }
            if (!found) {
                out.write("ERROR: The Pokemon isn't in the database");
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
