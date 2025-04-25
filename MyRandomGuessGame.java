import java.util.Random;
import java.util.Scanner;

// player class
class Player {
    String name;
    int id;
    int score;
    int highestScore;

// initializing player information
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.score = 0;
        this.highestScore = 0;
    }

// method to update scores
    public void updateScore(int score) {
        this.score += score;
        if (this.score > highestScore) {
            highestScore = this.score;
        }
    }

//display
    public void displayScore() {
        System.out.println("Player Name: " + name);
        System.out.println("Player ID: " + id);
        System.out.println("Current Score: " + score);
        System.out.println("Highest Score: " + highestScore);
    }
}

public class MyRandomGuessGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

//get player information
        System.out.print("Enter player name: ");
        String name = scanner.next();
        System.out.print("Enter player ID: ");
        int id = scanner.nextInt();

        Player player1 = new Player(name, id);
        boolean playAgain = true;

//game loop
        while (playAgain) {
            int level = 1;
            boolean playNextLevel = true;
            player1.score = 0;

            while (playNextLevel) {
                int score = playLevel(scanner, random, level);
                if (score == -1) {
                    System.out.println("You failed to guess the number. You will restart this level.");
                    // Don't increment level, restart the current level
                } else {
                    player1.updateScore(score);
                    level++;
                    if (level > 3) {
                        playNextLevel = false;
                        player1.displayScore();
                    }
                }

                if (!playNextLevel || score == -1) {
                    playAgain = askToPlayAgain(scanner);
                    if (!playAgain) {
                        player1.displayScore();
                        System.out.println("Thanks for playing!");
                    } else if (score == -1) {
                        level--; 
                        // Decrement level to restart the current level
                        playNextLevel = true;
                    }
                }
            }
        }
    }

// method to ask player if he wants to play again
    private static boolean askToPlayAgain(Scanner scanner) {
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next().toLowerCase();
        return response.equals("yes");
    }

//Methd to play
    private static int playLevel(Scanner scanner, Random random, int level) {
        System.out.println("Select a range:");
        int maxRange = 0;
        int trials = 0;
        int score = 0;
        switch (level) {
            case 1:
                System.out.println("Level 1: 0-10 (3 trials)");
                maxRange = 10;
                trials = 3;
                score = 100;
                break;
            case 2:
                System.out.println("Level 2: 0-20 (4 trials)");
                maxRange = 20;
                trials = 4;
                score = 200;
                break;
            case 3:
                System.out.println("Level 3: 0-100 (6 trials)");
                maxRange = 100;
                trials = 6;
                score = 300;
                break;
        }

// generating random number
        int numberToGuess = random.nextInt(maxRange) + 1;
        int remainingTrials = trials;

        while (remainingTrials > 0) {
            System.out.print("Guess a number between 1 and " + maxRange + ": ");
            int userGuess = scanner.nextInt();

// condision to check guess
            if (userGuess == numberToGuess) {
                System.out.println("Congratulations! You guessed the number in " + (trials - remainingTrials + 1) + " trials.");
                System.out.println("You earned " + score + " points for this level!");
                return score;
            } else if (userGuess < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }
            remainingTrials--;
            System.out.println("Remaining trials: " + remainingTrials);
            score -= 10; 
            // penalty for each incorrect guess
            if (score < 0) {
                score = 0;
            }
        }

        System.out.println("Sorry, you didn't guess the number. The number was " + numberToGuess + ".");
        System.out.println("You earned 0 points for this level.");
        return -1; 
        // return -1 to indicate game over
    }
}

