package puzzle;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day3P2 extends AbstractFilePuzzle {
    private static final int SIZE = 12;
    private final List<BitSet> rows = new ArrayList<>();

    public Day3P2() {
        super("Day3");
    }

    @Override
    protected void handleInput(String line) {
        BitSet bits = new BitSet(SIZE);
        // Attention on met le bits de poid fort dans l'indice 0
        for(int i=0; i<line.length(); i++){
            Integer value = Integer.parseInt(String.valueOf(line.charAt(i)));
            if(value.equals(1)) {
                bits.set(i);
            }
        }
        rows.add(bits);
    }

    @Override
    protected String generateResult() {
        List<BitSet> oxygen = new ArrayList<>(rows); //copy des lignes
        rows.clear(); // clear final list pour éviter d'avoir les objets en triples (reduce re-affecte la list)
        List<BitSet> co2 = new ArrayList<>(oxygen); // copy des lignes

        BitSet oxygenBitSet = reduce(oxygen, true);
        BitSet co2BitSet = reduce(co2, false);
        int oxygenValue = (int) reverseEndian(oxygenBitSet).toLongArray()[0];
        int co2Value = (int) reverseEndian(co2BitSet).toLongArray()[0];

        return String.valueOf(oxygenValue * co2Value);
    }

    private BitSet reduce(List<BitSet> list, boolean keepMost){
        log.info("");
        log.info("========== list : {}", list);
        for(int i=0; i<SIZE; i++){
            if(list.size() == 1){
                log.info("value found for keepMost[{}] : {}", keepMost, list.get(0));
                return list.get(0);
            } else {
                int finalI = i;
                long count = list.stream()
                        .map(row -> row.get(finalI)) // true si le bit vaut 1, false sinon
                        .mapToInt(b -> b.compareTo(false)) // si b = false alors r = 0, sinon r = 1
                        .sum();

                log.info("number of 1*2 : {} out of {}, Iteration : {}, keepMost : {}", count*2, list.size(), finalI, keepMost);
                // Si on a plus de 1 (ou égale)
                if(count*2 >= list.size()){
                    // garde toutes les valeurs ayant 1 sur le bit n°i si l'on garde les valeurs majoritaires, sinon on garde les 0
                    list = list.stream().filter(row -> row.get(finalI) == keepMost).collect(Collectors.toList());
                    log.info("new list without {} : {}", keepMost?"0":"1", list);
                } else {
                    // garde toutes les valeurs ayant 0 sur le bit n°i si l'on garde les valeurs majoritaires, sinon on garde les 1
                    list = list.stream().filter(row -> !row.get(finalI) == keepMost).collect(Collectors.toList());
                    log.info("new list without {} : {}", keepMost?"1":"0", list);
                }
            }
        }
        return null;
    }

    private BitSet reverseEndian(BitSet bitSet){
        BitSet reversed = new BitSet(SIZE);
        for(int i=0; i<SIZE; i++){
            if(bitSet.get(i)){
                reversed.set(SIZE -1 -i);
            }
        }
        log.info("Reverse {} to {}", bitSet, reversed);
        return reversed;
    }
}
