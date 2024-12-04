package ch.heig.dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class Battle {
    private static final String ENDOFLINE = "\n";
    private Pokemon activePokemon1;
    private Pokemon activePokemon2;
    private Pokemon[] pokeTeam1;
    private Pokemon[] pokeTeam2;
    private BufferedWriter out1;
    private BufferedWriter out2;
    private BufferedReader in1;
    private BufferedReader in2;
    private Trainer trainer1;
    private Trainer trainer2;
    private int round;
    private boolean turn; // if turn == true then it is the turn of trainer1
    private Random random = new Random();
    private boolean gameOver;


    public Battle(BufferedReader inTrainer1,
                  BufferedWriter outTrainer1,
                  Trainer trainer1,
                  BufferedReader inTrainer2,
                  BufferedWriter outTrainer2,
                  Trainer trainer2) {
        this.trainer1 = trainer1;
        this.trainer2 = trainer2;
        this.in1 = inTrainer1;
        this.in2 = inTrainer2;
        this.out1 = outTrainer1;
        this.out2 = outTrainer2;
        this.pokeTeam1 = trainer1.getPokemons();
        this.pokeTeam2 = trainer2.getPokemons();
        this.round = 0;

        // Do the Sockets for communication here
    }

    public void fighting() throws IOException {
        out1.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        out2.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        out1.flush();
        out2.flush();
        this.fightSetup(out1, in1, pokeTeam1, activePokemon1);
        out1.write(trainer1.getName() + " throw " + activePokemon1.getName() + " onto the battlefield" + ENDOFLINE);
        out2.write(trainer1.getName() + " throw " + activePokemon1.getName() + " onto the battlefield" + ENDOFLINE);
        this.fightSetup(out2, in2, pokeTeam2, activePokemon2);
        out1.write(trainer2.getName() + " throw " + activePokemon2.getName() + " onto the battlefield" + ENDOFLINE);
        out2.write(trainer2.getName() + " throw " + activePokemon2.getName() + " onto the battlefield" + ENDOFLINE);
        out1.flush();
        out2.flush();
        if (activePokemon1.getStats().getSpeed() > activePokemon2.getStats().getSpeed()) {
            turn = true;
        } else {
            if (activePokemon2.getStats().getSpeed() > activePokemon1.getStats().getSpeed()) {
                turn = false;
            } else {
                if (Math.random() >= 0.5){
                    turn = true;
                } else {
                    turn = false;
                }
            }
        }

        while(!gameOver){
            turn(turn);




        }




        activePokemon2 = trainer2.getPokemons()[0];


    }

    private void fightSetup(BufferedWriter out, BufferedReader in, Pokemon[] pokeTeam, Pokemon activePokemon) throws IOException {
        out.write("The battle is starting you may choose your active pokemon" + ENDOFLINE);
        out.write("Here is your team:" + ENDOFLINE);
        for (int i = 0; i < pokeTeam.length; i++) {
            out.write(i + ". " + pokeTeam[i].getName() + ENDOFLINE);
        }
        out.flush();
        String message = in.readLine();
        boolean response = Integer.parseInt(message) <= pokeTeam.length ;
        if (response) {
            activePokemon = pokeTeam[Integer.parseInt(message) - 1];
        } else {
            out.write("ERROR: message entered wasn't allowed." + ENDOFLINE);
            out.write("please enter the number of the pokemon you wish to put in the active position" + ENDOFLINE);
            out.flush();
        }

    }

    private void turn(boolean turn) throws IOException {
        BufferedWriter out = turn? out1 : out2;
        BufferedReader in = turn? in1 : in2;
        BufferedWriter outEnnemy = turn? out2 : out1;

        out.write("this is your turn." + ENDOFLINE);
        outEnnemy.write("this is the turn of your ennemy." + ENDOFLINE);
        outEnnemy.flush();

    }
}
