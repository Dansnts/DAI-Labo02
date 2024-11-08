package ch.heig.dai;
import java.util.Scanner;

enum Choice{
    BATLLE,
    ADD_POKEMON,
    REMOVE_POKEMON,
    MAKE_TEAM,
    MENU,
    QUIT,
    SHOW_POKEDEX;
}

public class Menu {
    private String database;
    private Trainer trainer;
    private Pokedex pokedex;

    private Choice chooseMenu(Choice[] choices){

        int choice;
        Scanner scanner = new Scanner(System.in);
        int min = 1,
            max = choices.length;

        System.out.println("Choose an option : ");
        for(int i = 1; i <= choices.length; i++){
            System.out.println(i + ". " + choices[i-1].name());
        }

        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice, please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.next();
            }
        }
        return choices[choice - 1];
    }


    public Menu(Trainer trainer, String pokedex){
        if(pokedex != null || trainer != null){
            if(trainer != null){
                this.pokedex = new Pokedex(pokedex);
                this.trainer = trainer;
            }
            else {
                System.out.println("Invalid input, please enter a valid Trainer.");
            }
        }

        else {
            System.out.println("Invalid input, please enter a valid pokedex.");
        }
    }

    public void makeTeam(){
        trainer.printTrainer();

        Choice choice = chooseMenu(new Choice[]{Choice.ADD_POKEMON, Choice.REMOVE_POKEMON, Choice.MENU});

        switch(choice) {
            case ADD_POKEMON:
            case REMOVE_POKEMON:
            case MENU:
                this.home();
        }
    }

    public void start(String pokedex){

    }

    public void home(){

        if(trainer == null || pokedex == null){
            return;
        }

        Choice choice = chooseMenu(new Choice[]{Choice.BATLLE, Choice.ADD_POKEMON, Choice.MAKE_TEAM, Choice.QUIT});

        while (true){


            switch(choice){
                case BATLLE:
                case ADD_POKEMON:
                case MAKE_TEAM:
                    this.makeTeam();
                    break;
                case QUIT:

            }
        }

    }
}
