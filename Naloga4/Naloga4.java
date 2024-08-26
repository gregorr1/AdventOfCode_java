package Naloga4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga4 {
    public static void naloga4_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/4 */

        String path = "input/input4.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        int allPoints = 0;

        // Remove the first 10 characters before the first number, then split the numbers into two sub-arrays
        for (String line : input) {
            String newLine = line.substring(10);
            String[] splitLine = newLine.split("\\|");
            String[] winningNumbers = splitLine[0].trim().split("\\s+");
            String[] yourNumbers = splitLine[1].trim().split("\\s+");
            int matches = 0;

            for (String yourNumber : yourNumbers) {
                for (String winningNumber : winningNumbers) {
                    if (winningNumber.equals(yourNumber)) {
                        matches++;
                    }
                }
            }

            double dbPoints = Math.pow(2, matches - 1);
            int points = (int)dbPoints;
            allPoints += points;
        }
        System.out.println("The total number of points is " + allPoints);
    }

    public static void naloga4_2() {
        
        String path = "input/input4.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        List<Card> cards = new ArrayList<>();

        // Remove the first 10 characters before the first number, then split the numbers into two sub-arrays. Use these to create a new Card object.
        for (String line : input) {
            String newLine = line.substring(10);
            String[] splitLine = newLine.split("\\|");
            String[] winningNumbers = splitLine[0].trim().split("\\s+");
            String[] yourNumbers = splitLine[1].trim().split("\\s+");
            Card card = new Card(winningNumbers, yourNumbers);
            cards.add(card);
        }

        for (int i = 0; i < cards.size(); i++) {
            int currentCopies = cards.get(i).getCopies();
            int currentMatches = cards.get(i).checkMatches();
            for (int j = 0; j < currentCopies; j++) {
                for (int k = currentMatches; k > 0; k--) {
                    if (i + k < cards.size()) {
                        int newCopies = cards.get(i + k).getCopies() + 1;
                        cards.get(i + k).setCopies(newCopies);
                    }
                }
            }
        }
        int allCopies = 0;
        for (Card card : cards) {
            allCopies += card.getCopies();
        }
        System.out.println("You end up with " + allCopies + " total scratchcards.");
    }
}
