import java.util.Scanner;

class Prompter {

    private Game game;

    public Prompter (Game game) {
        this.game = game;
    }

    public void promptForGuess() {
        Scanner scanner = new Scanner(System.in);
        while (game.getCurrentTries() > 0 && !game.isWon()){
            displayProgress();
            System.out.printf("Enter a letter: ");
            String guessInput = scanner.nextLine();
            try {
                game.applyGuess(guessInput);
            } catch(Exception iae) {
                System.out.println(iae.getMessage());
            }
        }
        gameOver();
        //promptForRetry();
    }

    public void gameOver() {
        if (game.isWon()) {
            System.out.println("Game over! You have won!");
        } else {
            System.out.printf("Game over! You've run out of tries! The correct answer was: %s\n", game.getAnswer());
        }
    }

    public boolean promptForRetry() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Try again?\nY) Yes\nN) No\n");
        String confirm = scanner.nextLine();
        return game.reTry(confirm);
    }

    private void displayProgress(){
        System.out.printf("Try to guess: %s You have %s tries left!\n", game.getCurrentProgress(), game.getCurrentTries());
    }
}