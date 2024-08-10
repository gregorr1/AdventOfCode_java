package Naloga3;

import java.util.List;

public class Gear {
    private int firstNumber = 0;
    private int secondNumber = 0;
    
    public Gear() { }

    public int getFirstNumber() {
        return firstNumber;
    }
    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }
    public int getSecondNumber() {
        return secondNumber;
    }
    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    // Enter the position of a digit in the input and determine if there are any digits before or after this digit. Return the entire number afterwards.
    public int getWholeNumber(int lineNumber, int lineIndex, List<String> input) {
        String stringNumber = "";
        int i = 1;
        while (true) {
            try {
                if (Character.isDigit(input.get(lineNumber).charAt(lineIndex - i))) {
                    i++;
                } else {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        int j = 1;
        while (true) {
            try {
                if (Character.isDigit(input.get(lineNumber).charAt(lineIndex - i + j))) {
                    stringNumber += input.get(lineNumber).charAt(lineIndex - i + j);
                    j++;
                } else {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        int number = Integer.parseInt(stringNumber);
        return number;
    }

    public void checkSurrounding(int lineNumber, int lineIndex, List<String> input) {
        
        // First check the places directly above and below. If these places contain a digit, this number would be counted twice, so we don't run the second loop. If these places do not contain a digit, we check the diagonal places and places directly to the left/right.
        for (int i = -1; i < 2; i++) {
            try {
                if (Character.isDigit(input.get(lineNumber + i).charAt(lineIndex))) {
                    if (firstNumber == 0) {
                        setFirstNumber(getWholeNumber(lineNumber + i, lineIndex, input));
                    } else if (secondNumber == 0) {
                        setSecondNumber(getWholeNumber(lineNumber + i, lineIndex, input));
                    } else {
                        // If * is adjacent to more than two part numbers, set value to 0 and return
                        setFirstNumber(0);
                        return;
                    }
                } else {
                    for (int j = -1; j < 2; j += 2) {
                        try {
                            if (Character.isDigit(input.get(lineNumber + i).charAt(lineIndex + j))) {
                                if (firstNumber == 0) {
                                    setFirstNumber(getWholeNumber(lineNumber + i, lineIndex + j, input));
                                } else if (secondNumber == 0) {
                                    setSecondNumber(getWholeNumber(lineNumber + i, lineIndex + j, input));
                                } else {
                                    setFirstNumber(0);
                                    return;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) { }
                    }
                }
            } catch (IndexOutOfBoundsException e) { }
        }
    }

    public int getValue() {
        return firstNumber * secondNumber;
    }
}
