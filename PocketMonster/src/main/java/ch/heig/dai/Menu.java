package ch.heig.dai;


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


    public Menu(Trainer trainer, String database){
        this.trainer = trainer;
        this.database = database;
    }

    public void makeTeam(){
        trainer.printTrainer();

        System.out.println("Choose an option : ");
        System.out.println("2. ADD POKEMON");
        System.out.println("2. REMOVE POKEMON");
        System.out.println("4. MENU");



        Choice choice = Choice.ADD_POKEMON;

        switch(choice) {
            case ADD_POKEMON:
            case REMOVE_POKEMON:
            case MENU:
                return;
                //this.home();
        }
    }

    public void home(){
        System.out.println("Choose an option : ");
        System.out.println("1. BATLLE");
        System.out.println("2. ADD POKEMON");
        System.out.println("3. MAKE TEAM");
        System.out.println("4. QUIT");


        Choice choice = Choice.MAKE_TEAM;

        switch(choice){
            case BATLLE:
            case ADD_POKEMON:
            case MAKE_TEAM:
                this.makeTeam();
            case QUIT:
                return;
        }
    }
}
