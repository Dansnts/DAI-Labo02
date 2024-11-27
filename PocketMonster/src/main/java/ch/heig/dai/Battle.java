package ch.heig.dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Battle {
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

    public void fighting(){
        activePokemon2 = trainer2.getPokemons()[0];


    }
}
