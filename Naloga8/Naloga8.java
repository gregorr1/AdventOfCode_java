package Naloga8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Naloga8 {
    public static List<String> getInput() {
        String path = "input/input8.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga8_1() {
        /* One of the camel's pouches is labeled "maps" - sure enough, it's full of documents (your puzzle input) about how to navigate the desert. At least, you're pretty sure that's what they are; one of the documents contains a list of left/right instructions, and the rest of the documents seem to describe some kind of network of labeled nodes. This format defines each node of the network individually.
        After examining the maps for a bit, two nodes stick out: AAA and ZZZ. You feel like AAA is where you are now, and you have to follow the left/right instructions until you reach ZZZ.
        Starting at AAA, follow the left/right instructions. How many steps are required to reach ZZZ? */

        List<String> input = getInput();
        List<Node> nodes = new ArrayList<>();
        String instruction = input.get(0);

        // Delete the instruction line and fill the list of nodes
        input.subList(0, 2).clear();
        for (String line : input) {
            String[] substrings = line.split("=|,");
            String nodeName = substrings[0].trim().replaceAll("[()]", "");
            String nodeLeft = substrings[1].trim().replaceAll("[()]", "");
            String nodeRight = substrings[2].trim().replaceAll("[()]", "");
            nodes.add(new Node(nodeName, nodeLeft, nodeRight));
        }
        
        // Loop through the list of nodes using given instruction
        String nodeName = "AAA";
        int instructionIndex = 0;
        int counter = 0;
        while (!nodeName.equals("ZZZ")) {
            for (Node node : nodes) {
                if (nodeName.equals(node.getName())) {
                    if (instruction.charAt(instructionIndex) == 'L') {
                        nodeName = node.getLeftNode();
                    } else {
                        nodeName = node.getRightNode();
                    }
                    counter++;
                    instructionIndex++;

                    // Instruction must be restarted when the end of the string is reached
                    if (instructionIndex >= instruction.length()) {
                        instructionIndex = 0;
                    }
                    break;
                }
            }
        }
        System.out.println("It takes " + counter + " steps.");
    }

    public static void naloga8_2() {
        /* After examining the maps a bit longer, your attention is drawn to a curious fact: the number of nodes with names ending in A is equal to the number ending in Z! If you were a ghost, you'd probably just start at every node that ends with A and follow all of the paths at the same time until they all simultaneously end up at nodes that end with Z.
        Simultaneously start on every node that ends with A. How many steps does it take before you're only on nodes that end with Z? */

        List<String> input = getInput();
        List<Node> nodes = new ArrayList<>();
        String instruction = input.get(0);

        // Delete the instruction line and fill the list of nodes
        input.subList(0, 2).clear();
        for (String line : input) {
            String[] substrings = line.split("=|,");
            String nodeName = substrings[0].trim().replaceAll("[()]", "");
            String nodeLeft = substrings[1].trim().replaceAll("[()]", "");
            String nodeRight = substrings[2].trim().replaceAll("[()]", "");
            nodes.add(new Node(nodeName, nodeLeft, nodeRight));
        }
        
        // Create maps for faster searching for a node to the left or right of a given node
        Map<String, String> rightNodeMap = new HashMap<>();
        Map<String, String> leftNodeMap = new HashMap<>();
        for (Node node : nodes) {
            rightNodeMap.put(node.getName(), node.getRightNode());
            leftNodeMap.put(node.getName(), node.getLeftNode());
        }

        // Create a list of starting node names and transform to an array for faster processing
        List<String> startingNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getName().charAt(2) == 'A') {
                startingNodes.add(node.getName());
            }
        }
        String[] currentNodes = startingNodes.toArray(new String[0]);
        
        // Find the intervals between two occurrences of a final 'Z' in node name
        List<Long> intervals = new ArrayList<>();
        for (int i = 0; i < currentNodes.length; i++) {
            long counter = 0;
            int instructionIndex = 0;
            while (true) {
                if (instruction.charAt(instructionIndex) == 'L') {
                    currentNodes[i] = leftNodeMap.get(currentNodes[i]);
                } else {
                    currentNodes[i] = rightNodeMap.get(currentNodes[i]);
                }
                counter++;
                instructionIndex++;
                if (instructionIndex >= instruction.length()) {
                    instructionIndex = 0;
                }
                if (currentNodes[i].charAt(2) == 'Z') {
                    intervals.add(counter);
                    break;
                }
            }
        }
        // Using the fuction below, it can be concluded that the interval for each starting node is a fixed number
        // checkIntervals(intervals, instruction, currentNodes, leftNodeMap, rightNodeMap);

        // Since nodes are traversed in a loop, each at a fixed interval, the solution to this problem is the least common multiple of all intervals
        long steps = findLcm(intervals);
        System.out.println("It takes " + steps + " steps.");
    }

    // Input a list of numbers to return the least common multiple of all list elements
    public static long findLcm(List<Long> listNumbers) {
        long lcm = listNumbers.get(0);
        long step = lcm;
        long next = 0;
        for (int i = 0; i < listNumbers.size() - 1; i++) {
            try {
                next = listNumbers.get(i + 1);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            while (lcm % next != 0) {
                lcm += step;
            }
            step = lcm;
        }
        return lcm;
    }

    // Test that proves that in the first 1000 occurrences of 'Z' in each node, the interval between two occurrences is a fixed number
    public static void checkIntervals(List<Long> intervals, String instruction, String[] nodes, Map<String, String> leftNodeMap, Map<String, String> rightNodeMap) {
        for (int i = 0; i < intervals.size(); i++) {
            int iterator = 1;
            long counter = 0;
            int instructionIndex = 0;
            while (iterator < 1000) {
                if (instruction.charAt(instructionIndex) == 'L') {
                    nodes[i] = leftNodeMap.get(nodes[i]);
                } else {
                    nodes[i] = rightNodeMap.get(nodes[i]);
                }
                counter++;
                instructionIndex++;
                if (instructionIndex >= instruction.length()) {
                    instructionIndex = 0;
                }
                if (nodes[i].charAt(2) == 'Z') {
                    if (counter % intervals.get(i) != 0) {
                        System.out.println("Error! Counter not at expected value.");
                        break;
                    }
                    iterator++;
                }
            }
        }
        System.out.println("Test passed.");
    }
}
