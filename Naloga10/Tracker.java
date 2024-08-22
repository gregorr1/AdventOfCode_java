package Naloga10;

public class Tracker {
    private int[] currentCoord;
    private int[] previousCoord;
    private int steps;
    
    public Tracker(int startingRow, int startingCol) {
        this.previousCoord = new int[] {startingRow, startingCol};
        this.currentCoord = new int[] {startingRow, startingCol};
        this.steps = 1;
    }

    public int[] getCurrentCoord() {
        return currentCoord;
    }
    public void setCurrentCoord(int[] currentCoord) {
        this.currentCoord = currentCoord;
    }
    public int[] getPreviousCoord() {
        return previousCoord;
    }
    public void setPreviousCoord(int[] previousCoord) {
        this.previousCoord = previousCoord;
    }
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void moveNorth() {
        try {
            currentCoord[0]--;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");
        }
    }

    public void moveSouth() {
        try {
            currentCoord[0]++;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");
        }
    }
    
    public void moveWest() {
        try {
            currentCoord[1]--;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");
        }
    }

    public void moveEast() {
        try {
            currentCoord[1]++;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");
        }
    }

    public void moveTracker(char ch) {
        int tempRow = currentCoord[0];
        int tempCol = currentCoord[1];
        if (currentCoord[0] < previousCoord[0]) {
            switch (ch) {
                case '7':
                    moveWest();
                    break;
                case 'F':
                    moveEast();
                    break;
                case '|':
                    moveNorth();
                    break;
                default:
                    System.out.println("Dead-end reached.");
                    break;
            }
        } else if (currentCoord[0] > previousCoord[0]) {
            switch (ch) {
                case 'J':
                    moveWest();
                    break;
                case 'L':
                    moveEast();
                    break;
                case '|':
                    moveSouth();
                    break;
                default:
                    System.out.println("Dead-end reached.");
                    break;
            }
        } else if (currentCoord[1] < previousCoord[1]) {
            switch (ch) {
                case '-':
                    moveWest();
                    break;
                case 'L':
                    moveNorth();
                    break;
                case 'F':
                    moveSouth();    
                    break;
                default:
                    System.out.println("Dead-end reached.");
                    break;
            }
        } else if (currentCoord[1] > previousCoord[1]) {
            switch (ch) {
                case '-':
                    moveEast();
                    break;
                case 'J':
                    moveNorth();
                    break;
                case '7':
                    moveSouth();
                    break;
                default:
                    System.out.println("Dead-end reached.");
                    break;
            }
        } else {
            System.out.println("Current coordinates equal previous coordinates: Dead-end reached.");
        }
        previousCoord[0] = tempRow;
        previousCoord[1] = tempCol;
        steps++;
    }
}
