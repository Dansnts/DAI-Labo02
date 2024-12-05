package ch.heig.dai;

import picocli.CommandLine;

@CommandLine.Command(
        name = "root",
        description = "Root command to start Server or Client.",
        subcommands = {Server.class, Client.class}
)
public class Root implements Runnable {

    @Override
    public void run() {
        System.out.println("Usage: java -cp <your-jar> ch.heig.dai.Root [server|client]");
        System.out.println("Use --help for detailed information.");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Root()).execute(args);
        System.exit(exitCode);
    }
}
