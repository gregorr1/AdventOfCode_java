import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Naloga14 {
    public static List<String> getInput() {
        String path = "input/input14.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    /* Instructions available here: https://adventofcode.com/2023/day/14 */
    public static void naloga14_1() {
        List<String> input = getInput();
        List<char[]> charInput = new ArrayList<>();
        for (String string : input) {
            charInput.add(string.toCharArray());
        }
        int sum = 0;
        
        // Tilt the input up so that all rocks move as far up as possible
        for (int i = 0; i < charInput.size(); i++) {
            for (int j = 0; j < charInput.get(i).length; j++) {
                if (charInput.get(i)[j] == '.') {
                    int tempIndex = i + 1;
                    while (tempIndex < charInput.size()) {
                        if (charInput.get(tempIndex)[j] == '#') {
                            break;
                        }
                        if (charInput.get(tempIndex)[j] == 'O') {
                            charInput.get(i)[j] = 'O';
                            charInput.get(tempIndex)[j] = '.';
                            break;
                        }
                        tempIndex++;
                    }                    
                }
                // After tilting, add the value of any rock in this place to the total sum
                if (charInput.get(i)[j] == 'O') {
                    sum += charInput.size() - i;
                }
            }
        }
        
        System.out.println("The result is " + sum);        
    }

    public static void naloga14_2() {
        List<String> input = getInput();
        
        // Create a cache and the key for the initial state of input
        Map<String, String> cache = new HashMap<>();
        String key = "";
        for (String line : input) {
            key += line + "X";
        }

        int sum = 0;
        int remainingSpins = 1_000_000_000; // Initial number of spins
        int cycle = 1; // The number of spins before a key is repeated
        boolean cycleFound = false;
        String cycleStart = "";

        // Add pairs of states of input before the spin (key) and after it (value). Once a key is found in the cache, we count the number of spins before it's found again. All the following cycles can be subtracted from the remaining spins.
        for (int i = remainingSpins; i > 0; i--) {            
            if (cache.containsKey(key)) {
                if (!cycleFound) {
                    cycleFound = true;
                    cycleStart = key;
                } else if (key == cycleStart) {
                    i = i % cycle;
                } else {
                    cycle++;
                }
                key = cache.get(key);
            } else {
                // Before a cycle is found, we create a copy of the input, spin it and insert the key-value pair in the cache
                String[] substrings = key.split("X");
                char[][] matrix = new char[substrings.length][];
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j] = substrings[j].toCharArray();
                }
                spin(matrix);
                String nextKey = "";
                for (char[] arrChar : matrix) {
                    for (char ch : arrChar) {
                        nextKey += ch;    
                    }
                    nextKey += "X";
                }
                cache.put(key, nextKey);
                key = nextKey;
            }
        }
        
        // Once there a no more remaining spins, we transform the last key in a matrix and count the load as per the instructions
        String[] substrings = key.split("X");
        for (int i = 0; i < substrings.length; i++) {
            for (int j = 0; j < substrings[i].length(); j++) {
                if (substrings[i].charAt(j) == 'O') {
                    sum += substrings.length - i;
                }
            }
        }

        System.out.println("The result is " + sum);        
    }

    // Reworked methods from the 1st task that arrange the input based on the direction of the tilt
    public static void spin(char[][] input) {
        tiltUp(input);
        tiltLeft(input);
        tiltDown(input);
        tiltRight(input);
    }
    
    public static void tiltUp(char[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '.') {
                    int tempIndex = i + 1;
                    while (tempIndex < input.length) {
                        if (input[tempIndex][j] == '#') {
                            break;
                        }
                        if (input[tempIndex][j] == 'O') {
                            input[i][j] = 'O';
                            input[tempIndex][j] = '.';
                            break;
                        }
                        tempIndex++;
                    }                    
                }
            }
        }
    }

    public static void tiltRight(char[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = input[i].length - 1; j >= 0; j--) {
                if (input[i][j] == '.') {
                    int tempIndex = j - 1;
                    while (tempIndex >= 0) {
                        if (input[i][tempIndex] == '#') {
                            break;
                        }
                        if (input[i][tempIndex] == 'O') {
                            input[i][j] = 'O';
                            input[i][tempIndex] = '.';
                            break;
                        }
                        tempIndex--;
                    }                    
                }
            }
        }
    }

    public static void tiltDown(char[][] input) {
        for (int i = input.length - 1; i >= 0; i--) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '.') {
                    int tempIndex = i - 1;
                    while (tempIndex >= 0) {
                        if (input[tempIndex][j] == '#') {
                            break;
                        }
                        if (input[tempIndex][j] == 'O') {
                            input[i][j] = 'O';
                            input[tempIndex][j] = '.';
                            break;
                        }
                        tempIndex--;
                    }                    
                }
            }
        }
    }

    public static void tiltLeft(char[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '.') {
                    int tempIndex = j + 1;
                    while (tempIndex < input[i].length) {
                        if (input[i][tempIndex] == '#') {
                            break;
                        }
                        if (input[i][tempIndex] == 'O') {
                            input[i][j] = 'O';
                            input[i][tempIndex] = '.';
                            break;
                        }
                        tempIndex++;
                    }                    
                }
            }
        }
    }
}
