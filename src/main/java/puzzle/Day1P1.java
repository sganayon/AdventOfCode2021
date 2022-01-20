package puzzle;

import lombok.extern.slf4j.Slf4j;
import utility.FileReader;
import utility.ItemReader;

import java.util.Objects;


@Slf4j
public class Day1P1 implements Puzzle {
    private Integer before;

    @Override
    public String solve() {
        ItemReader fileReader = new FileReader("puzzle/Day1");
        long result = fileReader.readAll()
                .mapToInt(Integer::valueOf)
                .filter(Objects::nonNull)
                .filter(this::biggerThanBefore)
                .count();

        return String.valueOf(result);
    }



    public boolean biggerThanBefore(Integer integer){
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
}
