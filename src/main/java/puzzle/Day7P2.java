package puzzle;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7P2 extends AbstractFilePuzzle {
    private Map<Integer, Integer> positionsCountMap;
    private int min;
    private int max;

    public Day7P2() {
        super("Day7");
    }

    @Override
    protected void handleInput(String line) {
        positionsCountMap = Stream.of(line.split(",")).map(Integer::parseInt).collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
        min = Collections.min(positionsCountMap.keySet());
        max = Collections.max(positionsCountMap.keySet());
    }

    @Override
    protected String generateResult() {
        // not 99541469, 70358015
        long result = IntStream.rangeClosed(min, max)
                .mapToLong(this::computeFuelCost)
                .min()
                .orElseThrow(() -> new NoSuchElementException("no min"));

        return String.valueOf(result);
    }

    private long computeFuelCost(int pos){
        long fuelCost = 0;
        for(Map.Entry<Integer, Integer> positionCount : positionsCountMap.entrySet()){
            int distance = Math.abs(positionCount.getKey() - pos);
            fuelCost += fuelCostFor(distance) * positionCount.getValue();
        }
        return fuelCost;
    }

    private long fuelCostFor(int distance){
        return IntStream.range(1, distance+1).sum();
    }
}
