import java.util.Random;

class Game {
    public static final int MAX_TRIES = 7;
    private Prompter prompter;
    private String answer;
    private String hits;
    private String misses = "";

    public Game(String answer) {
        this.answer = answer.toLowerCase();
        hits = "";
        misses = "";
    }

    public void setPrompter(Prompter prompter) {
        this.prompter = prompter;
    }

    private char normalizeGuess(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException("A letter is required!");
        }
        letter = Character.toLowerCase(letter);
        if (hits.indexOf(letter) != -1 || misses.indexOf(letter) != -1) {
            throw new IllegalArgumentException(letter + " has already been guessed!");
        }
        return letter;
    }

    private char normalizeRetry(String confirm) {
        confirm = confirm.toLowerCase();
        if (confirm.length() == 0 || confirm.charAt(0) != 'y' && confirm.charAt(0) != 'n') {
            throw new IllegalArgumentException("Please enter \"Y\" or \"N\"");
        }
        return confirm.charAt(0);
    }


    public boolean reTry(String confirm) {
        try {
            if (normalizeRetry(confirm) == 'y') {
                return true;

            } else {
                return false;
            }
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            //iae.printStackTrace();
            return prompter.promptForRetry();
        }
    }

    public boolean applyGuess(char letter) {
        letter = normalizeGuess(letter);
        boolean isHit = answer.indexOf(letter) != -1;
        if (isHit) {
            hits += letter;
        } else {
            misses += letter;
        }
        return isHit;
    }

    public boolean applyGuess(String letter) {
        if (letter.length() == 0) {
            throw new IllegalArgumentException("A letter is required!");
        }
        return applyGuess(letter.charAt(0));
    }

    public String getCurrentProgress() {
        String progress = "";
        for (char letter : answer.toCharArray()) {
            char display = '-';
            if (hits.indexOf(letter) != -1) {
                display = letter;
            }
            progress += display;
        }
        return progress;
    }

    public int getCurrentTries() {
        return MAX_TRIES - misses.length();
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isWon() {
        return getCurrentProgress().indexOf('-') == -1;
    }

    public void gameLoop() {
        while (prompter.promptForRetry()) {
            Game game = new Game(WordBank.getRandomWord());
            Prompter prompter = new Prompter(game);
            game.setPrompter(prompter);
            prompter.promptForGuess();
        }
    }
}