import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga11 {
    public static List<String> getInput() {
        String path = "input/input11.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }
  
    public static void naloga11_1() {
        /* The researcher has collected a bunch of data and compiled the data into a single giant image (your puzzle input). The image includes empty space (.) and galaxies (#).
        The researcher is trying to figure out the sum of the lengths of the shortest path between every pair of galaxies. However, there's a catch: the universe expanded in the time it took the light from those galaxies to reach the observatory.
        Due to something involving gravitational effects, only some space expands. In fact, the result is that any rows or columns that contain no galaxies should all actually be twice as big.
        Only count each pair once; order within the pair doesn't matter. For each pair, find any shortest path between the two galaxies using only steps that move up, down, left, or right exactly one . or # at a time. (The shortest path between two galaxies is allowed to pass through another galaxy.)
        Expand the universe, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths? */

        List<String> input = getInput();

        // Double empty rows 
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).indexOf('#') < 0) {
                String emptyString = "";
                for (int j = 0; j < input.get(i).length(); j++) {
                    emptyString += '.';
                }
                input.add(i, emptyString);
                i++;
            }
        }

        // Double empty columns
        for (int i = 0; i < input.get(0).length(); i++) {
            boolean emptyColumn = true;
            for (int j = 0; j < input.size(); j++) {
                if (input.get(j).charAt(i) == '#') {
                    emptyColumn = false;
                    break;
                }
            }
            if (emptyColumn) {
                for (int j = 0; j < input.size(); j++) {
                    String newString = input.get(j);
                    newString = newString.substring(0, i) + '.' + newString.substring(i);
                    input.set(j, newString);
                }
                i++;
            } 
        }

        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    sum += checkGalaxy(j, i, input);
                }
            }
        }
        sum /= 2;

        System.out.println("The sum of these lengths is " + sum);
    }

    public static void naloga11_2() {
        /* The galaxies are much older (and thus much farther apart) than the researcher initially estimated.
        Now, instead of the expansion you did before, make each empty row or column one million times larger. That is, each empty row should be replaced with 1000000 empty rows, and each empty column should be replaced with 1000000 empty columns.
        Starting with the same initial image, expand the universe according to these new rules, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths? */

        List<String> input = getInput();

        // Find empty rows
        List<Integer> emptyRowIndices = new ArrayList<>(); 
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).indexOf('#') < 0) {
                emptyRowIndices.add(i);
            }
        }

        // Find empty columns
        List<Integer> emptyColumnIndices = new ArrayList<>();
        for (int i = 0; i < input.get(0).length(); i++) {
            boolean emptyColumn = true;
            for (int j = 0; j < input.size(); j++) {
                if (input.get(j).charAt(i) == '#') {
                    emptyColumn = false;
                    break;
                }
            }
            if (emptyColumn) {
                emptyColumnIndices.add(i);
            } 
        }

        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    sum += calculateGalaxyDistances(j, i, input, emptyRowIndices, emptyColumnIndices);
                }
            }
        }

        sum /= 2;

        System.out.println("The sum of these lengths is " + sum);
    }

    public static long checkGalaxy(int coordinateX, int coordinateY, List<String> input) {
        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    sum += Math.abs(coordinateX - j);
                    sum += Math.abs(coordinateY - i);
                }
            }
        }
        return sum;
    }

    // Calculate empty fields instead of adding them
    public static long calculateGalaxyDistances(int coordinateX, int coordinateY, List<String> input, List<Integer> emptyRowIndices, List<Integer> emptyColumnIndices) { 
        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    int emptyColumnsPassed = 0;
                    int emptyRowsPassed = 0;
                    for (Integer columnIndex : emptyColumnIndices) {
                        if (columnIndex > Math.min(coordinateX, j) && columnIndex < Math.max(coordinateX, j)) {
                            emptyColumnsPassed++;
                        }
                    }
                    for (Integer rowIndex : emptyRowIndices) {
                        if (rowIndex > Math.min(coordinateY, i) && rowIndex < Math.max(coordinateY, i)) {
                            emptyRowsPassed++;
                        }
                    }
                    sum = sum + Math.abs(coordinateX - j) + Math.abs(coordinateY - i) + (999_999 * emptyColumnsPassed) + (999_999 * emptyRowsPassed);
                }
            }
        }
        return sum;
    }
}
