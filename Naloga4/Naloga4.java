package Naloga4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga4 {
    public static void naloga4_1() {
        /* It looks like each card has two lists of numbers separated by a vertical bar (|): a list of winning numbers and then a list of numbers you have. You organize the information into a table (your puzzle input).
        As far as the Elf has been able to figure out, you have to figure out which of the numbers you have appear in the list of winning numbers. The first match makes the card worth one point and each match after the first doubles the point value of that card.
        How many points are they worth in total? */

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
        /* There's no such thing as "points". Instead, scratchcards only cause you to win more scratchcards equal to the number of winning numbers you have.
        Specifically, you win copies of the scratchcards below the winning card equal to the number of matches.
        Copies of scratchcards are scored like normal scratchcards and have the same card number as the card they copied. So, if you win a copy of card 10 and it has 5 matching numbers, it would then win a copy of the same cards that the original card 10 won: cards 11, 12, 13, 14, and 15. This process repeats until none of the copies cause you to win any more cards. (Cards will never make you copy a card past the end of the table.)
        Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, how many total scratchcards do you end up with? */

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
