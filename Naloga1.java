import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Naloga1 {
    public static void naloga1_1() {
        /* The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
        Consider your entire calibration document. What is the sum of all of the calibration values? */

        String path = "input/input1.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        int sum = 0;

        for(String line : input) {
            int coordinate = 0;
            for(int i = 0; i < line.length(); i++) {
                if(Character.isDigit(line.charAt(i)))  {
                    int firstCoordinate = Character.getNumericValue(line.charAt(i));
                    coordinate += firstCoordinate * 10;
                    break;
                }
            }
            for(int i = line.length() - 1; i >= 0; i--) {
                if(Character.isDigit(line.charAt(i)))  {
                    int secondCoordinate = Character.getNumericValue(line.charAt(i));
                    coordinate += secondCoordinate;
                    break;
                }
            }
            sum += coordinate;
        }
        System.out.println("The answer is " + sum);
    }

    public static void naloga1_2() {
        /* Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
        Equipped with this new information, you now need to find the real first and last digit on each line.
        What is the sum of all of the calibration values? */

        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);

        String path = "input/input1.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        int sum = 0;

        List<String> textNumbers = new ArrayList<>(map.keySet());

        for(String line : input) {
            int coordinate = 0;

            int minIndex = -1;
            String minKey = "";

            for(String num : textNumbers) {

            }
        }

    }
}
