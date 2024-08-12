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
        /* In Camel Cards, you get a list of hands, and your goal is to order them based on the strength of each hand. A hand consists of five cards labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2. The relative strength of each card follows this order, where A is the highest and 2 is the lowest.
        Every hand is exactly one type. From strongest to weakest, they are:
        
        Five of a kind, where all five cards have the same label: AAAAA
        Four of a kind, where four cards have the same label and one card has a different label: AA8AA
        Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
        Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
        Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
        One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
        High card, where all cards' labels are distinct: 23456
        
        Hands are primarily ordered based on type. If two hands have the same type, a second ordering rule takes effect. Start by comparing the first card in each hand. If these cards are different, the hand with the stronger first card is considered stronger. If the first card in each hand have the same label, however, then move on to considering the second card in each hand, then the third, then the fourth, then the fifth.
        To play Camel Cards, you are given a list of hands and their corresponding bid (your puzzle input). Each hand wins an amount equal to its bid multiplied by its rank, where the weakest hand gets rank 1, the second-weakest hand gets rank 2, and so on up to the strongest hand. Now, you can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank.
        Find the rank of every hand in your set. What are the total winnings? */

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
        /* To make things a little more interesting, the Elf introduces one additional rule. Now, J cards are jokers - wildcards that can act like whatever card would make the hand the strongest type possible.
        To balance this, J cards are now the weakest individual cards, weaker even than 2.
        J cards can pretend to be whatever card is best for the purpose of determining hand type; for example, QJJQ2 is now considered four of a kind. However, for the purpose of breaking ties between two hands of the same type, J is always treated as J, not the card it's pretending to be.
        Using the new joker rule, find the rank of every hand in your set. What are the new total winnings? */

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
