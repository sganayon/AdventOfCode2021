package puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//https://www.baeldung.com/java-stream-integers-median-using-heap
public class Day7P1 extends AbstractFilePuzzle {
    private List<Integer> positions;

    public Day7P1() {
        super("Day7");
    }

    @Override
    protected void handleInput(String line) {
        positions = Stream.of(line.split(",")).map(Integer::parseInt).sorted().collect(Collectors.toList());
    }

    @Override
    protected String generateResult() {
        List<Long> results = new ArrayList<>();
        for(int pos : positions){
            results.add(computeFuelCost(pos));
        }
        return Collections.min(results).toString();
    }

    private long computeFuelCost(int pos){
        long fuelCost = 0;
        for(int position : positions){
            fuelCost += Math.abs(position - pos);
        }
        return fuelCost;
    }
}
