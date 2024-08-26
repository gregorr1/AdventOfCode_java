package Naloga3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga3 {
    public static void naloga3_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/3 */

        String path = "input/input3.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        int sum = 0;

        // Go through all numbers and determine whether there are adjacent symbols
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (Character.isDigit(line.charAt(j))) {
                    boolean isValid = false;
                    String number = "";
                    if (j > 0 && line.charAt(j - 1) != '.') {
                        // There's a symbol to the left.
                        isValid = true;
                    }
                    while (j < line.length() && Character.isDigit(line.charAt(j))) {
                        if (i > 0 && j > 0 && input.get(i - 1).charAt(j - 1) != '.') {
                            // There's a symbol diagonally above to the left.
                            isValid = true;
                        }
                        if (i > 0 && j > 0 && input.get(i - 1).charAt(j) != '.') {
                            // There's a symbol above.
                            isValid = true;
                        }
                        if (i > 0 && j < line.length() - 1 && input.get(i - 1).charAt(j + 1) != '.') {
                            // There's a symbol diagonally above to the right.
                            isValid = true;
                        }
                        if (i < input.size() - 1 && j > 0 && input.get(i + 1).charAt(j - 1) != '.') {
                            // There's a symbol diagonally below to the left.
                            isValid = true;
                        }
                        if (i < input.size() - 1 && input.get(i + 1).charAt(j) != '.') {
                            // There's a symbol below.
                            isValid = true;
                        }
                        if (i < input.size() - 1 && j < line.length() - 1 && input.get(i + 1).charAt(j + 1) != '.') {
                            // There's a symbol diagonally below to the right.
                            isValid = true;
                        }
                        // System.out.println("Number is " + number);
                        number = number + line.charAt(j);
                        // System.out.println("New number is " + number);
                        j++;
                    }
                    if (j < line.length() && line.charAt(j) != '.') {
                        // There's a symbol to the right.
                        isValid = true;
                    }
                    int partNumber = Integer.parseInt(number);
                    if (isValid) {
                        sum += partNumber;
                    }
                }
            }
        }
        System.out.println("The sum of all of the part numbers is " + sum);
    }

    public static void naloga3_2() {

        String path = "input/input3.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        int sum = 0;
        
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '*') {
                    Gear newGear = new Gear();
                    newGear.checkSurrounding(i, j, input);
                    sum += newGear.getValue();
                }
            }
        }
        System.out.println("The sum of all gear values is " + sum);
    }
}
