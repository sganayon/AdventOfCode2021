package puzzle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6P1 extends AbstractFilePuzzle {
    private List<Integer> fishs;
    // Part 1 : DAYS=80, part 2 : DAYS=256
    private static final int DAYS=80;

    public Day6P1() {
        super("Day6");
    }

    @Override
    protected void handleInput(String line) {
        fishs = Stream.of(line.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    protected String generateResult() {
        for(int i=1; i<=DAYS; i++){
            int newBorn = decrementFishsAndGetNewBorn();
            addNewBorn(newBorn);
        }
        return String.valueOf(fishs.size());
    }

    private int decrementFishsAndGetNewBorn(){
        int newBorn = 0;
        for(int i=0; i<fishs.size(); i++){
            int fish = fishs.get(i);
            if(fish == 0){
                newBorn++;
                fishs.set(i, 6);
            } else {
                fishs.set(i, --fish);
            }
        }
        return newBorn;
    }

    private void addNewBorn(int number){
        for(int i=0; i<number; i++){
            fishs.add(8);
        }
    }
}
