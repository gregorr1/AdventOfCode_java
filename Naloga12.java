import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Naloga12 {
    public static List<String> getInput() {
        String path = "input/input12.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga12_1() {
        List<String> input = getInput();
        int allMatches = 0;
        for (String line : input) {
            String[] substrings = line.split(" ");
            List<Integer> arrangement = new ArrayList<>();
            int damagedSprings = 0;
            String[] strNumbers = substrings[1].split(",");
            for (String strNum : strNumbers) {
                try {
                    int number = Integer.parseInt(strNum);
                    arrangement.add(number);
                    damagedSprings += number;
                } catch (Exception e) {
                    System.out.println("Error, cannot parse " + strNum);
                    return;
                }
            }

            int questionMarks = 0;
            int existingHashes = 0;
            for (int i = 0; i < substrings[0].length(); i++) {
                if (substrings[0].charAt(i) == '?') {
                    questionMarks++;
                }
                if (substrings[0].charAt(i) == '#') {
                    existingHashes++;
                }
            }

            // Find how many hashes and dots should replace the question marks
            int permutationHashes = damagedSprings - existingHashes;
            int permutationDots = questionMarks - permutationHashes;
            List<String> list = new ArrayList<>();
            Pattern pattern = Pattern.compile(buildRegex(arrangement));
            int matches = 0;
            generatePermutations(permutationHashes, permutationDots, list, ""); // Used to fill the list
            
            // Replace question marks with hashes or dots, depending on their order in the current list element
            for (String string : list) {
                int stringIndex = 0;
                String replacedString = "";
                for (int i = 0; i < substrings[0].length(); i++) {
                    if (substrings[0].charAt(i) == '?') {
                        replacedString += string.charAt(stringIndex);
                        stringIndex++;
                    } else {
                        replacedString += substrings[0].charAt(i);
                    }
                }
                Matcher matcher = pattern.matcher(replacedString);
                boolean matchFound = matcher.find();
                if (matchFound) {
                    matches++;
                }
            }
            allMatches += matches;
        }
        System.out.println("The sum of those counts is " + allMatches);
    }

    // Build a regex for a string that starts and ends with any character and has at least one dot after every hash or set of hashes
    public static String buildRegex(List<Integer> arrangement) {
        String regex = "\\.*";
        for (int i = 0; i < arrangement.size(); i++) {
            regex += "\\#{" + arrangement.get(i) + "}";
            if (i + 1 < arrangement.size()) {
                regex += "\\.+";
            }
        }
        regex += "\\.*";
        return regex;
    }

    // Print the permutation if both '#' and '.' counts are zero or add a hash/dot and recurse 
    public static void generatePermutations(int numHash, int numDot, List<String> permutations, String current) {
        if (numHash == 0 && numDot == 0) {
            permutations.add(current);
            return;
        }
        if (numHash > 0) {
            generatePermutations(numHash - 1, numDot, permutations, current + "#");
        }
        if (numDot > 0) {
            generatePermutations(numHash, numDot - 1, permutations, current + ".");
        }
    }
}
