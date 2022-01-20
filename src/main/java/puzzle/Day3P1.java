package puzzle;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class Day3P1 extends AbstractFilePuzzle {
    private final static int SIZE = 12;
    private final List<List<Integer>> bits = new ArrayList<>();
    private final BitSet result = new BitSet(SIZE);

    public Day3P1() {
        super("Day3");
        for(int i=0; i<SIZE; i++){
            bits.add(new LinkedList<>());
        }
    }

    @Override
    protected void handleInput(String line) {
        for(int i=0; i<line.length(); i++){
            Integer value = Integer.parseInt(String.valueOf(line.charAt(i)));
            bits.get(i).add(value);
        }
    }

    @Override
    protected String generateResult() {
        for(int i=0; i<SIZE; i++){
            long count = bits.get(i).stream().mapToInt(Integer::intValue).sum();
            if(count > bits.get(i).size()/2){
                result.set(SIZE - 1 - i);
            }
        }

        log.info("bitset {}", result);
        int gamma = (int) result.toLongArray()[0];
        result.flip(0, SIZE);
        log.info("bitset {}", result);
        int epsilon = (int) result.toLongArray()[0];
        return String.valueOf(gamma * epsilon);
    }
}
