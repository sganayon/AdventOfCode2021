package puzzle;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8P1 extends AbstractFilePuzzle {
    private List<String> outputValues = new ArrayList<>();
    private Map<Integer, List<Integer>> segmentToDigit = new HashMap<>();

    public Day8P1() {
        super("Day8");
        segmentToDigit.put(6, Stream.of(0,6,9).collect(Collectors.toList()));
        segmentToDigit.put(2, Collections.singletonList(1));
        segmentToDigit.put(5, Stream.of(2,3,5).collect(Collectors.toList()));
        segmentToDigit.put(4, Collections.singletonList(4));
        segmentToDigit.put(3, Collections.singletonList(7));
        segmentToDigit.put(7, Collections.singletonList(8));
    }

    @Override
    protected void handleInput(String line) {
        outputValues.addAll(Arrays.asList(line.substring(line.indexOf("|")+2).split(" ")));
    }

    @Override
    protected String generateResult() {
        return String.valueOf(outputValues.stream().mapToInt(String::length).filter(this::isUnique).count());
    }

    private boolean isUnique(int i){
        return segmentToDigit.get(i).size() == 1;
    }
}
