import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga2 {
    public static void naloga2_1() {
        /* You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
        Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games? */

        String path = "input/input2.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        String delimiters = ";|:|,";
        int sum = 0;

        for(String game : input) {
            // Calculate the info for every game (line of input)
            String[] substrings = game.split(delimiters);
            int gameID = 0;
            int maxReds = 0;
            int maxBlues = 0;
            int maxGreens = 0;
            for(String substring : substrings)
            {
                // Determine the game ID
                if(substring.contains("Game")) {
                    gameID = Integer.parseInt(substring.replace("Game ", ""));
                }

                // Determine the highest number of blue cubes shown during this game
                if(substring.contains("blue")) {
                    int blues = Integer.parseInt(substring.strip().replace(" blue", ""));
                    if(blues > maxBlues) {
                        maxBlues = blues;
                    }
                }

                // Determine the highest number of red cubes shown during this game
                if(substring.contains("red")) {
                    int reds = Integer.parseInt(substring.strip().replace(" red", ""));
                    if(reds > maxReds) {
                        maxReds = reds;
                    }
                }

                // Determine the highest number of green cubes shown during this game
                if(substring.contains("green")) {
                    int greens = Integer.parseInt(substring.strip().replace(" green", ""));
                    if(greens > maxGreens) {
                        maxGreens = greens;
                    }
                }
            }
            if(maxReds <= 12 && maxGreens <= 13 && maxBlues <= 14) {
                sum += gameID;
            }
        }
        System.out.println("The sum of the IDs of all games you can play is " + sum);
    }

    public static void naloga2_2() {
        /* As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?
        The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together.
        For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets? */

        String path = "input/input2.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        String delimiters = ";|:|,";
        int sum = 0;
        
        for(String game : input) {
            // Calculate the info for every game (line of input)
            String[] substrings = game.split(delimiters);
            int maxReds = 0;
            int maxBlues = 0;
            int maxGreens = 0;
            for(String substring : substrings)
            {
                // Determine the highest number of blue cubes shown during this game
                if(substring.contains("blue")) {
                    int blues = Integer.parseInt(substring.strip().replace(" blue", ""));
                    if(blues > maxBlues) {
                        maxBlues = blues;
                    }
                }

                // Determine the highest number of red cubes shown during this game
                if(substring.contains("red")) {
                    int reds = Integer.parseInt(substring.strip().replace(" red", ""));
                    if(reds > maxReds) {
                        maxReds = reds;
                    }
                }

                // Determine the highest number of green cubes shown during this game
                if(substring.contains("green")) {
                    int greens = Integer.parseInt(substring.strip().replace(" green", ""));
                    if(greens > maxGreens) {
                        maxGreens = greens;
                    }
                }
            }
            int power = maxBlues * maxGreens * maxReds;
            sum += power;
        }   
        System.out.println("The sum of the powers of all sets is " + sum);
    }
}
