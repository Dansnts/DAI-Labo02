package ch.heig.dai;
import java.util.Arrays;
import java.util.Comparator;

public class Pokedex {
    private int nbPokemon;
    private Pokemon[] pokemons;


    public void sortPokedex() {
        Arrays.sort(this.pokemons, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return Integer.compare(p1.getPokedexID(), p2.getPokedexID());
            }
        });
    }

    public Pokedex(int nbPokemon, Pokemon[] pokemons) {
        this.nbPokemon = nbPokemon;
        this.pokemons = pokemons;
        this.sortPokedex();
    }

    public void addPokemon(Pokemon pokemon) {
        Pokemon[] newPokemons = new Pokemon[this.nbPokemon + 1];
        System.arraycopy(this.pokemons, 0, newPokemons, 0, this.nbPokemon);
        newPokemons[this.nbPokemon] = pokemon;
        this.pokemons = newPokemons;
        this.sortPokedex();
    }

    public Pokemon getPokemon(int index) {
        if (index > 0 || index <= this.nbPokemon) {
            return this.pokemons[index + 1];
        }

        System.out.println("Error this pokemon does not exist in this Pokedex");
        return null;
    }

    public void printPokedex() {
        for (int i = 1; i <= this.nbPokemon; i++) {
            this.pokemons[i].printPokemon();
        }
    }

}
