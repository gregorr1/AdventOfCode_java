import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga6 {
    public static void naloga6_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/6 */

        String path = "input/input6.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }

        // Parse numbers needed for calculation. This is not needed in this case where there are only 4 races, but is included to ensure scalability.
        String[] arrTimes = input.get(0).split("\\s+");
        List<Integer> listTimes = new ArrayList<>();
        for (String substring : arrTimes) {
            try {
                int number = Integer.parseInt(substring);
                listTimes.add(number);
            } catch (NumberFormatException e) { }
        }
        String[] arrDistances = input.get(1).split("\\s+");
        List<Integer> listDistances = new ArrayList<>();
        for (String substring : arrDistances) {
            try {
                int number = Integer.parseInt(substring);
                listDistances.add(number);
            } catch (NumberFormatException e) { }
        }

        // Compare two elements of both lists at the same index. Once we reach the end of either list, processing stops.
        int totalWins = 1;
        int i = 0;
        while (i < listTimes.size() && i < listDistances.size()) {
            int wins = 0;
            int[] race = { listTimes.get(i), listDistances.get(i) };
            for (int j = 0; j < race[0]; j++) {
                int speed = j;
                int distance = speed * (race[0] - j);
                if (distance > race[1]) {
                    wins++;
                }
            }
            totalWins *= wins;
            i++;
        }
        System.out.println("The results is " + totalWins);
    }

    public static void naloga6_2() {
        
        String path = "input/input6.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }

        // Parse numbers needed for calculation
        String stringTime = "";
        String[] arrTimes = input.get(0).split("\\s+");
        for (String substring : arrTimes) {
            try {
                int number = Integer.parseInt(substring);
                stringTime += number;
            } catch (NumberFormatException e) { }
        }
        long time = 0;
        try {
            time = Long.parseLong(stringTime);
        } catch (NumberFormatException e) { }
        String stringRecord = "";
        String[] arrRecords = input.get(1).split("\\s+");
        for (String substring : arrRecords) {
            try {
                int number = Integer.parseInt(substring);
                stringRecord += number;
            } catch (NumberFormatException e) { }
        }
        long record = 0;
        try {
            record = Long.parseLong(stringRecord);
        } catch (NumberFormatException e) { }

        int wins = 0;
        for (int i = 0; i < time; i++) {
            int speed = i;
            long distance = speed * (time - i);
            if (distance > record) {
                wins++;
            }
        }
        System.out.println("There are " + wins + " ways you can beat the record.");
    }
}
