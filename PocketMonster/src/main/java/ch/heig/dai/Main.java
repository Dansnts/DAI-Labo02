package ch.heig.dai;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Pockemon Octogone Edition");
/*
        Stats stats = new Stats(17,15,20,25,13,100);
        Elements element = new Elements("GRASS");
        ArrayList<Move> moveset = new ArrayList<>();
        moveset.add(new Move ("Tackle",20,95, Type.NORMAL));

>>>>>>> master
        Pokemon bulbasaur = new Pokemon(1,"Bulbasaur",5,element,moveset,stats);
        Pokemon[] pokemons = new Pokemon[]{bulbasaur};
        Trainer defaultTrainer = new Trainer(348766483, "Red",pokemons,10000);

<<<<<<< HEAD
        Menu menu = new Menu(defaultTrainer,"");
        menu.home();
=======
        Menu menu = new Menu(defaultTrainer,"resources/pokemons.txt");
*/

        Server server = new Server();

        server.start();


    }
}