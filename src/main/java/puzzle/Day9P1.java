package puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day9P1 extends AbstractFilePuzzle {
    private List<List<Integer>> heightMap = new ArrayList<>();

    protected Day9P1() {
        super("Day9");
    }

    @Override
    protected void handleInput(String line) {
        heightMap.add(toInts(line.toCharArray()));
    }

    private List<Integer> toInts(char[] chars){
        List<Integer> ints = new ArrayList<>();
        for(char c : chars){
            ints.add(Integer.parseInt(String.valueOf(c)));
        }
        return ints;
    }

    @Override
    protected String generateResult() {
        return null;
    }
}
