package Naloga16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Naloga16 {
    public static List<String> getInput() {
        String path = "input/input16.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga16_1() {
        List<String> input = getInput();
        boolean[][] energized = new boolean[input.size()][input.get(0).length()];
        Map<String, boolean[]> mirrors = new HashMap<>();
        Beam beam = new Beam(0, 0, 1);
        int sum = 0;

        moveBeam(beam, input, mirrors, energized);

        for (boolean[] bs : energized) {
            for (boolean bs2 : bs) {
                if (bs2) {
                    sum++;
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga16_2() {
        List<String> input = getInput();
        int maxSum = 0;

        // Loop through the input and run the function from the previous exercise from every edge field
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (i != 0 && i != input.size() - 1 && j != 0 && j != input.get(i).length()) {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
                    boolean[][] energized = new boolean[input.size()][input.get(0).length()];
                    Map<String, boolean[]> mirrors = new HashMap<>();
                    Beam beam = new Beam(i, j, k);
                    int sum = 0;

                    moveBeam(beam, input, mirrors, energized);

                    for (boolean[] bs : energized) {
                        for (boolean bs2 : bs) {
                            if (bs2) {
                                sum++;
                            }
                        }
                    }

                    if (sum > maxSum) {
                        maxSum = sum;
                    }
                }
            }
        }

        System.out.println("The result is " + maxSum);  
    }

    // While beam is within bounds, its current coordinates become energized. All mirrors are represented by a String key (its coordinates) and a boolean array that consists of 4 booleans - one for each direction of the beam. Once the beam reaches a mirror from an already reached direction, the method recognizes this as a loop and stops.
    public static void moveBeam(Beam beam, List<String> input, Map<String, boolean[]> mirrors, boolean[][] energized) {
        while (beam.checkGrid(input)) {
            energized[beam.getRow()][beam.getColumn()] = true;
            if (beam.checkMirror(input)) {
                String key = "" + beam.getRow() + "X" + beam.getColumn();
                if (mirrors.containsKey(key)) {
                    boolean[] values = mirrors.get(key);
                    if (!values[beam.getDirection() % 4]) {
                        values[beam.getDirection() % 4] = true;
                        mirrors.put(key, values);
                    } else {
                        return;
                    }
                } else {
                    boolean[] values = new boolean[4];
                    values[beam.getDirection() % 4] = true;
                    mirrors.put(key, values);
                }
                int change = beam.changeDirection(input);
                beam.setDirection(beam.getDirection() + change);
                
                if (change == 5) { // The beam should split
                    Beam splitBeam = new Beam(beam);
                    splitBeam.move(input);
                    moveBeam(splitBeam, input, mirrors, energized);
                }
            }
            beam.move(input);
        }
    }
}