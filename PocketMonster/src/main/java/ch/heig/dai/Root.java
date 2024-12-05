package ch.heig.dai;

import picocli.CommandLine;

/**
 * The Root command serves as the entry point for the application.
 * It provides options to start the Server or the Client via subcommands.
 */
@CommandLine.Command(
        name = "root",
        description = "Root command to start Server or Client.",
        subcommands = {Server.class, Client.class}
)
public class Root implements Runnable {

    /**
     * Executes the root command when no subcommands are specified.
     * Prints usage instructions to the console.
     */
    @Override
    public void run() {
        System.out.println("Usage: java -cp <your-jar> ch.heig.dai.Root [server|client]");
        System.out.println("Use --help for detailed information.");
    }

    /**
     * Main method to start the application.
     * Parses the provided command-line arguments and delegates execution to the appropriate subcommand.
     *
     * @param args Command-line arguments provided to the application.
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Root()).execute(args);
        System.exit(exitCode);
    }
}
