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
        /* Instructions available here: https://adventofcode.com/2023/day/11 */

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
