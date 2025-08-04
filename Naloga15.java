import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga15 {
    public static List<String> getInput() {
        String path = "input/input15.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga15_1() {
        String input = getInput().get(0);
        String[] substrings = input.split(",");
        int sum = 0;

        for (String string : substrings) {
            sum += hashAlgorithm(string);
        }

        System.out.println("The result is " + sum);

    }

    public static void naloga15_2() {
        String input = getInput().get(0);
        String[] lenses = input.split(",");

        int boxesSize = 256;
        List<List<String>> boxes = new ArrayList<>();
        for (int i = 0; i < boxesSize; i++) {
            boxes.add(new ArrayList<String>()); // Manually set the size of a List<List<String>>
        }
        int sum = 0;

        // Get the label from the lens and check if it's already in the corresponding box
        for (String lens : lenses) {
            String[] labels = lens.split("[=-]");
            int box = hashAlgorithm(labels[0]);
            boolean contains = false;
            for (int i = 0; i < boxes.get(box).size(); i++) {
                if (boxes.get(box).get(i).contains(labels[0])) {
                    contains = true;
                    if (lens.contains("=")) {
                        boxes.get(box).set(i, labels[0] + " " + labels[1]); // Replace with the new lens
                    } else if (lens.contains("-")) {
                        boxes.get(box).remove(i); // Remove the lens
                    }
                }
            }
            if (!contains && lens.contains("=")) {
                boxes.get(box).add(labels[0] + " " + labels[1]); // Add the lens to the first available position
            }
        }

        // Parse the lens number and calculate focusing power
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                try {
                    int focal = Integer.parseInt(boxes.get(i).get(j).replaceAll("[^0-9]", ""));
                    sum += ((i + 1) * (j + 1) * focal);
                } catch (Exception e) {
                    System.out.println("Error, cannot parse lens " + boxes.get(i).get(j));
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    // Return a number from a string by initializing a variable and setting it to zero. For each character, add its ASCII value to this total value, then multiply it by 17 and set it to the remainder of dividing itself by 256.
    public static int hashAlgorithm(String input) {
        int value = 0;
        for (int i = 0; i < input.length(); i++) {
            value += (int)input.charAt(i);
            value *= 17;
            value %= 256;
        }
        return value;
    }
}
