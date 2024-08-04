package Naloga5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tools.StopWatch;

public class Naloga5 {
    public static void naloga5_1() {
        /* The almanac starts by listing which seeds need to be planted. The rest of the almanac contains a list of maps which describe how to convert numbers from a source category into numbers in a destination category. That is, the section that starts with seed-to-soil map: describes how to convert a seed number (the source) to a soil number (the destination). This lets the gardener and his team know which soil to use with which seeds, which water to use with which fertilizer, and so on.
        Rather than list every source number and its corresponding destination number one by one, the maps describe entire ranges of numbers that can be converted. Each line within a map contains three numbers: the destination range start, the source range start, and the range length.
        Any source numbers that aren't mapped correspond to the same destination number.
        What is the lowest location number that corresponds to any of the initial seed numbers? */

        String path = "input/input5.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        input.removeIf(s -> s.isBlank()); // Remove empty strings

        // Instantiate all lists of Longs and MapElements
        ArrayList<Long> seedsList = new ArrayList<>();
        ArrayList<Long> locationNumbers = new ArrayList<>();
        ArrayList<MapElement> seedToSoilMap = new ArrayList<>();
        ArrayList<MapElement> soilToFertilizerMap = new ArrayList<>();
        ArrayList<MapElement> fertilizerToWaterMap = new ArrayList<>();
        ArrayList<MapElement> waterToLightMap = new ArrayList<>();
        ArrayList<MapElement> lightToTemperatureMap = new ArrayList<>();
        ArrayList<MapElement> temperatureToHumidityMap = new ArrayList<>();
        ArrayList<MapElement> humidityToLocationMap = new ArrayList<>();

        // Create a list of lists in correct order in order to iterate over all lists
        ArrayList<ArrayList<MapElement>> almanachLists = new ArrayList<>();
        almanachLists.add(seedToSoilMap);
        almanachLists.add(soilToFertilizerMap);
        almanachLists.add(fertilizerToWaterMap);
        almanachLists.add(waterToLightMap);
        almanachLists.add(lightToTemperatureMap);
        almanachLists.add(temperatureToHumidityMap);
        almanachLists.add(humidityToLocationMap);
        
        // Fill the list of seeds
        String[] seeds = input.get(0).split(" ");
        for(String s : seeds) {
            try {
                Long seed = Long.parseLong(s);
                seedsList.add(seed);   
            } catch (Exception e) {
                continue;
            }
        }

        // Fill all almanach lists by either creating a new MapElement or switching to a new list
        int j = 0;
        for(int i = 2; i < input.size(); i++) {
            try {
                String[] nums = input.get(i).split(" ");
                almanachLists.get(j).add(new MapElement(Long.parseLong(nums[0]), Long.parseLong(nums[1]), Long.parseLong(nums[2])));
            } catch (Exception e) {
                j++;
                continue;
            }
        }

        for(long seed : seedsList) {
            long tempNum = seed;
            for(ArrayList<MapElement> list : almanachLists) {

                // For each seed and almanach list, create a list of all MapElements where source is lower or equal than seed or subsequently mapped number. If no such MapElement is found, the number is mapped to the same number and we continue with the next list/seed.
                ArrayList<MapElement> lowerSources = new ArrayList<>();
                for(MapElement e : list) {
                    if(tempNum >= e.getSource()) {
                        lowerSources.add(e);
                    }
                }
                if(!lowerSources.isEmpty()) {
                    
                    // Find the highest source among sources lower or equal than temp number. If source + range is greater than seed or currently mapped number, this number is in the map and is therefore mapped. If source + range is lower, this number is not in the map and remains unchanged.
                    int maxIndex = 0;
                    for(int i = 0; i < lowerSources.size(); i++) {
                        if(lowerSources.get(i).getSource() > lowerSources.get(maxIndex).getSource()) {
                            maxIndex = i;
                        }
                    }
                    MapElement maxElement = lowerSources.get(maxIndex);
                    if(tempNum <= maxElement.getSource() + maxElement.getRange()) {
                        tempNum = maxElement.getDestination() + (tempNum - maxElement.getSource());
                    }
                }
            }
            locationNumbers.add(tempNum);
        }
        locationNumbers.sort(null);
        System.out.println("The lowest location number is " + locationNumbers.get(0));
    }

