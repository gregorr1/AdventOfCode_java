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
            return;
        }
        int sum = 0;

        for(String line : input) {
            int coordinate = 0;
            for(int i = 0; i < line.length(); i++) {
                if(Character.isDigit(line.charAt(i))) {
                    int firstCoordinate = Character.getNumericValue(line.charAt(i));
                    coordinate += firstCoordinate * 10;
                    break;
                }
            }
            for(int i = line.length() - 1; i >= 0; i--) {
                if(Character.isDigit(line.charAt(i))) {
                    int secondCoordinate = Character.getNumericValue(line.charAt(i));
                    coordinate += secondCoordinate;
                    break;
                }
            }
            sum += coordinate;
        }
        System.out.println("The sum of all of the calibration values is " + sum);
    }

    public static void naloga1_2() {
        /* Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
        Equipped with this new information, you now need to find the real first and last digit on each line.
        What is the sum of all of the calibration values? */

        Map<String, Integer> mapNums = new HashMap<>();
        mapNums.put("one", 1);
        mapNums.put("two", 2);
        mapNums.put("three", 3);
        mapNums.put("four", 4);
        mapNums.put("five", 5);
        mapNums.put("six", 6);
        mapNums.put("seven", 7);
        mapNums.put("eight", 8);
        mapNums.put("nine", 9);

        String path = "input/input1.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        List<String> textNums = new ArrayList<>(mapNums.keySet());
        int sum = 0;
        
        for(String line : input) {
            int coordinate = 0;

            // Finding min index of a dictionary key
            int minIndex = -1;
            String minKey = "";
            for(String num : textNums) {
                int index = line.indexOf(num);
                if(index > -1) {
                    if(minIndex == -1 || minIndex > index) {
                        minIndex = index;
                        minKey = num;
                    }
                }
            }
            String minIndexReplaced = line;
            if(mapNums.containsKey(minKey)) {
                minIndexReplaced = line.replace(minKey, mapNums.get(minKey).toString());
                System.out.println(minIndexReplaced);
                minIndexReplaced = line.substring(0, minIndex) + mapNums.get(minKey) + line.substring(minIndex + minKey.length());
                System.out.println(minIndexReplaced);
            }

            // Finding max index of a dictionary key
            int maxIndex = -1;
            String maxKey = "";
            for(String num : textNums) {
                int index = line.lastIndexOf(num);
                if(index > -1) {
                    if(maxIndex < index) {
                        maxIndex = index;
                        maxKey = num;
                    }
                }
            }
            String maxIndexReplaced = line;
            if(mapNums.containsKey(maxKey)) {
                maxIndexReplaced = line.replace(maxKey, mapNums.get(maxKey).toString());
                System.out.println(minIndexReplaced);
                maxIndexReplaced = line.substring(0, maxIndex) + mapNums.get(maxKey) + line.substring(maxIndex + maxKey.length());
            }

            // Calculating the first coordinate from the line with the first instance of a dictionary key replaced by its value
            for(int i = 0; i < minIndexReplaced.length(); i++) {
                if(Character.isDigit(minIndexReplaced.charAt(i))) {
                    int firstCoordinate = Character.getNumericValue(minIndexReplaced.charAt(i));
                    coordinate += firstCoordinate * 10;
                    break;
                }
            }

            // Calculating the second coordinate from the line with the last instance of a dictionary key replaced by its value
            for(int i = maxIndexReplaced.length() - 1; i >= 0; i--) {
                if(Character.isDigit(maxIndexReplaced.charAt(i))) {
                    int secondCoordinate = Character.getNumericValue(maxIndexReplaced.charAt(i));
                    coordinate += secondCoordinate;
                    break;
                }
            }
            sum += coordinate;
        }
        System.out.println("The sum of all of the calibration values is " + sum);
    }
}
