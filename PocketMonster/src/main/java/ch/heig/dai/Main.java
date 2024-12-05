package ch.heig.dai;

/**
 * Main class that starts the Pokémon Octogone Edition server.
 * <p>
 * The server is started by invoking the {@link #main(String[])} method.
 * This class also includes commented-out code for initializing a Pokémon trainer and some basic Pokémon logic.
 * </p>
 */
public class Main {
    /**
     * The main method that launches the Pokémon Octogone Edition server.
     *
     * @param args Command line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // TIP: Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Pokémon Octogone Edition");

        /*
        Stats stats = new Stats(17, 15, 20, 25, 13, 100);
        Elements element = new Elements("GRASS");
        ArrayList<Move> moveset = new ArrayList<>();
        moveset.add(new Move("Tackle", 20, 95, Type.NORMAL));
        Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", 5, element, moveset, stats);
        Pokemon[] pokemons = new Pokemon[]{bulbasaur};
        Trainer defaultTrainer = new Trainer(348766483, "Red", pokemons, 10000);
        Menu menu = new Menu(defaultTrainer, "");
        menu.home();
        Menu menu = new Menu(defaultTrainer, "resources/pokemons.txt");
        */

        // Initialize and start the server
        Server server = new Server();
        server.start();
    }
}
