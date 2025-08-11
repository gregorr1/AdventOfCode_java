package Naloga17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Naloga17 {
    public static List<String> getInput() {
        String path = "input/input17.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    /* Instructions available here: https://adventofcode.com/2023/day/17 */
    public static void naloga17_1() {
        List<String> input = getInput();

        // Create a Map of settled nodes and Priority Queue of unsettled nodes and add the first node among the unsettled nodes
        Map<String, Node> settledNodes = new HashMap<>();
        Queue<Node> unsettledNodes = new PriorityQueue<>();
        int maxSameMoves = 3;
        Node startingNode = new Node(new int[]{0, 0}, input.get(0).charAt(0) - '0', 0, 0);
        startingNode.setTotalLoss(0); // Top left node's loss does not apply to the crucible
        unsettledNodes.add(startingNode);
        
        // Run a modified Dijkstra's algorithm adding the new adjacent nodes among the unsettled nodes and filling the map of settled nodes
        while (!unsettledNodes.isEmpty()) {
            Node node = unsettledNodes.poll();
            String currentKey = node.getName();
            if (settledNodes.containsKey(currentKey)) {
                continue;
            }

            fillAdjacentNodes(node, input, settledNodes);
            for (Node adjNode : node.getAdjacentNodes()) {

                // We are adding an additional requirement that the node must not move more than the set number of times in any unchanged direction
                if (!checkPath(adjNode, maxSameMoves)) {
                    continue;
                }
                calculateTotalLoss(node, adjNode);
                unsettledNodes.add(adjNode);
            }
            settledNodes.put(currentKey, node);
        }

        // Find the smallest total loss by checking all settled nodes where the key begins with the coordinates of the bottom right node 
        String lastCoords = (input.size() - 1) + "X" + (input.get(0).length() - 1);
        int minLoss = Integer.MAX_VALUE;
        for (Map.Entry<String, Node> entry : settledNodes.entrySet()) {
            if (entry.getKey().startsWith(lastCoords)) {
                if (entry.getValue().getTotalLoss() < minLoss) {
                    minLoss = entry.getValue().getTotalLoss();
                }
            }
        }

        System.out.println("The result is " + minLoss);
    }

    public static void naloga17_2() {
        List<String> input = getInput();
        Map<String, Node> settledNodes = new HashMap<>();
        Queue<Node> unsettledNodes = new PriorityQueue<>();
        int minSameMoves = 4;
        int maxSameMoves = 10;
        Node startingNode = new Node(new int[]{0, 0}, input.get(0).charAt(0) - '0', 0, 0);
        startingNode.setTotalLoss(0);
        unsettledNodes.add(startingNode);
        
        while (!unsettledNodes.isEmpty()) {
            Node node = unsettledNodes.poll();
            String currentKey = node.getName();
            if (settledNodes.containsKey(currentKey)) {
                continue;
            }

            fillAdjacentNodes(node, input, settledNodes);
            for (Node adjNode : node.getAdjacentNodes()) {
                if (checkReversing(adjNode)) {
                    continue;
                }
                calculateTotalLoss(node, adjNode);
 
                // This algorithm works similarly to the one above, except here we introduce a set minimum number of moves in addition to the set maximum
                if (adjNode.getLastDirection() != node.getLastDirection()) {
                    if (!addMinMove(adjNode, input, minSameMoves)) {
                        continue;
                    }
                } else if (!checkPathUltra(adjNode, minSameMoves, maxSameMoves)) {
                    continue;
                }
                unsettledNodes.add(adjNode);
            }
            settledNodes.put(currentKey, node);
        }

        String lastCoords = (input.size() - 1) + "X" + (input.get(0).length() - 1);
        int minLoss = Integer.MAX_VALUE;
        for (Map.Entry<String, Node> entry : settledNodes.entrySet()) {
            if (entry.getKey().startsWith(lastCoords)) {
                if (entry.getValue().getTotalLoss() < minLoss) {
                    minLoss = entry.getValue().getTotalLoss();
                }
            }
        }

        System.out.println("The result is " + minLoss);
    }

    // Fill source node's adjacent nodes by checking up, down, left and right in the input
    public static void fillAdjacentNodes(Node node, List<String> input, Map<String, Node> settledNodes) {
        int x = node.getCoords()[0];
        int y = node.getCoords()[1];
        int height = input.size();
        int width = input.get(x).length();
        int currentDirection = node.getLastDirection();
        int currentCount = node.getSameDirectionCount();

        // Direction vectors
        int[][] directions = {
            {-1, 0, 1}, // up
            {1, 0, 2},  // down
            {0, -1, 3}, // left
            {0, 1, 4}   // right
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            int newDirection = direction[2];
            int newCount = (newDirection == currentDirection) ? currentCount + 1 : 1;

            // Check if the adjacent node is already settled and do not add it in this case
            if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
                String key = newX + "X" + newY + "X" + newDirection + "X" + newCount;
                Node adjNode = settledNodes.get(key);
                if (adjNode == null) {
                    int loss = input.get(newX).charAt(newY) - '0';
                    adjNode = new Node(new int[]{newX, newY}, loss, newDirection, newCount);
                    adjNode.setPath(node.getPath() + newDirection);
                    node.getAdjacentNodes().add(adjNode);
                }
            }
        }
    }

    // If the crucible has a minimum move in the set direction, check if it can move by staying within bounds
    public static boolean addMinMove(Node node, List<String> input, int minSameMoves) {
        int currentDirection = node.getLastDirection();
        int currentCount = node.getSameDirectionCount();
        int totalLoss = node.getTotalLoss();
        int[][] directions = {
            {-1, 0, 1}, // up
            {1, 0, 2},  // down
            {0, -1, 3}, // left
            {0, 1, 4}   // right
        };
        int newX = node.getCoords()[0];
        int newY = node.getCoords()[1];
        int height = input.size();
        int width = input.get(newX).length();
        
        // For each new move in the given direction, check if the new position is out of bounds and return the method as invalid if minimum move cannot be added by staying within bounds
        for (int i = currentCount; i < minSameMoves; i++) {
            newX += directions[currentDirection - 1][0];
            newY += directions[currentDirection - 1][1];
            node.setPath(node.getPath() + currentDirection);
            currentCount++;
            if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
                totalLoss += input.get(newX).charAt(newY) - '0';
            } else {
                return false;
            }
        }

        // Rewrite the attributes of the node after successfully moving
        node.setCoords(new int[]{newX, newY});
        node.setLoss(input.get(newX).charAt(newY) - '0');
        node.setTotalLoss(totalLoss);
        node.setSameDirectionCount(currentCount);
        node.setName();

        return true;
    }

    // Calculate adjacent node's total loss by adding up source node's total loss and the loss of adjacent node itself. Then compare with the adjacent node's current total loss and use the lower value. 
    public static void calculateTotalLoss(Node srcNode, Node adjNode) {
        int newTotalLoss = srcNode.getTotalLoss() + adjNode.getLoss();
        if (newTotalLoss < adjNode.getTotalLoss()) {
            adjNode.setTotalLoss(newTotalLoss);
        }
    }

    // Check if the last two moves were in opposite directions, which indicates an invalid path
    public static boolean checkReversing(Node node) {
        if (node.getPath().length() > 1) {
            String finalTwoChars = node.getPath().substring(node.getPath().length() - 2);
            String matcher = "12|21|34|43";
            if (finalTwoChars.matches(matcher)) {
                return true;
            }
        }
        return false;
    }

    // Check the path of non-Ultra crucible without a minimum nuber of same direction moves
    public static boolean checkPath(Node node, int maxSameMoves) {
        if (node.getPath().length() > 1) {
            if (checkReversing(node)) {
                return false;
            }
        } else {
            return true;
        }
        if (node.getSameDirectionCount() > maxSameMoves) {
            return false;
        }
        return true;
    }

    // Checking if path is valid by entering the minimum number of same direction moves and the maximum number
    public static boolean checkPathUltra(Node node, int minSameMoves, int maxSameMoves) {
        if (node.getSameDirectionCount() > maxSameMoves || node.getSameDirectionCount() < minSameMoves) {
            return false;
        }
        return true;
    }
}
