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
        List<Long> seedsList = new ArrayList<>();
        List<Long> locationNumbers = new ArrayList<>();
        List<MapElement> seedToSoilMap = new ArrayList<>();
        List<MapElement> soilToFertilizerMap = new ArrayList<>();
        List<MapElement> fertilizerToWaterMap = new ArrayList<>();
        List<MapElement> waterToLightMap = new ArrayList<>();
        List<MapElement> lightToTemperatureMap = new ArrayList<>();
        List<MapElement> temperatureToHumidityMap = new ArrayList<>();
        List<MapElement> humidityToLocationMap = new ArrayList<>();

        // Create a list of lists in correct order in order to iterate over all lists
        List<List<MapElement>> almanachLists = new ArrayList<>();
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
            for(List<MapElement> list : almanachLists) {

                // For each seed and almanach list, create a list of all MapElements where source is lower or equal than seed or subsequently mapped number. If no such MapElement is found, the number is mapped to the same number and we continue with the next list/seed.
                List<MapElement> lowerSources = new ArrayList<>();
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
        List<Long> seedsList = new ArrayList<>();
        List<MapElement> seedToSoilMap = new ArrayList<>();
        List<MapElement> soilToFertilizerMap = new ArrayList<>();
        List<MapElement> fertilizerToWaterMap = new ArrayList<>();
        List<MapElement> waterToLightMap = new ArrayList<>();
        List<MapElement> lightToTemperatureMap = new ArrayList<>();
        List<MapElement> temperatureToHumidityMap = new ArrayList<>();
        List<MapElement> humidityToLocationMap = new ArrayList<>();

        // Create a list of lists in correct order in order to iterate over all lists
        List<List<MapElement>> almanachLists = new ArrayList<>();
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
        for(List<MapElement> list : almanachLists) {
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

    public static void naloga5_2_1() {
        /* Everyone will starve if you only plant such a small number of seeds. Re-reading the almanac, it looks like the seeds: line actually describes ranges of seed numbers.
        The values on the initial seeds: line come in pairs. Within each pair, the first value is the start of the range and the second value is the length of the range.
        What is the lowest location number that corresponds to any of the initial seed numbers? */

        // This is a faster version of the code from naloga5_2(), it takes around 60 ms to run (measured with Intel Core i5-8250U with 8 GB RAM). Although ranges are already defined in Apache Commons, I decided to define the class and functions from scratch for practice.
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
        List<NumRange> rangesList = new ArrayList<>();
        List<MapElement> seedToSoilMap = new ArrayList<>();
        List<MapElement> soilToFertilizerMap = new ArrayList<>();
        List<MapElement> fertilizerToWaterMap = new ArrayList<>();
        List<MapElement> waterToLightMap = new ArrayList<>();
        List<MapElement> lightToTemperatureMap = new ArrayList<>();
        List<MapElement> temperatureToHumidityMap = new ArrayList<>();
        List<MapElement> humidityToLocationMap = new ArrayList<>();

        // Create a list of lists in correct order in order to iterate over all lists
        List<List<MapElement>> almanachLists = new ArrayList<>();
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
        for(List<MapElement> list : almanachLists) {
            Collections.sort(list, Comparator.comparing(MapElement::getSource));
        }

        // Fill the list of ranges
        String[] seeds = input.get(0).split(" ");
        long tempStart = -1;
        long tempEnd = -1;
        boolean start = true;
        for(String s : seeds) {
            try {
                long seed = Long.parseLong(s);
                if (start) {
                    tempStart = seed;
                } else {
                    if (tempStart >= 0) {
                        tempEnd = tempStart + seed - 1;
                        rangesList.add(new NumRange(tempStart, tempEnd));
                    }
                }
                start = !start;
            } catch (Exception e) {
                continue;
            }
        }

        // Run checkRange() function on all ranges and find the lowest location number
        for(NumRange range : rangesList) {
            long returnedValue = checkRange(range, almanachLists);
            if(returnedValue < lowestLocationNum) {
                lowestLocationNum = returnedValue;
            }
        }
        
        System.out.println("The lowest location number is " + lowestLocationNum);
        stopWatch.stop();
        System.out.println("This took " + stopWatch.getElapsedTime() + " millisec");
    }

    // Find the highest source among sources lower or equal than temp number. If source + range is greater than seed or currently mapped number, this number is in the map and is therefore mapped. If source + range is lower, this number is not in the map and remains unchanged.
    public static long checkSeed(long seed, List<List<MapElement>> almanachLists) {
        long tempNum = seed;
        for(List<MapElement> list : almanachLists) {         
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
    
    
    public static long checkRange(NumRange inputRange, List<List<MapElement>> almanachLists) {
        List<NumRange> listRanges = new ArrayList<>();
        listRanges.add(inputRange);
        List<NumRange> listNewRanges = new ArrayList<>();
        for(List<MapElement> list : almanachLists) {
            for(NumRange range : listRanges) {
                NumRange tempRange = new NumRange(range.getRangeStart(), range.getRangeEnd());
                
                // A loop is initialized that creates new ranges based on sources and destination in all lists
                while(true) {
                    long newRangeStart;
                    long newRangeEnd;
                    long tempRangeStart = tempRange.getRangeStart();
                    long tempRangeEnd = tempRange.getRangeEnd();
                    int i = -1;
                    while (i + 1 < list.size() && tempRangeStart >= list.get(i + 1).getSource()) {
                        i++;
                    }
                    if(i >= 0) {
                        MapElement maxElement = list.get(i);
                        if(tempRangeStart < maxElement.getSource() + maxElement.getRange()) {
                            newRangeStart = maxElement.getDestination() + (tempRangeStart - maxElement.getSource());
                            if(tempRangeEnd < maxElement.getSource() + maxElement.getRange()) {
                                // If both tempRangeStart and tempRangeEnd are lower than source and range of maxElement, a new mapped range is created and the loop ends as we've reached the end of tempRange
                                newRangeEnd = maxElement.getDestination() + (tempRangeEnd - maxElement.getSource());
                                listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                                break;
                            } else {
                                // If tempRangeEnd is not lower than source + range of maxElement, a new mapped range is created and tempRangeStart is moved up to be higher than newRangeEnd
                                newRangeEnd = maxElement.getDestination() + maxElement.getRange() - 1;
                                listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                                tempRange.setRangeStart(maxElement.getSource() + maxElement.getRange());
                            }
                        } else {
                            newRangeStart = tempRangeStart;
                            if(i < list.size() - 1 && list.get(i + 1).getSource() <= tempRangeEnd) {
                                // If i is not the last index and the next element's source is lower than tempRangeEnd, a new range with unchanged values is created and tempRangeStart is moved up to be equal to next element's source
                                newRangeEnd = list.get(i + 1).getSource() - 1;
                                listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                                tempRange.setRangeStart(list.get(i + 1).getSource());
                            } else {
                                // If i is the last index or the next element's source is higher than tempRangeEnd, a new range with unchanged values is created and the loop ends as we've reached the end of tempRange
                                newRangeEnd = tempRangeEnd;
                                listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                                break;
                            }
                        }
                    } else {
                        // If i is lower than 0, the tempRangeStart is lower than the first element's source. If tempRangeEnd is lower as well, a new element with unchanged values is created and the loop ends as we've reached the end of tempRange; if tempRangeEnd is equal or higher, a new range with unchanged values is created and tempRangeStart is increased to be equal to the first element's source.
                        newRangeStart = tempRangeStart;
                        if(list.get(0).getSource() > tempRangeEnd) {
                            newRangeEnd = tempRangeEnd;
                            listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                            break;
                        } else {
                            newRangeEnd = list.get(0).getSource() - 1;
                            listNewRanges.add(new NumRange(newRangeStart, newRangeEnd));
                            tempRange.setRangeStart(list.get(0).getSource());
                        }
                    }                        
                }
            }
            listRanges = new ArrayList<>(listNewRanges);
            listNewRanges.clear();
        }

        // The final list of ranges are ranges of location numbers, which are then sorted and the lowest rangeStart is output as the lowest location number of this input range
        Collections.sort(listRanges, Comparator.comparing(NumRange::getRangeStart));
        return listRanges.get(0).getRangeStart();
    }
}
