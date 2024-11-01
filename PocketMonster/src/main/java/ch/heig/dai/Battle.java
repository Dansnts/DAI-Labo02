package ch.heig.dai;

public class Battle {
    private Pokemon pokemonA;
    private Pokemon pokemonB;
    private int round;


    public Battle(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonA = pokemonA;
        this.pokemonB = pokemonB;

        this.round = 0;

        // Do the Sockets for communication here
    }
}
