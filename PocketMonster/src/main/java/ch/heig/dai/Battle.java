package ch.heig.dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

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

    public void fighting() throws IOException {
        out.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        outEnnemy.write("WELCOME to POKEMON OCTOGONE !!!!!" + ENDOFLINE);
        out.flush();
        outEnnemy.flush();
        this.fightSetup(out, in, pokeTeam, activePokemon);
        out.write(trainer.getName() + " throw " + activePokemon.getName() + " onto the battlefield" + ENDOFLINE);
        outEnnemy.write(trainer.getName() + " throw " + activePokemon.getName() + " onto the battlefield" + ENDOFLINE);
        this.fightSetup(outEnnemy, inEnnemy, pokeTeamEnnemy, activePokemonEnnemy);
        out.write(trainerEnnemy.getName() + " throw " + activePokemonEnnemy.getName() + " onto the battlefield" + ENDOFLINE);
        outEnnemy.write(trainerEnnemy.getName() + " throw " + activePokemonEnnemy.getName() + " onto the battlefield" + ENDOFLINE);
        out.flush();
        outEnnemy.flush();
        if (activePokemon.getStats().getSpeed() > activePokemonEnnemy.getStats().getSpeed()) {
            turn = true;
        } else {
            if (activePokemonEnnemy.getStats().getSpeed() > activePokemon.getStats().getSpeed()) {
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
            turnSetup();




        }




        activePokemonEnnemy = trainerEnnemy.getPokemons()[0];


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

    private void turnSetup() throws IOException {
        out = turn? this.out : outEnnemy;
        in = turn? this.in : inEnnemy;
        outEnnemy = turn? this.outEnnemy : this.out;
        Trainer temp = turn? this.trainer : this.trainerEnnemy;
        trainerEnnemy = turn? this.trainerEnnemy : this.trainer;
        trainer = turn? this.trainer : temp;
        Pokemon[] pokeTemp = turn ? this.pokeTeam : this.pokeTeamEnnemy;
        pokeTeamEnnemy = turn? this.pokeTeamEnnemy : this.pokeTeam;
        pokeTeam = turn? this.pokeTeam : pokeTemp;
        Pokemon actualTemp = turn? this.activePokemon : this.activePokemonEnnemy;
        activePokemonEnnemy = turn? this.activePokemonEnnemy : this.activePokemon;
        activePokemon = turn? this.activePokemon : actualTemp;




        outEnnemy.write("this is the turn of your ennemy." + ENDOFLINE);
        outEnnemy.flush();
        out.write("this is your turn." + ENDOFLINE);
        out.flush();
        turnChoice();

    }

    private void turnChoice () throws IOException {
        out.write("please choose your next move:" + ENDOFLINE);
        out.write("- CHANGE <pokemon>" + ENDOFLINE);
        out.write("- ATTACK <move>" + ENDOFLINE);
        out.flush();

        String message = in.readLine();
        String[] parts = message.split(" ", 2);
        boolean noEntry = (parts.length == 2);
        switch (parts[0]){
            case "CHANGE":
                if (noEntry) {
                    change(parts[1]);
                } else {
                    changeNoEntry();
                }
                break;

            case "ATTACK":
                if (noEntry) {

                } else {

                }
                break;

            default:
                out.write("please use an eligible choice" + ENDOFLINE );
                turnChoice();
                break;
        }
    }

    private void change (String pokemon) throws IOException {
        int healthCount = 0;
        boolean changed = false;
        for (int i = 0; i < pokeTeam.length; i++) {
            if (pokeTeam[i].getName().equals(pokemon) && pokeTeam[i].actualHealth != 0) {
                activePokemon = pokeTeam[i];
                changed = true;
            } else {
                if (pokeTeam[i].getName().equals(pokemon) && pokeTeam[i].actualHealth == 0) {
                    healthCount++;
                }
            }
        }
        if (healthCount >0 && !changed) {
            out.write("bro this pokemon already died, don't do him like that" + ENDOFLINE);
            out.flush();
            turnChoice();
        } else {
            if (!changed){
                out.write("bro you ain't got this pokemon" + ENDOFLINE);
                out.flush();
                turnChoice();
            } else {
                out.write(trainer.getName() + "switched his pokemon to " + activePokemon.getName() + ENDOFLINE);
                out.flush();
                outEnnemy.write(trainer.getName() + "switched his pokemon to " + activePokemon.getName() + ENDOFLINE);
                turn = !turn;
                turnSetup();
            }
        }
    }

    private void changeNoEntry () throws IOException {
        out.write("here is your team:" + ENDOFLINE);
        for (int i = 0; i < pokeTeam.length; i++) {
            out.write(i + ". " + pokeTeam[i].getName() + ENDOFLINE);
        }
        out.flush();
        turnChoice();
    }

    private void attack (String move) throws IOException {
        for (int i = 0; i < activePokemon.getMoveset().size(); i++) {
            if (activePokemon.getMoveset().get(i).getName().equals(move)) {
                if (activePokemon.getMoveset().get(i).getActualPp() == 0){
                    out.write("this move is out of pp and is therefore useless" + ENDOFLINE);
                    out.flush();
                    turnChoice();
                } else {
                    damageCalc(activePokemon.getMoveset().get(i));
                }
            } else {
                out.write("Your pokemon cannot perform this move " + ENDOFLINE);
                out.flush();
                turnChoice();
            }
        }
    }

    private void attackNoEntry () throws IOException {
        out.write("here are the moves of your pokemon:" + ENDOFLINE);
        for (int i = 0; i < activePokemon.getMoveset().size(); i++) {
            activePokemon.getMoveset().get(i).printMove();
        }
        out.flush();
        turnChoice();
    }

    private void damageCalc (Move move) throws IOException {
        
    }
}
