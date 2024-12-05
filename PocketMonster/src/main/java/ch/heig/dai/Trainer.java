package ch.heig.dai;
import java.util.Scanner;



public class Trainer {
    public static final int MAX_POKEMONS = 6;
    private int noID;
    private String name;
    private int nbPokemon;
    private Pokemon[] pokemons = new Pokemon[MAX_POKEMONS];
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
        this.money = money;
        this.nbPokemon = pokemons.length;
        for (int i = 0; i < pokemons.length; i++) {
            this.pokemons[i] = pokemons[i];
        }

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

    public Pokemon[] getPokemons(){
        return pokemons;
    }

    public String getName(){
        return name;
    }

    public void setPokemons(Pokemon pokemons, int nbr){
        if (this.nbPokemon == 6){
            this.pokemons[nbr] = pokemons;
        } else {
            this.pokemons[this.nbPokemon] = pokemons;
            this.nbPokemon++;
        }
    }

    public String printPokemon(){
        String temp = "";
        for (int i = 0; i < this.nbPokemon; i++) {
            temp += pokemons[i].showPokemon() + "\n";
        }
        return temp;
    }

}
