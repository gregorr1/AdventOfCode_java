package Naloga7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Naloga7 {

    public static List<String> getInput() {
        String path = "input/input7.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga7_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/7 */

        List<String> input = getInput();
        List<Hand> hands = new ArrayList<>();
        for (String line : input) {
            String[] substrings = line.split(" ");
            int bid = 0;
            try {
                bid = Integer.parseInt(substrings[1]);
            } catch (NumberFormatException e) {
                System.out.println("Input not in correct format.");
            }
            Hand hand = new Hand(substrings[0], bid);
            hands.add(hand);
        }

        Collections.sort(hands, Comparator.comparing(Hand::getStrength));
        int totalWinnings = 0;
        int bidMultiplier = 1;
        for (Hand hand : hands) {
            totalWinnings += bidMultiplier * hand.getBid();
            bidMultiplier++;
        }
        System.out.println("Total winnings are " + totalWinnings);
    }

    public static void naloga7_2() {
        
        List<String> input = getInput();
        List<JokerHand> hands = new ArrayList<>();
        for (String line : input) {
            String[] substrings = line.split(" ");
            int bid = 0;
            try {
                bid = Integer.parseInt(substrings[1]);
            } catch (NumberFormatException e) {
                System.out.println("Input not in correct format.");
            }
            JokerHand hand = new JokerHand(substrings[0], bid);
            hands.add(hand);
        }

        Collections.sort(hands, Comparator.comparing(JokerHand::getStrength));
        int totalWinnings = 0;
        int bidMultiplier = 1;
        for (JokerHand hand : hands) {
            totalWinnings += bidMultiplier * hand.getBid();
            bidMultiplier++;
        }
        System.out.println("Total winnings are " + totalWinnings);
    }
}
