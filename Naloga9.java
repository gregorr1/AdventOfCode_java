import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga9 {
    public static List<String> getInput() {
        String path = "input/input9.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga9_1() {
        /* You pull out your handy Oasis And Sand Instability Sensor and analyze your surroundings. The OASIS produces a report of many values and how they are changing over time (your puzzle input). Each line in the report contains the history of a single value.
        To best protect the oasis, your environmental report should include a prediction of the next value in each history. To do this, start by making a new sequence from the difference at each step of your history. If that sequence is not all zeroes, repeat this process, using the sequence you just generated as the input sequence. Once all of the values in your latest sequence are zeroes, you can extrapolate what the next value of the original history should be.
        To extrapolate, start by adding a new zero to the end of your list of zeroes. Then, work out the next value in each sequence from the bottom up by increasing the value to its left by the value below it.
        Analyze your OASIS report and extrapolate the next value for each history. What is the sum of these extrapolated values? */

        List<String> input = getInput();
        long sum = 0;
        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            String[] substrings = line.split(" ");
            for (String substring : substrings) {
                try {
                    lineNumbers.add(Integer.parseInt(substring));
                } catch (Exception e) {
                    System.out.println("Error, cannot parse " + substring);
                    return;
                }
            }            
            sum += findNextElement(lineNumbers);
        }
        System.out.println("The sum of extrapolated values is " + sum);
    }

    public static void naloga9_2() {
        /* For each history, repeat the process of finding differences until the sequence of differences is entirely zero. Then, rather than adding a zero to the end and filling in the next values of each previous sequence, you should instead add a zero to the beginning of your sequence of zeroes, then fill in new first values for each previous sequence.
        Analyze your OASIS report again, this time extrapolating the previous value for each history. What is the sum of these extrapolated values? */

        List<String> input = getInput();
        long sum = 0;
        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            String[] substrings = line.split(" ");
            for (String substring : substrings) {
                try {
                    lineNumbers.add(Integer.parseInt(substring));
                } catch (Exception e) {
                    System.out.println("Error, cannot parse " + substring);
                    return;
                }
            }
            sum += findFirstWithLists(lineNumbers);
        }
        System.out.println("The sum of extrapolated values is " + sum);
    }

    // Since the next element of a sequence is also the sum of all previous last elements of list, this is a slightly faster method to find the next element by simply adding up the last element each time we create a new list instead of storing several lists.
    public static int findNextElement(List<Integer> numbers) {
        int lastElement = 0;
        int nextElement = 0;
        List<Integer> tempList = new ArrayList<>(numbers);
        List<Integer> lastElementList = new ArrayList<>();

        while (true) {
            lastElement = tempList.get(tempList.size() - 1);
            lastElementList.add(lastElement);
            if (lastElement == 0) {
                boolean checkForBreak = true;
                for (Integer element : tempList) {
                    if (element != 0) {
                        checkForBreak = false;
                        break;
                    }
                }
                if (checkForBreak) {
                    break;
                }
            }
            List<Integer> newList = new ArrayList<>();
            for (int i = 0; i < tempList.size() - 1; i++) {
                int element = tempList.get(i + 1) - tempList.get(i);
                newList.add(element);
            }
            tempList.clear();
            tempList.addAll(newList);
            newList.clear();
        }
        for (Integer num : lastElementList) {
            nextElement += num;
        }
        return nextElement;
    }

    public static int findNextWithLists(List<Integer> numbers) {
        List<List<Integer>> allLists = new ArrayList<>();
        List<Integer> inputList = new ArrayList<>(numbers);
        allLists.add(inputList);
        int lastIndexAllLists = 0;
        int nextElement = 0;

        // Fill the list of lists and stop adding new lists once all elements of the last added list are zeroes
        while (true) {
            List<Integer> previousList = allLists.get(lastIndexAllLists);
            boolean checkForBreak = true;

            for (Integer element : previousList) {
                if (element != 0) {
                    checkForBreak = false;
                    break;
                }
            }
            if (checkForBreak) {
                break;
            }

            List<Integer> newList = new ArrayList<>();
            for (int i = 0; i < previousList.size() - 1; i++) {
                int element = previousList.get(i + 1) - previousList.get(i);
                newList.add(element);
            }
            allLists.add(newList);
            lastIndexAllLists++;
        }

        // Calculate the last element of each list
        for (int i = allLists.size() - 1; i > 0; i--) {
            List<Integer> tempList = allLists.get(i);
            List<Integer> nextList = allLists.get(i - 1);
            int lastElementTempList = tempList.get((tempList.size()) - 1);
            int lastElementNextList = nextList.get((nextList.size()) - 1);
            nextList.add(lastElementNextList + lastElementTempList);
        }

        // The next element is the last element on the first (input) list on the list of lists
        nextElement = allLists.get(0).get(allLists.get(0).size() - 1);
        return nextElement;
    }

    public static int findFirstWithLists(List<Integer> numbers) {
        List<List<Integer>> allLists = new ArrayList<>();
        List<Integer> inputList = new ArrayList<>(numbers);
        allLists.add(inputList);
        int lastIndexAllLists = 0;
        int firstElement = 0;

        // Fill the list of lists and stop adding new lists once all elements of the last added list are zeroes
        while (true) {
            List<Integer> previousList = allLists.get(lastIndexAllLists);
            boolean checkForBreak = true;

            for (Integer element : previousList) {
                if (element != 0) {
                    checkForBreak = false;
                    break;
                }
            }
            if (checkForBreak) {
                break;
            }

            List<Integer> newList = new ArrayList<>();
            for (int i = 0; i < previousList.size() - 1; i++) {
                int element = previousList.get(i + 1) - previousList.get(i);
                newList.add(element);
            }
            allLists.add(newList);
            lastIndexAllLists++;
        }

        // Calculate the first element of each list
        for (int i = allLists.size() - 1; i > 0; i--) {
            List<Integer> tempList = allLists.get(i);
            List<Integer> nextList = allLists.get(i - 1);
            int firstElementTempList = tempList.get(0);
            int firstElementNextList = nextList.get(0);
            nextList.add(0, firstElementNextList - firstElementTempList);
        }

        // The first element is the first element on the first (input) list on the list of lists
        firstElement = allLists.get(0).get(0);
        return firstElement;
    }
}
