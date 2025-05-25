import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga13 {
    public static List<String> getInput() {
        String path = "input/input13.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga13_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/13 */

        List<String> input = getInput();
        List<List<String>> listPatterns = new ArrayList<>();
        int sum = 0;
        int inputIndex = 0;

        // Fill the list of patterns from input with empty rows serving as separators between patterns
        while (inputIndex < input.size()) {
            List<String> pattern = new ArrayList<>();
            while (inputIndex < input.size() && !input.get(inputIndex).isEmpty()) {
                pattern.add(input.get(inputIndex));
                inputIndex++;
            }
            listPatterns.add(pattern);
            inputIndex++;
        }

        // Iterate through the list of patterns and try finding a vertical mirror. If this fails, then try finding a horizontal mirror
        for (List<String> pattern : listPatterns) {
            boolean mirrorFound = false;
            for (int i = 0; i < pattern.get(0).length() - 1; i++) {
                if (findVerticalMirror(pattern, i)) {
                    sum += (i + 1);
                    mirrorFound = true;
                    break;
                }
            }
            if (!mirrorFound) {
                for (int j = 0; j < pattern.size() - 1; j++) {
                    if (findHorizontalMirror(pattern, j)) {
                        sum += (j + 1) * 100;
                        break;
                    }
                }
            }
        }

        System.out.println("The sum of mirrors is " + sum);
    }

    public static void naloga13_2() {
        List<String> input = getInput();
        List<List<String>> listPatterns = new ArrayList<>();
        int sum = 0;
        int inputIndex = 0;
        while (inputIndex < input.size()) {
            List<String> pattern = new ArrayList<>();
            while (inputIndex < input.size() && !input.get(inputIndex).isEmpty()) {
                pattern.add(input.get(inputIndex));
                inputIndex++;
            }
            listPatterns.add(pattern);
            inputIndex++;
        }

        // The second part is very similar, but instead of mirror images, we have to find corrupt mirror images with exactly one difference
        for (List<String> pattern : listPatterns) {
            boolean mirrorFound = false;
            for (int i = 0; i < pattern.get(0).length() - 1; i++) {
                if (findVerticalSmudge(pattern, i)) {
                    sum += (i + 1);
                    mirrorFound = true;
                    break;
                }
            }
            if (!mirrorFound) {
                for (int j = 0; j < pattern.size() - 1; j++) {
                    if (findHorizontalSmudge(pattern, j)) {
                        sum += (j + 1) * 100;
                        break;
                    }
                }
            }
        }

        System.out.println("The sum of mirrors is " + sum);
    }

    // This function compares the column at the passed index with the column at the next index and returns whether a mirror has been found
    public static boolean findVerticalMirror(List<String> input, int index) {
        int leftIndex = index;
        int rightIndex = index + 1;
        int differences = 0;
        while (leftIndex >= 0 && rightIndex < input.get(0).length()) {
            differences += checkVertical(input, leftIndex, rightIndex);
            if (differences == 0) {
                leftIndex--;
                rightIndex++;
            } else {
                break;
            }
        }
        if (differences == 0) {
            return true;
        } else {
            return false;
        }
    }

    // This is a slight variation of the previous function which enables setting the maximum number of differences. If maxDiff is set at 0, this function works exactly the same as findVerticalMirror(), otherwise we can find any given number of smudges
    public static boolean findVerticalSmudge(List<String> input, int index) {
        int leftIndex = index;
        int rightIndex = index + 1;
        int differences = 0;
        int maxDiff = 1;
        while (leftIndex >= 0 && rightIndex < input.get(0).length()) {
            differences += checkVertical(input, leftIndex, rightIndex);
            if (differences <= maxDiff) {
                leftIndex--;
                rightIndex++;
            } else {
                break;
            }
        }
        if (differences == maxDiff) {
            return true;
        } else {
            return false;
        }
    }

    // This function returns the number of differences between the compared columns
    public static int checkVertical(List<String> input, int leftIndex, int rightIndex) {
        int differences = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).charAt(leftIndex) != input.get(i).charAt(rightIndex)) {
                differences++;
            }
        }
        return differences;
    }

    // This function compares the row at the passed index with the row at the next index and returns whether a mirror has been found
    public static boolean findHorizontalMirror(List<String> input, int index) {
        int upperIndex = index;
        int lowerIndex = index + 1;
        int differences = 0;
        while (upperIndex >= 0 && lowerIndex < input.size()) {
            differences += checkHorizontal(input, upperIndex, lowerIndex);
            if (differences == 0) {
                upperIndex--;
                lowerIndex++;
            } else {
                break;
            }
        }
        if (differences == 0) {
            return true;
        } else {
            return false;
        }
    }

    // This is a slight variation of the previous function which enables setting the maximum number of differences. If maxDiff is set at 0, this function works exactly the same as findHorizontalMirror(), otherwise we can find any given number of smudges
    public static boolean findHorizontalSmudge(List<String> input, int index) {
        int upperIndex = index;
        int lowerIndex = index + 1;
        int differences = 0;
        int maxDiff = 1;
        while (upperIndex >= 0 && lowerIndex < input.size()) {
            differences += checkHorizontal(input, upperIndex, lowerIndex);
            if (differences <= maxDiff) {
                upperIndex--;
                lowerIndex++;
            } else {
                break;
            }
        }
        if (differences == maxDiff) {
            return true;
        } else {
            return false;
        }
    }

    // This function returns the number of differences between the compared columns
    public static int checkHorizontal(List<String> input, int upperIndex, int lowerIndex) {
        int differences = 0;
        for (int i = 0; i < input.get(0).length(); i++) {
            if (input.get(upperIndex).charAt(i) != input.get(lowerIndex).charAt(i)) {
                differences++;
            }
        }
        return differences;
    }
}
