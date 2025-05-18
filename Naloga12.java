import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Naloga12 {
    // Due to a very large number of possible combinations, already calculated strings should be cached
    public static Map<String, Long> cache = new HashMap<>();

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
        /* Instructions available here: https://adventofcode.com/2023/day/12 */

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
                }
            }

            int questionMarks = 0;
            int existingHashes = 0;
            for (int i = 0; i < substrings[0].length(); i++) {
                if (substrings[0].charAt(i) == '?') {
                    questionMarks++;
                } else if (substrings[0].charAt(i) == '#') {
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

    public static void naloga12_2() {
        List<String> input = getInput();
        long allMatches = 0;
        for (String line : input) {
            String[] substrings = line.split(" ");
            // More than one consecutive dot has no effect, so they should be replaced with a single dot for faster processing
            substrings[0] = substrings[0].replaceAll("\\.+", ".");
            List<Integer> arrangement = new ArrayList<>();
            String[] strNumbers = substrings[1].split(",");
            for (String strNum : strNumbers) {
                try {
                    int number = Integer.parseInt(strNum);
                    arrangement.add(number);
                } catch (Exception e) {
                    System.out.println("Error, cannot parse " + strNum);
                }
            }

            // Strings and arrangements should be 'unfolded' 5 times
            String unfoldedRow = "";
            List<Integer> unfoldedArrangement = new ArrayList<>();
            int multiplier = 5;
            for (int i = 0; i < multiplier; i++) {
                unfoldedArrangement.addAll(arrangement);
                if (i > 0) {
                    unfoldedRow += "?";
                }
                unfoldedRow += substrings[0];
            }
            allMatches += countCombinations(unfoldedRow, unfoldedArrangement);
        }

        System.out.println("The sum of all combinations is: " + allMatches);
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

    // Complete the permutation if both '#' and '.' counts are zero or add a hash/dot and recurse 
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

    // Recursive function that returns the total number of possible combinations for the given input and rules
    public static long countCombinations(String input, List<Integer> rules) {
        long result = 0;
        String key = input + rules;

        // Check whether the result is already in the cache
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        // The method should not run if the input is too short to be even theoretically able to contain the arrangement
        if (input.length() >= calculateMinLength(rules)) {
            if (!input.isEmpty() && !rules.isEmpty()) {
                String nextInput;

                // Check for the dot character or '?', where '?' is assumed to be a dot.
                // In this case, the current character should be skipped over to the next one.
                if (input.charAt(0) == '.' || input.charAt(0) == '?') {
                    nextInput = input.substring(1);
                    result += countCombinations(nextInput, rules);
                }

                // Check for the hash character or '?', where '?' is assumed to be a hash
                if (input.charAt(0) == '#' || input.charAt(0) == '?') {
                    // Check if the remaining input fits exactly with the final remaining rule. In this case, only one combination is possible and it should stop processing.
                    if (rules.size() == 1 && rules.get(0) == input.length() && !input.contains(".")) {
                        return 1;
                    } else if (rules.get(0) < input.length()) {
                        // If input is longer than the first rule, check to find a string of '#' and/or '?' symbols of the same length as the first rule, followed by a '.' or '?' symbol and recurse with the new string and the first rule removed
                        String checkedString = input.substring(0, rules.get(0));
                        if (!checkedString.contains(".") && input.charAt(rules.get(0)) != '#') {
                            nextInput = input.substring(rules.get(0) + 1);
                            List<Integer> newRules = new ArrayList<>(rules);
                            newRules.remove(0);
                            result += countCombinations(nextInput, newRules);
                        }
                    } else {
                        // If input is shorter than the first rule or same length as the first rule followed by more rules, any further combinations are invalid and should not be processed further
                        return 0;
                    }
                    
                }
            // Check whether there are '#' symbols in case the rules are empty
            } else if (rules.isEmpty()) {
                if (input.contains("#") ) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0; // Input is empty while there are still rules - such combination is invalid
            }
        } else {
            return 0; // Input is too short for the given rules
        }
        cache.put(key, result);
        return result;
    }

    public static int calculateMinLength(List<Integer> rules) {
        int minLength = -1;
        for (Integer num : rules) {
            minLength++;
            minLength += num;
        }
        return minLength;
    }
}
