import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga2 {
    public static void naloga2_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/2 */

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
