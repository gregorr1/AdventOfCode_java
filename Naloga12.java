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
            // regex samo za pike in lojtre: compile("\\.+\\#+\\.+");
            
            
            

            
            /*
            if (numQuest > maxQuests) {
                maxQuests = numQuest;
                System.out.println("Line is " + substrings[0]);
                System.out.println("Question marks is " + maxQuests);
                System.out.println("Lngth is " + substrings[0].length());
            }
            */

            // Prepare input - substrings[0] is a String we work on, substrings[1] creates a list of ints.
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

            int permutationHashes = damagedSprings - existingHashes;
            int permutationDots = questionMarks - permutationHashes;
            List<String> list = new ArrayList<>();
            Pattern pattern = Pattern.compile(buildRegex(arrangement));
            int matches = 0;
            generatePermutations(permutationHashes, permutationDots, list, "");
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
                    // System.out.println("Found match: " + replacedString);
                }
                // System.out.println(replacedString);
            }
            allMatches += matches;
            // regex za znake vmes:
            // Pattern.compile("\\.+.*\\#+.*\\.+");
            // 
            // 
            /*
            
            */





            
            
        }

        /*
        int numberOfQuestionmarks = maxQuests;
        for (int i = 0; i < Math.pow(2, numberOfQuestionmarks); i++) {
            String bin = String.format("%" + numberOfQuestionmarks + "s", Integer.toBinaryString(i)).replace(" ", "0");

            String newString = "";
            for (int j = 0; j < bin.length(); j++) {
                if (bin.charAt(j) == '1') {
                    newString += '#';
                } else {
                    newString += '.';
                }
            }
            // System.out.println(newString);
        }
        */
        System.out.println("All matches is " + allMatches);

        

        

        

    }

    public static String buildRegex(List<Integer> arrangement) {
        String regex = "\\.*";
        for (int i = 0; i < arrangement.size(); i++) {
            regex += "\\#{" + arrangement.get(i) + "}";
            if (i + 1 < arrangement.size()) {
                regex += "\\.+";
            }
        }
        regex += "\\.*";
        // System.out.println(regex);
        return regex;
    }

    public static void generatePermutations(int numHash, int numDot, List<String> permutations, String current) {
        // Base case: if both `#` and `.` counts are zero, print the result
        if (numHash == 0 && numDot == 0) {
            permutations.add(current);
            return;
        }

        // If there are hashes left to place, add a hash and recurse
        if (numHash > 0) {
            generatePermutations(numHash - 1, numDot, permutations, current + "#");
        }

        // If there are dots left to place, add a dot and recurse
        if (numDot > 0) {
            generatePermutations(numHash, numDot - 1, permutations, current + ".");
        }
    }

    public static boolean checkMatch(String permutation, String regex) {
        return true;
    }

}
