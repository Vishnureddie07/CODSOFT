import java.util.Random;
import java.util.Scanner;

public class Numgame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int score = 0;
        
        System.out.println("Hello! Welcome to the Number Guessing Game!");
        
        while (true) {
            int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            System.out.println("\nI've selected a number between " + minRange + " and " + maxRange + ". Try to guess it!");
            
            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int Guess = scanner.nextInt();
                if (Guess < minRange || Guess > maxRange) {
                    System.out.println("Try again, You are out of range.");
                    continue;
                }
                attempts++;
                if (Guess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number (" + targetNumber + ") in " + attempts + " attempts.");
                    score += maxAttempts - attempts + 1;
                    break;
                } else if (Guess < targetNumber) {
                    System.out.println("Too low! Remaining Attempts: " + (maxAttempts - attempts));
                } else {
                    System.out.println("Too high! Remaining Attempts: " + (maxAttempts - attempts));
                }
            }
            System.out.print("\nDo you want to play another round? (yes/no): ");
            String playAgain = scanner.next();
            
            if (!playAgain.toLowerCase().equals("yes")) {
                break;
            }
        }
        System.out.println("Thanks for playing! Your total score is: " + score);
        scanner.close();
    }
}
