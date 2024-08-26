package Naloga10;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga10 {
    public static List<String> getInput() {
        String path = "input/input10.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga10_1() {
        /* Instructions available here: https://adventofcode.com/2023/day/10 */

        List<String> input = getInput();
        int startingRow = 0;
        int startingCol = 0;
        boolean startFound = false;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'S') {
                    startingRow = i;
                    startingCol = j;
                    startFound = true;
                    break;
                } 
            }
            if (startFound) {
                break;
            }
        }
        
        // Initialize a Tracker and find a field it can move to from starting position
        Tracker tracker = new Tracker(startingRow, startingCol);
        if (startingRow > 0 && "|7F".indexOf(input.get(startingRow - 1).charAt(startingCol)) >= 0) {
            tracker.moveNorth();
        } else if (startingCol + 1 < input.get(startingRow).length() && "-J7".indexOf(input.get(startingRow).charAt(startingCol + 1)) >= 0) {
            tracker.moveEast();
        } else if (startingRow + 1 < input.size() && "|LJ".indexOf(input.get(startingRow + 1).charAt(startingCol)) >= 0) {
            tracker.moveSouth();
        } else {
            System.out.println("Starting field cannot be connected to less than two pipes.");
        }

        // Keep moving the Tracker until it arrives back at the starting point. The farthest distance is half the length of the main loop.
        char currentField = input.get(tracker.getCurrentCoord()[0]).charAt(tracker.getCurrentCoord()[1]);
        while(currentField != 'S') {
            tracker.moveTracker(currentField);
            currentField = input.get(tracker.getCurrentCoord()[0]).charAt(tracker.getCurrentCoord()[1]);
        }
        int farthestDistance = tracker.getSteps() / 2;
        System.out.println("It takes " + farthestDistance + " steps.");
    }

    public static void naloga10_2() {
       
        List<String> input = getInput();
        int startingRow = 0;
        int startingCol = 0;

        // We create a matrix the size of the input that first only shows the starting point and everything else as '.' characters, but later we fill the matrix with the pipes forming the main loop. 
        char[][] matrix = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'S') {
                    startingRow = i;
                    startingCol = j;
                    matrix[i][j] = 'S';
                } else {
                    matrix[i][j] = '.';
                }
            }
        }
        Tracker tracker = new Tracker(startingRow, startingCol);

        // Find the two pipes that are connected to the starting field. By using multiples of 2, startDirection is a unique number for every pair of pipes connected to the starting field.
        int startDirection = 0;
        char replacedStart = ' ';

        if (startingRow > 0 && "|7F".indexOf(input.get(startingRow - 1).charAt(startingCol)) >= 0) {
            startDirection += 1;
        }
        if (startingCol + 1 < input.get(startingRow).length() && "-J7".indexOf(input.get(startingRow).charAt(startingCol + 1)) >= 0) {
            startDirection += 2;
        }
        if (startingRow + 1 < input.size() && "|LJ".indexOf(input.get(startingRow + 1).charAt(startingCol)) >= 0) {
            startDirection += 4;
        }
        if (startingCol > 0 && "-LF".indexOf(input.get(startingRow).charAt(startingCol - 1)) >= 0) {
            startDirection += 8;
        }
        switch (startDirection) {
            case 3:
                replacedStart = 'L';
                tracker.moveNorth();
                break;
            case 5:
                replacedStart = '|';
                tracker.moveNorth();
                break;
            case 9:
                replacedStart = 'J';
                tracker.moveNorth();
                break;
            case 6:
                replacedStart = 'F';
                tracker.moveEast();
                break;
            case 10:
                replacedStart = '-';
                tracker.moveEast();
                break;
            case 12:
                replacedStart = '7';
                tracker.moveSouth();
                break;
            default:
                System.out.println("Starting field cannot be connected to less than two pipes.");
                break;
        }

        // In order to count pipes traversed, 'S' has to be changed to the pipe it represents in the matrix. Then we mark all fields that form the main loop in the matrix.
        matrix[startingRow][startingCol] = replacedStart;
        char currentField = input.get(tracker.getCurrentCoord()[0]).charAt(tracker.getCurrentCoord()[1]);
        while(currentField != 'S') {
            matrix[tracker.getCurrentCoord()[0]][tracker.getCurrentCoord()[1]] = input.get(tracker.getCurrentCoord()[0]).charAt(tracker.getCurrentCoord()[1]);
            tracker.moveTracker(currentField);
            currentField = input.get(tracker.getCurrentCoord()[0]).charAt(tracker.getCurrentCoord()[1]);
        }

        // Since all fields that don't form the main loop are marked as '.' in the matrix, we can go from every edge field inwards and mark fields that don't traverse the main loop in every one of the cardinal directions as 'O', greatly reducing the amount of fields to be checked.
        for (int i = 0; i < matrix.length; i++) {
            int j = 0;
            while (j < matrix[i].length && matrix[i][j] == '.') {
                matrix[i][j] = 'O';
                j++;
            }
            int k = matrix[i].length - 1;
            while (k >= 0 && matrix[i][k] == '.') {
                matrix[i][k] = 'O';
                k--;
            }
        }  
        for (int i = 0; i < matrix[0].length; i++) {
            int j = 0;
            while (j < matrix.length && (matrix[j][i] == '.' || matrix[j][i] == 'O')) {
                matrix[j][i] = 'O';
                j++;
            }
            int k = matrix.length - 1;
            while (k >= 0 && (matrix[k][i] == '.' || matrix[k][i] == 'O')) {
                matrix[k][i] = 'O';
                k--;
            }
        }
             
        int fieldsInsideLoop = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '.') {
                    if (checkLoopNorth(i, j, matrix)) {
                        fieldsInsideLoop++;
                    }
                }
            }
            
        }
        System.out.println("The number of tiles enclosed by the loop is " + fieldsInsideLoop);
    }

    /* As per instructions, traversing both 'F' and 'L' and both '7' and 'J' to the north/south essentially means going past the pipe instead of traversing it, so we're only interested in the difference between paired pipes – this is why we're subtracting it.
    Furthermore, the difference between 'F' and 'L' pipes traversed and '7' and 'J' pipes traversed must always be the same, since a combination of 'F'/'L' and either '7' or 'J' results in 1 pipe traversed. Thus, we can ignore '7' and 'J' pipes completely. */
    public static boolean checkLoopNorth(int checkedRow, int checkedCol, char[][] matrix) {
        boolean insideLoop = false;
        if (checkedRow == 0) {
            return insideLoop;
        }
        int pipesTraversed = 0;
        int totalFL = 0;
        for (int i = checkedRow - 1; i >= 0; i--) {
            char ch = matrix[i][checkedCol];
            if (ch == 'O') {
                break;
            }
            switch (ch) {
                case '-':
                    pipesTraversed++;
                    break;
                case 'F':
                    totalFL++;
                    break;
                case 'L':
                    totalFL--;
                    break;
            }    
        }
        pipesTraversed += Math.abs(totalFL);
        if (pipesTraversed %2 != 0) {
            insideLoop = true;
        }
        return insideLoop;
    }

    // Alternative check to verify that it shows the same result as checkLoopNorth()
    public static boolean checkLoopSouth(int checkedRow, int checkedCol, char[][] matrix) {
        boolean insideLoop = false;
        if (checkedRow == matrix.length - 1) {
            return insideLoop;
        }
        int pipesTraversed = 0;
        int total7J = 0;
        for (int i = checkedRow + 1; i < matrix.length; i++) {
            char ch = matrix[i][checkedCol];
            if (ch == 'O') {
                break;
            }
            switch (ch) {
                case '-':
                    pipesTraversed++;
                    break;
                case '7':
                    total7J++;
                    break;
                case 'J':
                    total7J--;
                    break;
            }    
        }
        pipesTraversed += Math.abs(total7J);
        if (pipesTraversed %2 != 0) {
            insideLoop = true;
        }
        return insideLoop;
    }
}
