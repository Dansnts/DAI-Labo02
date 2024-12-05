package ch.heig.dai;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server {

    private static final int PORT = 15100;
    public static final String ENDOFLINE = "\n";
    private static final List<ClientHandler> clients = new ArrayList<>();

    // Main method to start the server
    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Listening on port " + PORT);

            // Example Pokémon and Trainer setup (can be replaced with actual logic)
            Stats stats = new Stats(17, 15, 20, 25, 13, 100);
            Elements element = new Elements("GRASS");
            ArrayList<Move> moveset = new ArrayList<>();
            moveset.add(new Move("Tackle", 20, 95, Type.NORMAL, 20, false));
            Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", 5, element, moveset, stats);
            Pokemon herbizzare = new Pokemon(1, "herbizzare", 5, element, moveset, stats);
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

    private static class ClientHandler implements Runnable {
        private static ClientHandler[] lookingToFight = new ClientHandler[0];
        private final Socket socket;
        private final BufferedReader in;
        private final BufferedWriter out;
        private String clientName;
        private Trainer trainer;

        ClientHandler(Socket socket, Trainer trainer) throws IOException {
            this.socket = socket;
            this.trainer = trainer;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        }

        @Override
        public void run() {
            try {
                boolean isRunning = true;

                while (isRunning) {
                    sendMenu();
                    String choice = in.readLine();

                    System.out.println("[DEBUG] Received input: '" + choice + "'");

                    if (choice == null || choice.trim().isEmpty()) {
                        sendMessage("[Server] Invalid input. Please try again.");
                        continue;
                    }

                    switch (choice.trim()) {
                        case "1":
                            startChat();
                            break;
                        case "2":
                            handlePokemonMenu();
                            break;
                        default:
                            sendMessage("[Server] Invalid choice. Please enter '1' or '2'.");
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("[Server] IO exception with client: " + e.getMessage());
            } finally {
                disconnect();
            }
        }

        private void sendMenu() throws IOException {
            out.write("[Server] Welcome to the Server!\n");
            out.write("Choose an option:\n");
            out.write("1. Start Chat\n");
            out.write("2. Pokémon Menu\n");
            out.write("Enter choice: \n");
            out.flush();
        }

        private void handlePokemonMenu() throws IOException {
            out.write("[Server] Pokémon Menu\n");
            out.write("Choose an option:\n");
            out.write("1. Add Pokémon\n");
            out.write("2. Remove Pokémon\n");
            out.write("3. Make Team\n");
            out.write("4. Show Pokedex\n");
            out.write("5. Exit\n");
            out.write("6. LookingToFighting\n");
            out.write("7. WaitingToFight\n");
            out.flush();

            String choice = in.readLine();
            switch (choice) {
                case "1":
                    startChat();
                    break;
                case "2":
                    removePokemon();
                    break;
                case "3":
                    makeTeam();
                    break;
                case "4":
                    showPokedex();
                    break;
                case "6":
                    setLookingToFight();
                    break;
                case "7":
                    // adding the client to the waiting list
                    ClientHandler[] temp = new ClientHandler[lookingToFight.length + 1];
                    for (int i = 0; i < lookingToFight.length; i++) {
                        temp[i] = lookingToFight[i];
                    }
                    temp[lookingToFight.length] = this;
                    lookingToFight = temp;
                    waitingToFight();
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
                    out.write( "fdsafdsaf\n");
                    if (Objects.equals(in.readLine(), "1") ){
                        // changing the waiting to make sure the client isn't here no more
                        boolean passed = false;
                        ClientHandler[] temp = new ClientHandler[lookingToFight.length - 1];
                        for (int i = 0; i < lookingToFight.length; i++) {
                            if (lookingToFight[i] == this) {
                                passed = true;
                            } else {
                                if (passed){
                                    temp[i - 1] = lookingToFight[i];
                                } else {
                                    temp[i] = lookingToFight[i];
                                }
                            }
                        }
                        lookingToFight = temp;
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
                boolean notChosen = true;
                while(notChosen){
                    String message = in.readLine();

                    try{
                        int opponent = Integer.parseInt(message);
                        notChosen = false;
                        battling(this, lookingToFight[opponent]);

                    } catch (NumberFormatException e){
                        System.out.println("[Server] Invalid opponent. Please enter the number of the opponent you wish to fight.");
                    }

                }
            } catch (IOException e){
                System.out.println("[Server] IO exception: " + e.getMessage());
            }
        }

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
            Battle fight = new Battle(first.in, first.out, first.trainer, second.in, second.out, second.trainer);
            fight.fighting();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
