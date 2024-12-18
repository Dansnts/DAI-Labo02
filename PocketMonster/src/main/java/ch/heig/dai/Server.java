package ch.heig.dai;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import picocli.CommandLine;

/**
 * Server class that handles incoming client connections and manages Pokémon-related interactions.
 * Provides a multithreaded architecture for handling multiple clients simultaneously.
 */
@CommandLine.Command(
        name = "server",
        description = "Starts the server."
)
public class Server implements Runnable {


    /**
     * Runs the server when executed as a command.
     * Delegates to the {@code start} method to initialize the server.
     */
    @Override
    public void run() {
        System.out.println("[Server] Starting...");
        start(); // Appelle la méthode existante pour démarrer le serveur
    }

    private static final int PORT = 28500;
    private static final List<ClientHandler> clients = new ArrayList<>();

    /**
     * Starts the server and listens for incoming client connections.
     * Initializes example Pokémon and Trainer data for testing purposes.
     */

    // Main method to start the server
    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Listening on port " + PORT);

            // Example Pokémon and Trainer setup (can be replaced with actual logic)
            Stats stats = new Stats(17, 15, 20, 25, 13, 100);
            Stats stats2 = new Stats(17, 50, 20, 25, 13, 150);
            Elements element = new Elements("GRASS");
            ArrayList<Move> moveset = new ArrayList<>();
            moveset.add(new Move("Tackle", 20, 95, Type.NORMAL, 20, false));
            Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", 5, element, moveset, stats);
            Pokemon herbizzare = new Pokemon(1, "herbizzare", 5, element, moveset, stats2);
            Pokemon[] pokemons = new Pokemon[]{bulbasaur, herbizzare};
            Trainer defaultTrainer = new Trainer(348766483, "Red", pokemons, 10000);

            while (true) {
                // Accept new client connection
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, defaultTrainer);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("[Server] IO exception: " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender.
     *
     * @param message The message to be sent.
     * @param sender  The client that sent the message.
     */
    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
        System.out.println("[DEBUG] Broadcasting message: " + message);
    }

    /**
     * Inner class that handles communication with a single client.
     */
    private static class ClientHandler implements Runnable {
        private static ClientHandler[] lookingToFight = new ClientHandler[0];
        private final Socket socket;
        private final BufferedReader in;
        private final BufferedWriter out;
        private String clientName;
        private Trainer trainer;
        private Pokedex pokedex;

        /**
         * Constructs a ClientHandler instance for a connected client.
         *
         * @param socket  The client's socket.
         * @param trainer The trainer data associated with the client.
         * @throws IOException If an I/O error occurs during initialization.
         */
        ClientHandler(Socket socket, Trainer trainer) throws IOException {
            this.socket = socket;
            this.trainer = trainer;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            this.pokedex = new Pokedex();
        }


        /**
         * Handles client interaction in a separate thread.
         * Processes client requests and manages server-client communication.
         */
        @Override
        public void run() {
            try {
                boolean isRunning = true;

                while (isRunning) {
                    handlePokemonMenu();
                }
            } catch (IOException e) {
                System.out.println("[Server] IO exception with client: " + e.getMessage());
            } finally {
                disconnect();
            }
        }

        /**
         * Sends the Pokémon menu options to the client and processes their choice.
         *
         * @throws IOException If an I/O error occurs while communicating with the client.
         */
        private void handlePokemonMenu() throws IOException {
            out.write("[Server] Welcome to the Server!\n");
            out.write("Choose an option:\n");
            out.write("POKEDEX <pokemon>: Show Pokedex or the entry for a pokemon\n");
            out.write("POKEDEX ADD <pokemon>: Adds a pokemon to the pokedex\n");
            out.write("JOIN <username>: Show list of poeple waiting to battle or challenge someone \n");
            out.write("HOST: makes you wait for someone to challenge you\n");
            out.write("CHANGE <number> <pokemon>: Show you your team or switch the 'number'-ième pokemon par 'pokemon'\n");
            out.write("QUIT\n");
            out.flush();

            String choice = in.readLine();
            String[] parts = choice.split(" ", 3);
            switch (parts[0]) {
                case "1":
                    startChat();
                    break;
                case "2":
                    removePokemon();
                    break;
                case "CHANGE":
                    if (parts.length == 1){
                        out.write(trainer.printPokemon());
                    } else {
                        if (parts.length == 3){
                            out.write("OK");
                            Pokemon pokemon = pokedex.getPokemon(parts[2]);
                            if (pokemon != null){
                                if (Integer.parseInt(parts[1]) >= 1 || Integer.parseInt(parts[1]) <= 6 ){
                                    trainer.setPokemons(pokemon,Integer.parseInt(parts[1]) - 1);

                                } else {
                                    out.write("ERROR: please put a number between 1 and 6.");
                                }
                            } else {
                                out.write("ERROR " + parts[2] + " is not in the database please ask for another pokemon or create him");
                            }
                        } else {
                            out.write("ERROR :you lack one parameter");
                            out.flush();
                            handlePokemonMenu();
                        }
                    }
                    makeTeam();
                    break;
                case "POKEDEX":
                    if (parts.length == 2 && parts[1].equalsIgnoreCase("ADD")){
                        pokedex.addPokedex(out, in);
                        handlePokemonMenu();
                    } else {
                        if (parts.length == 1){
                            pokedex.printPokedex(out, in);
                            handlePokemonMenu();
                        }
                        if (parts.length == 2){
                            pokedex.printPokemon(parts[1], out);
                        } else {
                            out.write("ERROR :you have too much parameters");
                        }
                    }
                    break;
                case "JOIN":
                    if (parts.length == 2){
                        challenge(parts[1]);
                    } else {
                        if (parts.length == 1){
                            setLookingToFight();
                        } else {
                            out.write("ERROR :you have too many parameters");
                        }
                    }
                    break;
                case "HOST":
                    // adding the client to the waiting list
                    ClientHandler[] temp = new ClientHandler[lookingToFight.length + 1];
                    for (int i = 0; i < lookingToFight.length; i++) {
                        temp[i] = lookingToFight[i];
                    }
                    temp[lookingToFight.length] = this;
                    lookingToFight = temp;
                    waitingToFight();
                    break;

                case "QUIT":
                    disconnect();
                    break;
                default:
                    sendMessage("[Server] Goodbye!");
            }
        }

