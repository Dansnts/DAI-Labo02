package ch.heig.dai;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import picocli.CommandLine;

/**
 * A simple TCP client that connects to a server and allows interaction via console input.
 *
 * The client uses a separate thread to listen for messages from the server while allowing
 * the user to send messages interactively.
 */
@CommandLine.Command(
        name = "client",
        description = "Starts the client."
)
public class Client implements Runnable {

    private static final String SERVER_ADDRESS = "localhost";  // Address of the server
    private static final int SERVER_PORT = 28500;              // Port of the server

    /**
     * Entry point when the client is run as a command-line application.
     * This method is invoked by the picocli framework.
     */
    @Override
    public void run() {
        System.out.println("[Client] Connecting to server...");
        start();
    }

    /**
     * Starts the client, connecting it to the server and managing communication.
     * The client listens to messages from the server on a separate thread and
     * allows the user to send messages via console input.
     */
    public static void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in);

            // Start a thread to continuously read messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage); // Display server messages
                    }
                } catch (IOException e) {
                    System.err.println("[Client] Error reading message: " + e.getMessage());
                }
            }).start();

            // Main loop for sending messages to the server
            while (true) {
                System.out.print("> "); // Prompt for user input
                String userInput = scanner.nextLine(); // Read user input

                out.write(userInput + "\n"); // Send user input to the server
                out.flush();

                // Exit condition if user types "adios"
                if ("adios".equalsIgnoreCase(userInput.trim())) {
                    System.out.println("[Client] Disconnecting from server...");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("[Client] Error connecting to server: " + e.getMessage());
        }
    }
}
