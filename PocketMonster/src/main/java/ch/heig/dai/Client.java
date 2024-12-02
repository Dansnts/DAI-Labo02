package ch.heig.dai;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import picocli.CommandLine;

@CommandLine.Command(
        name = "client",
        description = "Starts the client."
)

public class Client implements Runnable {

    @Override
    public void run() {
        System.out.println("[Client] Connecting to server...");
        start(); // Appelle la méthode existante pour démarrer le client
    }

    private static final String SERVER_ADDRESS = "localhost";  // Adresse du serveur
    private static final int SERVER_PORT = 15100;             // Port du serveur

    public static void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in);

            // Thread pour écouter les messages du serveur en continu
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage); // Affiche les messages reçus
                    }
                } catch (IOException e) {
                    System.out.println("[Client] Error reading message: " + e.getMessage());
                }
            }).start();

            // Boucle principale pour envoyer des messages au serveur
            while (true) {
                String userInput = scanner.nextLine(); // Lire l'entrée utilisateur
                out.write(userInput + "\n"); // Envoyer au serveur
                out.flush();

                // Quitte si l'utilisateur tape "adios"
                if ("adios".equalsIgnoreCase(userInput.trim())) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("[Client] Error connecting to server: " + e.getMessage());
        }
    }
}
