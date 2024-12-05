package ch.heig.dai;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Pokedex {
    private ArrayList<Pokemon> pokemons;
    private String filePath = "resources/pokemons.txt";

    public void sortPokedex() {
        Collections.sort(this.pokemons, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return Integer.compare(p1.getPokedexID(), p2.getPokedexID());
            }
        });
    }

    public void aPokedex(String pokedex) {
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

    public Pokemon getPokemon(String name) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name)) { // Rechercher le Pokémon par son nom
                    System.out.println(line);
                    // Split by ';' to get primary fields of the Pokemon data
                    String[] info = line.split(";");

                    // Parse ID, Name, Level
                    int id = Integer.parseInt(info[0]);
                    String namePokemon = info[1];
                    int level = Integer.parseInt(info[2]);

                    // Parse Elements from the 4th field, trimming outer {}
                    String elementsData = info[3];
                    elementsData = elementsData.substring(1, elementsData.length() - 1);
                    Elements element = new Elements(elementsData);


                    // If you want to implement pokemon in the data base with more than one ability this code is bound to change because the split split all and not only between the { in the string
                    String movesData = info[4];
                    movesData = movesData.substring(1, movesData.length() - 1);
                    String[] moveParts = movesData.split(";"); // Split by semicolon
                    ArrayList<Move> moveset = new ArrayList<>();

                    int moveSize = 6;
                    //if (moveParts.length == moveSize) {
                        // Create the Move object using the split parts
                        moveset.add(new Move(info[4].substring(1),
                                Integer.parseInt(info[5]),
                                Integer.parseInt(info[6]),
                                element.getType(info[7]),
                                Integer.parseInt(info[8]),
                                Boolean.parseBoolean(info[9].substring(0,info[9].length()-1))));

                        // Use the move object as needed
                    // } else {
                    //System.out.println("Invalid move data.");
                    //}

                    moveset.get(0).printMove();

                    // Parse Stats from the 6th field, trimming outer {}
                    String statsData = info[10];
                    System.out.println(statsData);
                    String statsDataTrimed = statsData.substring(1, statsData.length() - 1);
                    String[] statsParts = statsDataTrimed.split(",");

                    Stats stats = new Stats(Integer.parseInt(statsParts[0]), Integer.parseInt(statsParts[1])
                            ,Integer.parseInt(statsParts[2]),Integer.parseInt(statsParts[3]),Integer.parseInt(statsParts[4])
                            ,Integer.parseInt(statsParts[5]));

                    // Create a new Pokemon object and add it to the list
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

    public void addPokedex(BufferedWriter out, BufferedReader in) throws IOException {
        try (BufferedWriter inPokedex = new BufferedWriter(new FileWriter(filePath, true))) {
            String pokemonData = "";

            //ID
            out.write("\n");
            out.write("pls enter the ID of the pokémon" + "\n");
            out.flush();
            String id = in.readLine();

            //Name
            out.write("\n");
            out.write("pls enter the name of the pokémon" + "\n");
            out.flush();
            String name = in.readLine();

            //lvl
            out.write("\n");
            out.write("pls enter the LVL of the pokémon" + "\n");
            out.flush();
            String lvl = in.readLine();

            //Type
            out.write("\n");
            out.write("pls enter the Type of the pokémon (ex: WATER)" + "\n");
            out.flush();
            String type = in.readLine();
            String typed = "{" + type + "}";

            //moveset {Water Gun;20;95;WATER;60;true};{15,13,20,20,20,100}
            out.write("\n");
            out.write("pls enter the number of move of the pokemon " + "\n");
            out.flush();
            int nbr = Integer.parseInt(in.readLine());
            String move = "";
            for (int i = 0; i < nbr; i++) {

                //move name
                out.write("\n");
                out.write("pls enter the name of the move:" + i + "\n");
                out.flush();
                String moveName = in.readLine();

                //move pp
                out.write("\n");
                out.write("pls enter the pp of the move:" + i + "\n");
                out.flush();
                String movePp = in.readLine();

                //move precision
                out.write("\n");
                out.write("pls enter the precision of the move:" + i + "\n");
                out.flush();
                String movePrecision = in.readLine();

                //move type
                out.write("\n");
                out.write("pls enter the type of the move:" + i + "\n");
                out.flush();
                String moveType = in.readLine();

                //move power
                out.write("\n");
                out.write("pls enter the power of the move:" + i + "\n");
                out.flush();
                String movePower = in.readLine();

                //move isspe
                out.write("\n");
                out.write("pls enter tell us if your move is special (TRUE/FALSE) :" + i + "\n");
                out.flush();
                String moveIsspe = in.readLine();

                //{Water Gun;20;95;WATER;60;true}
                if (i > 0){
                    move += ";";
                }
                move += "{" + moveName + ";" + movePp + ";" + movePrecision + ";" + moveType + ";" + movePower + ";" + moveIsspe + "}";

            }

            out.write("\n");
            out.write("pls enter the hp of the pokémon" + "\n");
            out.flush();
            String hp = in.readLine();

            out.write("\n");
            out.write("pls enter the attack of the pokémon" + "\n");
            out.flush();
            String attack = in.readLine();

            out.write("\n");
            out.write("pls enter the defense of the pokémon" + "\n");
            out.flush();
            String defense = in.readLine();

            out.write("\n");
            out.write("pls enter the special of the pokémon" + "\n");
            out.flush();
            String special = in.readLine();

            out.write("\n");
            out.write("pls enter the speed of the pokémon" + "\n");
            out.flush();
            String speed = in.readLine();
            //{15,13,20,20,20,100}
            String stats = "{" + hp + "," + attack + "," + defense + ","  + "1," + special + "," + speed + "}";
            pokemonData = id + ";" + name + ";" + lvl + ";" + typed + ";" + move + ";" + stats;

            inPokedex.write(pokemonData + System.lineSeparator()); // Ajoute le Pokémon avec un retour à la ligne
            System.out.println("Le Pokémon a été ajouté avec succès !");
            out.write("pokémon was successfully added");
            out.flush();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du Pokémon : " + e.getMessage());
        }
    }

    public void printPokedex (BufferedWriter out, BufferedReader in) throws IOException {
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

    public void printPokemon(String name , BufferedWriter out) throws IOException {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name)) { // Rechercher le Pokémon par son nom
                    found = true;
                    System.out.println(line);
                    // Split by ';' to get primary fields of the Pokemon data
                    String[] info = line.split(";");

                    // Parse ID, Name, Level
                    int id = Integer.parseInt(info[0]);
                    String namePokemon = info[1];
                    int level = Integer.parseInt(info[2]);

                    // Parse Elements from the 4th field, trimming outer {}
                    String elementsData = info[3];
                    elementsData = elementsData.substring(1, elementsData.length() - 1);
                    Elements element = new Elements(elementsData);


                    // If you want to implement pokemon in the data base with more than one ability this code is bound to change because the split split all and not only between the { in the string
                    String movesData = info[4];
                    movesData = movesData.substring(1, movesData.length() - 1);
                    String[] moveParts = movesData.split(";"); // Split by semicolon
                    ArrayList<Move> moveset = new ArrayList<>();

                    int moveSize = 6;
                    //if (moveParts.length == moveSize) {
                    // Create the Move object using the split parts
                    moveset.add(new Move(info[4].substring(1),
                            Integer.parseInt(info[5]),
                            Integer.parseInt(info[6]),
                            element.getType(info[7]),
                            Integer.parseInt(info[8]),
                            Boolean.parseBoolean(info[9].substring(0,info[9].length()-1))));

                    // Use the move object as needed
                    // } else {
                    //System.out.println("Invalid move data.");
                    //}

                    moveset.get(0).printMove();

                    // Parse Stats from the 6th field, trimming outer {}
                    String statsData = info[10];
                    System.out.println(statsData);
                    String statsDataTrimed = statsData.substring(1, statsData.length() - 1);
                    String[] statsParts = statsDataTrimed.split(",");

                    Stats stats = new Stats(Integer.parseInt(statsParts[0]), Integer.parseInt(statsParts[1])
                            ,Integer.parseInt(statsParts[2]),Integer.parseInt(statsParts[3]),Integer.parseInt(statsParts[4])
                            ,Integer.parseInt(statsParts[5]));

                    // Create a new Pokemon object and add it to the list
                    Pokemon pokemon = new Pokemon(id, name, level, element, moveset, stats);
                    out.write(pokemon.showPokemon());
                    out.write("\n");
                    out.flush();
                }
            }
            if (!found) {
                out.write("ERROR the pokemon isnt in the database");
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

}
