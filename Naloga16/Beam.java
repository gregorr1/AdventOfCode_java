package Naloga16;

import java.util.List;

public class Beam {
    private int row;
    private int column;
    private int direction;

    public Beam(int row, int column, int direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    // Copy constructor for Beam object, adding 2 to direction for the split beam
    public Beam(Beam beam) {
        this.row = beam.row;
        this.column = beam.column;
        this.direction = beam.direction + 2;
    }

    public Beam() {
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int rowModifier() {
        switch (this.direction % 4) {
            case 0:
                return -1;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public int columnModifier() {
        switch (this.direction % 4) {
            case 1:
                return 1;
            case 3:
                return -1;
            default:
                return 0;
        }
    }

    // Based on row and column modifier, determined from object's direction, check if the next field on the object's path is within bounds
    public boolean checkGrid(List<String> input) {
        if (this.row >= 0 && this.row < input.get(0).length()
        && this.column >= 0 && this.column < input.size()) {
            return true;
        } else {
            return false;
        }
    }

    // Direction is changed clockwise, direction % 4 == 0 means UP, 1 means RIGHT, 2 means DOWN, 3 means LEFT. Default is no change (0), in case direction is changed, change should be modified.
    public int changeDirection(List<String> input) {
        char mirror = input.get(this.row).charAt(this.column);
        int currDirection = this.direction % 4;
        int change = 0;
        switch (mirror) {
            // If mirror is '\' and beam is moving up or down, it should be changed counter-clockwise: left or right respectively
            case '\\':
                if (currDirection == 0 || currDirection == 2) {
                    change += 3;
                } else {
                    change++;
                }
                break;
            // If mirror is '/' and beam is moving right or left, it should be changed counter-clockwise: up or down respectively
            case '/':
                if (currDirection == 1 || currDirection == 3) {
                    change += 3;
                } else {
                    change++;
                }
                break;
            // No change if beam is moving left or right, but the beam splits if moving up or down
            case '-':
                if (currDirection == 0 || currDirection == 2) {
                    change += 5;
                }
                break;
            // No change if beam is moving up or down, but the beam splits if moving left or right
            case '|':
                if (currDirection == 1 || currDirection == 3) {
                    change += 5;
                }
                break;
            default:
                break;
        }
        return change;
    }

    // Check if the current field contains a mirror
    public boolean checkMirror(List<String> input) {
        if (input.get(this.row).charAt(this.column) != '.') {
            return true;
        } else {
            return false;
        }
    }

    // This method may trigger an Index out of bounds error, so it is called only in the loop where checkGrid returns true
    public void move(List<String> input) {
        this.row += rowModifier();
        this.column += columnModifier();
    }
}
