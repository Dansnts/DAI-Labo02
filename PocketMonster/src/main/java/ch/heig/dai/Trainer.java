package ch.heig.dai;
import java.util.Scanner;



public class Trainer {
    public static final int MAX_POKEMONS = 6;
    private int noID;
    private String name;
    private int nbPokemon;
    private Pokemon[] pokemons;
    private int money;

    public void printTrainer() {
        System.out.println("Trainer name : " + this.name);
        System.out.println("noID : " + this.noID);
        System.out.println("Money : " + this.money + "$");
        System.out.println();
        System.out.println("Pokemons : ");
        for(Pokemon pokemon : this.pokemons){
            pokemon.printPokemon();
        }
    }

    public Trainer(int noID, String name, Pokemon[] pokemons, int money) {
        this.noID = noID;
        this.name = name;
        this.pokemons = pokemons;
        this.money = money;
        this.nbPokemon = this.pokemons.length;

    }

    public void addPokemon(Pokemon pokemon){
        if(this.nbPokemon < MAX_POKEMONS){
            this.nbPokemon++;
            Pokemon[] newPokemons = new Pokemon[this.nbPokemon];
            System.arraycopy(this.pokemons, 0, newPokemons, 0, this.nbPokemon);
            newPokemons[this.nbPokemon] = pokemon;
            this.pokemons = newPokemons;
        }

        else {
            System.out.println("Trainer cant have more than " + MAX_POKEMONS + " Pokémons.");
        }
    }

    public void removePokemon(int id){
        if(this.nbPokemon > 0){
            this.nbPokemon--;
            pokemons[id] = null;
            Pokemon[] newPokemons = new Pokemon[this.nbPokemon];
            System.arraycopy(this.pokemons, 0, newPokemons, 0, this.nbPokemon);
            this.pokemons = newPokemons;
        }
        else {
            System.out.println("Trainer doesnt have any Pokémons.");
        }
    }

}