        private void addPokemon() throws IOException {
            out.write("[Server] Pokémon added!\n");
            out.flush();
            handlePokemonMenu();
        }

        private void removePokemon() throws IOException {
            out.write("[Server] Pokémon removed!\n");
            out.flush();
            handlePokemonMenu();
        }

        private void makeTeam() throws IOException {
            out.write("[Server] Team made!\n");
            out.flush();
            handlePokemonMenu();
        }

        private void showPokedex() throws IOException {
            out.write("[Server] Pokedex: [Bulbasaur, Squirtle, Pikachu]\n");
            out.flush();
            handlePokemonMenu();
        }

        private void startChat() {
            try {
                out.write("[Server] Enter your name: ");
                out.flush();
                clientName = in.readLine();
                System.out.println("[Server] " + clientName + " has joined the chat.");
                broadcastMessage("[Server] " + clientName + " has joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    if ("adios".equalsIgnoreCase(message.trim())) {
                        System.out.println("[Server] " + clientName + " has left.");
                        broadcastMessage("[Server] " + clientName + " has left the chat.", this);
                        break;
                    }
                    String formattedMessage = clientName + ": " + message;
                    System.out.println("[DEBUG] Broadcasting message: " + formattedMessage);
                    broadcastMessage(formattedMessage, this);
                }
            } catch (IOException e) {
                System.out.println("[Server] Connection lost with " + clientName);
            } finally {
                if (clientName != null) {
                    broadcastMessage("[Server] " + clientName + " has left the chat.", this);
                }
                disconnect();
            }
        }

        public void sendMessage(String message) {
            try {
                out.write(message + "\n");
                out.flush();
            } catch (IOException e) {
                System.out.println("[Server] Error sending message to client: " + e.getMessage());
            }
        }

        private void waitingToFight() throws IOException {
            boolean iswaiting = true;
            out.write("You are currently waiting for an opponent, if you want to stop waiting enter 1\n");
            out.flush();
            while (iswaiting) {
                out.flush();
                // wait and if he enters something to maybe quit he can but the server won't wait for a response.
                if (in.ready()){
                    if (Objects.equals(in.readLine(), "1") ){
                        // changing the waiting to make sure the client isn't here no more
                        lookingToFight = delete(this);
                        iswaiting = false;
                    }
                }
            }
        }

        private void setLookingToFight (){
            try{
                out.write("[Server] Looking to fight?\n");
                out.write("Choose your opponent:\n");
                for (int i = 0; i < lookingToFight.length; i++) {
                    out.write(i + ". " + lookingToFight[i] + "\n");
                }
                out.flush();
            } catch (IOException e){
                System.out.println("[Server] IO exception: " + e.getMessage());
            }
        }

        private void challenge(String id) throws IOException {
            try{
                int opponent = Integer.parseInt(id);
                battling(this, lookingToFight[opponent]);
            } catch (NumberFormatException e){
                out.write("ERROR Invalid opponent. Please enter the number of the opponent you wish to fight.");
            }

        }

        /**
         * Disconnects the client from the server and cleans up resources.
         */
        private void disconnect() {
            try {
                synchronized (clients) {
                    clients.remove(this);
                }
                if (clientName != null) {
                    System.out.println("[Server] Disconnected: " + clientName);
                }
                socket.close();
            } catch (IOException e) {
                System.out.println("[Server] Error closing socket for client: " + e.getMessage());
            }
        }

        private void battling(ClientHandler first, ClientHandler second) throws IOException {
            lookingToFight = delete(second);
            Battle fight = new Battle(first.in, first.out, first.trainer, second.in, second.out, second.trainer);
            fight.fighting();
            second.out.write("Because you were waiting for a fight before you will be put again, enter 1 to quit");
            ClientHandler[] temp = new ClientHandler[lookingToFight.length + 1];
            for (int i = 0; i < lookingToFight.length; i++) {
                temp[i] = lookingToFight[i];
            }
            temp[lookingToFight.length] = second;
            lookingToFight = temp;

        }

        private ClientHandler[] delete(ClientHandler client){
            boolean passed = false;
            ClientHandler[] temp = new ClientHandler[lookingToFight.length - 1];
            for (int i = 0; i < lookingToFight.length; i++) {
                if (lookingToFight[i] == client) {
                    passed = true;
                } else {
                    if (passed){
                        temp[i - 1] = lookingToFight[i];
                    } else {
                        temp[i] = lookingToFight[i];
                    }
                }
            }
            return temp;
        }
    }

    /**
     * Main method to run the server.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        start();
    }
}
