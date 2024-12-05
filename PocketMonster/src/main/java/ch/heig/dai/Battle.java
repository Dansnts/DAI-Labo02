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
    private boolean turn;// if turn == true then it is the turn of trainer1
    private Random random = new Random();
    private boolean gameOver;
    private int turnNumber;


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
        turnNumber = 1;

        while(!gameOver){
            turnSetup();
            System.out.println(turn);
            turn = !turn;
            this.turnNumber++;
        }
        end();
    }

    private void fightSetup(BufferedWriter out, BufferedReader in, Pokemon[] pokeTeam) throws IOException {
        out.write("The battle is starting you may choose your active pokemon" + ENDOFLINE);
        out.write("Here is your team:" + ENDOFLINE);
        for (int i = 0; i < trainer.getNbPokemon(); i++) {
            out.write(i + ". " + pokeTeam[i].getName() + ENDOFLINE);
        }
        out.flush();
        String message = in.readLine();
        boolean response = Integer.parseInt(message) <= pokeTeam.length - 1 ;
        if (response) {
            if (out.equals(this.out)){
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

    private void turnSetup() throws IOException {
        if (turnNumber == 1){
            BufferedWriter tempOut = turn? this.out : outEnnemy;
            outEnnemy = turn? this.outEnnemy : this.out;
            out = turn? this.out : tempOut;
            BufferedReader tempIn = inEnnemy;
            inEnnemy = turn? this.inEnnemy : this.in;
            in = turn? this.in : tempIn;
            Trainer temp = turn? this.trainer : this.trainerEnnemy;
            trainerEnnemy = turn? this.trainerEnnemy : this.trainer;
            trainer = turn? this.trainer : temp;
            Pokemon[] pokeTemp = turn ? this.pokeTeam : this.pokeTeamEnnemy;
            pokeTeamEnnemy = turn? this.pokeTeamEnnemy : this.pokeTeam;
            pokeTeam = turn? this.pokeTeam : pokeTemp;
            Pokemon actualTemp = turn? this.activePokemon : this.activePokemonEnnemy;
            activePokemonEnnemy = turn? this.activePokemonEnnemy : this.activePokemon;
            activePokemon = turn? this.activePokemon : actualTemp;
        } else {
            BufferedWriter tempOut = outEnnemy;
            outEnnemy = this.out;
            out = tempOut;
            BufferedReader tempIn = inEnnemy;
            inEnnemy = this.in;
            in = tempIn;
            Trainer temp = this.trainerEnnemy;
            trainerEnnemy = this.trainer;
            trainer = temp;
            Pokemon[] pokeTemp = this.pokeTeamEnnemy;
            pokeTeamEnnemy = this.pokeTeam;
            pokeTeam = pokeTemp;
            Pokemon actualTemp = this.activePokemonEnnemy;
            activePokemonEnnemy = this.activePokemon;
            activePokemon = actualTemp;
        }




        outEnnemy.write(ENDOFLINE);
        outEnnemy.write("this is the turn of your ennemy." + ENDOFLINE);
        outEnnemy.write("STATE of the game:" + ENDOFLINE);
        outEnnemy.write("Ennemy: " + activePokemon.getName() + " (" + activePokemon.actualHealth + ")" + ENDOFLINE);
        outEnnemy.write("You : " + activePokemonEnnemy.getName() + " (" + activePokemonEnnemy.actualHealth + ")" + ENDOFLINE);
        outEnnemy.flush();
        out.write(ENDOFLINE);
        out.write("this is your turn." + ENDOFLINE);
        out.write("STATE of the game:" + ENDOFLINE);
        out.write("Ennemy: " + activePokemonEnnemy.getName() + " (" + activePokemonEnnemy.actualHealth + ")" + ENDOFLINE);
        out.write("You : " + activePokemon.getName() + " (" + activePokemon.actualHealth + ")" + ENDOFLINE);
        out.flush();
        turnChoice();

    }

    private void turnChoice () throws IOException {
        out.write(ENDOFLINE);
        out.write("please choose your next move:" + ENDOFLINE);
        out.write("- CHANGE <pokemon>" + ENDOFLINE);
        out.write("- ATTACK <move>" + ENDOFLINE);
        out.flush();

        String message = in.readLine();
        String[] parts = message.split(" ", 2);
        boolean noEntry = (parts.length < 2);
        switch (parts[0]){
            case "CHANGE":
                if (noEntry) {
                    changeNoEntry();
                } else {
                    change(parts[1]);
                }
                break;

            case "ATTACK":
                if (noEntry) {
                    attackNoEntry();
                } else {
                    attack(parts[1]);
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
        for (int i = 0; i < trainer.getNbPokemon(); i++) {
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
                outEnnemy.flush();
            }
        }
    }

    private void changeNoEntry () throws IOException {
        out.write("here is your team:" + ENDOFLINE);
        for (int i = 0; i < trainerEnnemy.getNbPokemon(); i++) {
            out.write(pokeTeam[i].getName() + ENDOFLINE);
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
            out.write(activePokemon.getMoveset().get(i).printMove());
        }
        out.flush();
        turnChoice();
    }

    private void damageCalc (Move move) throws IOException {
        if (random.nextInt(100) < move.getPrecision()) {
            double tempCalc;
            if(move.isSpe()){
                tempCalc = (double) activePokemon.getStats().getSpecial() /activePokemonEnnemy.getStats().getSpecial();
            } else {
                tempCalc = (double) activePokemon.getStats().getAttack() /activePokemonEnnemy.getStats().getDefense();
            }
            double tempTypeCalc = (move.getPower() * (move.getType().equals(activePokemon.getType().type)? 1.5 : 1) *
                                (move.getType().equals(activePokemonEnnemy.getType().weakness)? 2 : 1));
            tempCalc = tempTypeCalc * tempCalc;
            activePokemonEnnemy.actualHealth = (int) (activePokemonEnnemy.actualHealth - tempCalc);
            out.write(activePokemon.getName() + " has hit with " + move.getName() + ENDOFLINE);
            outEnnemy.write(activePokemon.getName() + "has hit with " + move.getName() + ENDOFLINE);
            out.write(activePokemonEnnemy.getName() + " has lost " + tempCalc + " hp and now has " + activePokemonEnnemy.actualHealth + ENDOFLINE);
            outEnnemy.write(activePokemonEnnemy.getName() + " has lost " + tempCalc + " hp and now has " + activePokemonEnnemy.actualHealth + ENDOFLINE);
            out.flush();
            outEnnemy.flush();
            if (activePokemonEnnemy.actualHealth <= 0){
                pokemonDead();
            }
        } else {
            out.write(activePokemon.getName() + " misses his attack" + ENDOFLINE);
            out.flush();
        }
    }

    private void pokemonDead () throws IOException {
        outEnnemy.write(activePokemonEnnemy.getName() + " is dead" + ENDOFLINE);
        int nbrDead = 0;
        for (int i = 0; i < trainerEnnemy.getNbPokemon(); i++) {
            if (pokeTeam[i].actualHealth <= 0){
                nbrDead++;
            }
        }
        if (nbrDead == trainerEnnemy.getNbPokemon()){
            outEnnemy.write("All your team is dead you have lost" + ENDOFLINE);
            out.write("The ennemy team is dead. GG you have win" + ENDOFLINE);
            outEnnemy.flush();
            out.flush();
            gameOver = true;
        } else {
            boolean hasSelected = false;
            while (!hasSelected){
                outEnnemy.write("Here is your team, please select an alive pokemon to put in the active place:" + ENDOFLINE);
                for (int i= 0; i < trainerEnnemy.getNbPokemon(); i++) {
                    outEnnemy.write(pokeTeamEnnemy[i].getName() + ENDOFLINE);
                }
                String pokemon = inEnnemy.readLine();
                int healthCount = 0;
                boolean changed = false;
                for (int i = 0; i < trainerEnnemy.getNbPokemon(); i++) {
                    if (pokeTeamEnnemy[i].getName().equals(pokemon) && pokeTeamEnnemy[i].actualHealth > 0) {
                        activePokemonEnnemy = pokeTeamEnnemy[i];
                        changed = true;
                        hasSelected = true;
                    } else {
                        if (pokeTeamEnnemy[i].getName().equals(pokemon) && pokeTeamEnnemy[i].actualHealth <= 0) {
                            healthCount++;
                        }
                    }
                }
                if (healthCount >0 && !changed) {
                    outEnnemy.write("bro this pokemon already died, don't do him like that" + ENDOFLINE);
                    outEnnemy.flush();
                    pokemonDead();
                } else {
                    if(!changed){
                        outEnnemy.write("bro you ain't got this pokemon" + ENDOFLINE);
                        outEnnemy.flush();
                        pokemonDead();
                    } else {
                        outEnnemy.write(trainer.getName() + "switched his pokemon to " + activePokemon.getName() + ENDOFLINE);
                        outEnnemy.flush();
                        out.write(trainer.getName() + "switched his pokemon to " + activePokemon.getName() + ENDOFLINE);
                        out.flush();
                    }
                }
            }
        }
    }
    public void end () throws IOException {
        for (int i = 0; i < trainerEnnemy.getNbPokemon(); i++) {
            pokeTeamEnnemy[i].actualHealth = pokeTeamEnnemy[i].getStats().getHp();
        }
        for (int i = 0; i < trainer.getNbPokemon(); i++) {
            pokeTeam[i].actualHealth = pokeTeam[i].getStats().getHp();
        }
    }
}