    public static void naloga5_2() {
        /* Everyone will starve if you only plant such a small number of seeds. Re-reading the almanac, it looks like the seeds: line actually describes ranges of seed numbers.
        The values on the initial seeds: line come in pairs. Within each pair, the first value is the start of the range and the second value is the length of the range.
        What is the lowest location number that corresponds to any of the initial seed numbers? */

        // Please note that while this code works and outputs the correct result, it takes 6 min 12 s to run (measured with Intel Core i5-8250U with 8 GB RAM). A new version with ranges is in the works :)
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String path = "input/input5.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
            return;
        }
        input.removeIf(s -> s.isBlank()); // Remove empty strings
        long lowestLocationNum = Long.MAX_VALUE;

        // Instantiate all lists of Longs and MapElements
        ArrayList<Long> seedsList = new ArrayList<>();
        ArrayList<MapElement> seedToSoilMap = new ArrayList<>();
        ArrayList<MapElement> soilToFertilizerMap = new ArrayList<>();
        ArrayList<MapElement> fertilizerToWaterMap = new ArrayList<>();
        ArrayList<MapElement> waterToLightMap = new ArrayList<>();
        ArrayList<MapElement> lightToTemperatureMap = new ArrayList<>();
        ArrayList<MapElement> temperatureToHumidityMap = new ArrayList<>();
        ArrayList<MapElement> humidityToLocationMap = new ArrayList<>();

        // Create a list of lists in correct order in order to iterate over all lists
        ArrayList<ArrayList<MapElement>> almanachLists = new ArrayList<>();
        almanachLists.add(seedToSoilMap);
        almanachLists.add(soilToFertilizerMap);
        almanachLists.add(fertilizerToWaterMap);
        almanachLists.add(waterToLightMap);
        almanachLists.add(lightToTemperatureMap);
        almanachLists.add(temperatureToHumidityMap);
        almanachLists.add(humidityToLocationMap);
        
        // Fill all almanach lists by either creating a new MapElement or switching to a new list, then sort them by source
        int j = 0;
        for(int i = 2; i < input.size(); i++) {
            try {
                String[] nums = input.get(i).split(" ");
                almanachLists.get(j).add(new MapElement(Long.parseLong(nums[0]), Long.parseLong(nums[1]), Long.parseLong(nums[2])));
            } catch (Exception e) {
                j++;
                continue;
            }
        }

        // In order to avoid creating a new list of all MapElements where source is lower or equal than temp number, the lists are first sorted in this example
        for(ArrayList<MapElement> list : almanachLists) {
            Collections.sort(list, Comparator.comparing(MapElement::getSource));
        }

        // Fill the list of seeds
        String[] seeds = input.get(0).split(" ");
        for(String s : seeds) {
            try {
                Long seed = Long.parseLong(s);
                seedsList.add(seed);   
            } catch (Exception e) {
                continue;
            }
        }

        // Add a new loop that copies seeds at even indices and creates a loop for odd indices, working with the number immediately before it. Then run checkSeed() method on each seed, replacing the lowest location number when found.
        for(int i = 0; i < seedsList.size(); i++) {
            if(i%2 != 0) {
                for(long k = 1; k < seedsList.get(i); k++) {
                    long tempLocation = checkSeed((seedsList.get(i - 1) + k), almanachLists);
                    if(tempLocation < lowestLocationNum) {
                        lowestLocationNum = tempLocation;
                    }
                }
            } else {
                long tempLocation = checkSeed(seedsList.get(i), almanachLists);
                if(tempLocation < lowestLocationNum) {
                    lowestLocationNum = tempLocation;
                }
            }
        }
        System.out.println("The lowest location number is " + lowestLocationNum);
        stopWatch.stop();
        System.out.println("This took " + stopWatch.getElapsedTimeSecs() + " sec");
    }

    // Find the highest source among sources lower or equal than temp number. If source + range is greater than seed or currently mapped number, this number is in the map and is therefore mapped. If source + range is lower, this number is not in the map and remains unchanged.
    public static long checkSeed(long seed, ArrayList<ArrayList<MapElement>> almanachLists) {
        long tempNum = seed;
        for(ArrayList<MapElement> list : almanachLists) {         
            int i = -1;    
            while (i + 1 < list.size() && tempNum >= list.get(i + 1).getSource()) {
                i++;
            }
            if(i >= 0) {
                MapElement maxElement = list.get(i);
                if(tempNum <= maxElement.getSource() + maxElement.getRange()) {
                    tempNum = maxElement.getDestination() + (tempNum - maxElement.getSource());
                }
            }   
        }
        return tempNum;
    }
}
