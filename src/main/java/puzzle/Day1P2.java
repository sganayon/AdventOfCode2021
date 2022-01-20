package puzzle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utility.FileReader;
import utility.ItemReader;

import java.util.LinkedList;
import java.util.Objects;

@Slf4j
public class Day1P2 implements Puzzle {
    private Integer before;
    private Accumulator accumulator;

    @Override
    public String solve() {
        accumulator = new Accumulator(3);
        ItemReader fileReader = new FileReader("puzzle/Day1");
        long result = fileReader.readAll()
                .map(Integer::valueOf)
                .map(accumulator::accumulateAndSum)
                .filter(Objects::nonNull)
                .filter(this::biggerThanBefore)
                .count();

        return String.valueOf(result);
    }

    private boolean biggerThanBefore(Integer integer){
        if(before == null) {
            before = integer;
            log.debug("First Input : {} not kept", integer);
            return false;
        }

        boolean keep = integer > before;
        log.debug("[{}] is bigger than [{}] : {}", integer, before, keep);
        before = integer;
        return keep;
    }

    @RequiredArgsConstructor
    private static final class Accumulator {
        private final int depth;
        private LinkedList<Integer> integers = new LinkedList<>();

        public Integer accumulateAndSum(Integer integer){
            if(integers.size() < depth){
                integers.addFirst(integer);
                if(integers.size() == depth){
                    log.debug("sum of {} is {}", integers, integers.stream().mapToInt(Integer::intValue).sum());
                    return integers.stream().mapToInt(Integer::intValue).sum();
                }
                log.debug("{} kept, returning null", integer);
                return null;
            }

            integers.removeLast();
            integers.addFirst(integer);

            log.debug("sum of {} is {}", integers, integers.stream().mapToInt(Integer::intValue).sum());
            return integers.stream().mapToInt(Integer::intValue).sum();
        }
    }
}
