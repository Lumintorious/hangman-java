import java.util.Random;

public class Hangman {

    public static void main(String[] args) {
        Game game = new Game(WordBank.getRandomWord());
        Prompter prompter = new Prompter(game);
        game.setPrompter(prompter);
        prompter.promptForGuess();
        game.gameLoop();
    }
}