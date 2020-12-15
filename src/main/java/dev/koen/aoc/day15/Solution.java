package dev.koen.aoc.day15;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {
        final Map<Integer, Integer> lastOccurences = new HashMap<>();
        lastOccurences.put(0, 0);
        lastOccurences.put(1, 1);
        lastOccurences.put(5, 2);
        lastOccurences.put(10, 3);
        lastOccurences.put(3, 4);
        lastOccurences.put(12, 5);
        lastOccurences.put(19, 6);

        var current = 0;
        var numberOfIterations = lastOccurences.size();
        while (numberOfIterations < 30_000_000 - 1) {
            final var prevIndex = lastOccurences.getOrDefault(current, numberOfIterations);
            lastOccurences.put(current, numberOfIterations);
            current = numberOfIterations - prevIndex;
            numberOfIterations++;
        }
        System.out.println(current);
    }

}
