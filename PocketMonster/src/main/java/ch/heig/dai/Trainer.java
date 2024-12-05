package ch.heig.dai;

import java.util.Scanner;

/**
 * La classe {@code Trainer} représente un entraîneur de Pokémon avec une équipe de Pokémon, un identifiant unique,
 * un nom et un montant d'argent.
 */
public class Trainer {
    public static final int MAX_POKEMONS = 6;
    private int noID;
    private String name;
    private int nbPokemon;
    private Pokemon[] pokemons = new Pokemon[MAX_POKEMONS];
    private int money;

    /**
     * Affiche les informations sur l'entraîneur, son identifiant, son argent et ses Pokémon.
     */
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

    /**
     * Constructeur pour initialiser un entraîneur avec un identifiant, un nom, une liste de Pokémon et un montant d'argent.
     *
     * @param noID L'identifiant unique de l'entraîneur.
     * @param name Le nom de l'entraîneur.
     * @param pokemons Un tableau de Pokémon que possède l'entraîneur.
     * @param money Le montant d'argent de l'entraîneur.
     */
    public Trainer(int noID, String name, Pokemon[] pokemons, int money) {
        this.noID = noID;
        this.name = name;
        this.money = money;
        this.nbPokemon = pokemons.length;
        for (int i = 0; i < pokemons.length; i++) {
            this.pokemons[i] = pokemons[i];
        }
    }

    /**
     * Ajoute un Pokémon à l'équipe de l'entraîneur si l'équipe n'a pas encore atteint la taille maximale.
     *
     * @param pokemon Le Pokémon à ajouter à l'équipe de l'entraîneur.
     */
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

    /**
     * Supprime un Pokémon de l'équipe de l'entraîneur en fonction de son identifiant.
     *
     * @param id L'identifiant du Pokémon à supprimer de l'équipe.
     */
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

    /**
     * Retourne la liste des Pokémon de l'entraîneur.
     *
     * @return Un tableau de Pokémon appartenant à l'entraîneur.
     */
    public Pokemon[] getPokemons(){
        return pokemons;
    }

    /**
     * Retourne le nom de l'entraîneur.
     *
     * @return Le nom de l'entraîneur.
     */
    public String getName(){
        return name;
    }

    /**
     * Retourne le nombre de Pokémon que possède l'entraîneur.
     *
     * @return Le nombre de Pokémon.
     */
    public int getNbPokemon(){
        return this.nbPokemon;
    }

    /**
     * Modifie l'équipe de Pokémon de l'entraîneur en remplaçant ou en ajoutant un Pokémon à une position spécifique.
     *
     * @param pokemons Le Pokémon à ajouter ou remplacer dans l'équipe.
     * @param nbr L'indice dans l'équipe où le Pokémon doit être ajouté ou remplacé.
     */
    public void setPokemons(Pokemon pokemons, int nbr){
        if (this.nbPokemon == 6){
            this.pokemons[nbr] = pokemons;
        } else {
            this.pokemons[this.nbPokemon] = pokemons;
            this.nbPokemon++;
        }
    }

    /**
     * Affiche les informations de tous les Pokémon de l'entraîneur.
     *
     * @return Une chaîne de caractères représentant les informations de chaque Pokémon.
     */
    public String printPokemon(){
        String temp = "";
        for (int i = 0; i < this.nbPokemon; i++) {
            temp += pokemons[i].showPokemon() + "\n";
        }
        return temp;
    }

}
