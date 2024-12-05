package ch.heig.dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This class represents a battle between two trainers in a Pokémon-like game. It handles the setup,
 * turns, actions, and end of the game logic.
 */
public class Battle {
    private static final String ENDOFLINE = "\n";
    private Pokemon activePokemon;
    private Pokemon activePokemonEnnemy;
    private Pokemon[] pokeTeam;
    private Pokemon[] pokeTeamEnnemy;
    private BufferedWriter out;
    private BufferedWriter outEnnemy;
    private BufferedReader in;
    private BufferedReader inEnnemy;
    private Trainer trainer;
    private Trainer trainerEnnemy;
    private int round;
    private boolean turn; // if turn == true then it is the turn of trainer1
    private Random random = new Random();
    private boolean gameOver;
    private int turnNumber;

    /**
     * Creates a new battle between two trainers, initializing their teams, communication channels,
     * and the game state.
     *
     * @param inTrainer1 BufferedReader for reading input from Trainer 1.
     * @param outTrainer1 BufferedWriter for sending output to Trainer 1.
     * @param trainer1 Trainer 1 participating in the battle.
     * @param inTrainer2 BufferedReader for reading input from Trainer 2.
     * @param outTrainer2 BufferedWriter for sending output to Trainer 2.
     * @param trainer2 Trainer 2 participating in the battle.
     */
    public Battle(BufferedReader inTrainer1,
                  BufferedWriter outTrainer1,
                  Trainer trainer1,
                  BufferedReader inTrainer2,
                  BufferedWriter outTrainer2,
                  Trainer trainer2) {
        this.trainer = trainer1;
        this.trainerEnnemy = trainer2;
        this.in = inTrainer1;
        this.inEnnemy = inTrainer2;
        this.out = outTrainer1;
        this.outEnnemy = outTrainer2;
        this.pokeTeam = trainer1.getPokemons();
        this.pokeTeamEnnemy = trainer2.getPokemons();
        this.round = 0;

        // Do the Sockets for communication here
    }

    /**
     * Starts the fighting phase of the battle, including setup, turn-by-turn gameplay,
     * and determining the winner.
     *
     * @throws IOException If an I/O error occurs during communication with trainers.
     */
    public void fighting() throws IOException {
        out.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        outEnnemy.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        out.flush();
        outEnnemy.flush();
        this.fightSetup(out, in, pokeTeam);
        out.write(trainer.getName() + " throw " + activePokemon.getName() + " onto the battlefield" + ENDOFLINE);
        outEnnemy.write(trainer.getName() + " throw " + activePokemon.getName() + " onto the battlefield" + ENDOFLINE);
        this.fightSetup(outEnnemy, inEnnemy, pokeTeamEnnemy);
        out.write(trainerEnnemy.getName() + " throw " + activePokemonEnnemy.getName() + " onto the battlefield" + ENDOFLINE);
        outEnnemy.write(trainerEnnemy.getName() + " throw " + activePokemonEnnemy.getName() + " onto the battlefield" + ENDOFLINE);
        out.flush();
        outEnnemy.flush();
        if (activePokemon.getStats().getSpeed() > activePokemonEnnemy.getStats().getSpeed()) {
            turn = true;
        } else if (activePokemonEnnemy.getStats().getSpeed() > activePokemon.getStats().getSpeed()) {
            turn = false;
        } else {
            turn = Math.random() >= 0.5;
        }
        turnNumber = 1;

        while (!gameOver) {
            turnSetup();
            System.out.println(turn);
            turn = !turn;
            this.turnNumber++;
        }
        end();
    }

    /**
     * Handles the setup of the battle for a trainer by allowing them to choose their active Pokémon.
     *
     * @param out BufferedWriter for sending messages to the trainer.
     * @param in BufferedReader for receiving input from the trainer.
     * @param pokeTeam The team of Pokémon available to the trainer.
     * @throws IOException If an I/O error occurs during communication.
     */
    private void fightSetup(BufferedWriter out, BufferedReader in, Pokemon[] pokeTeam) throws IOException {
        out.write("The battle is starting you may choose your active pokemon" + ENDOFLINE);
        out.write("Here is your team:" + ENDOFLINE);
        for (int i = 0; i < trainer.getNbPokemon(); i++) {
            out.write(i + ". " + pokeTeam[i].getName() + ENDOFLINE);
        }
        out.flush();
        String message = in.readLine();
        boolean response = Integer.parseInt(message) <= pokeTeam.length - 1;
        if (response) {
            if (out.equals(this.out)) {
                this.activePokemon = pokeTeam[Integer.parseInt(message)];
            } else {
                this.activePokemonEnnemy = pokeTeam[Integer.parseInt(message)];
            }
        } else {
            out.write("ERROR: message entered wasn't allowed." + ENDOFLINE);
            out.write("please enter the number of the pokemon you wish to put in the active position" + ENDOFLINE);
            out.flush();
        }
    }

    /**
     * Prepares the game state and trainers for the current turn.
     *
     * @throws IOException If an I/O error occurs during communication.
     */
    private void turnSetup() throws IOException {
        // Logic for turn setup...
    }

    /**
     * Handles the player's turn, presenting choices such as attacking or switching Pokémon.
     *
     * @throws IOException If an I/O error occurs during communication.
     */
    private void turnChoice() throws IOException {
        // Logic for handling turn choices...
    }

    /**
     * Resets the health of all Pokémon in both teams to their maximum at the end of the battle.
     *
     * @throws IOException If an I/O error occurs during communication.
     */
    public void end() throws IOException {
        for (int i = 0; i < trainerEnnemy.getNbPokemon(); i++) {
            pokeTeamEnnemy[i].actualHealth = pokeTeamEnnemy[i].getStats().getHp();
        }
        for (int i = 0; i < trainer.getNbPokemon(); i++) {
            pokeTeam[i].actualHealth = pokeTeam[i].getStats().getHp();
        }
    }

}
