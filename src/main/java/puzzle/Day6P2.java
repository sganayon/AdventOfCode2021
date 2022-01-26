package puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day6P2 extends AbstractFilePuzzle {
    private Map<Integer, Long> fishsByAge;
    // Part 1 : DAYS=80, part 2 : DAYS=256
    private static final int DAYS=256;
    private static final int GESTATION_NOMINAL=6;
    private static final int GESTATION_NOUVEAU_NEE=8;

    public Day6P2() {
        super("Day6");
        fishsByAge = new HashMap<>();
        for(int i=0; i<=GESTATION_NOUVEAU_NEE; i++){
            fishsByAge.put(i,0L);
        }
    }

    @Override
    protected void handleInput(String line) {
        Stream.of(line.split(","))
                .map(Integer::parseInt)
                .forEach(integer -> fishsByAge.compute(integer, (age, number) -> ++number));
    }

    @Override
    protected String generateResult() {
        for(int i=1; i<=DAYS; i++){
            decrementFishsGestationAndAddNewBorn();
        }
        return String.valueOf(fishsByAge.values().stream().mapToLong(Long::longValue).sum());
    }

    private void decrementFishsGestationAndAddNewBorn(){
        // Pour chaque age partant de 8 à 1 on sauvegarde la valeur de l'age suivant et on l'écrase par la valeur de l'age actuel
        // Si 8:2, 7:6, 6:1 => 8:X, 7:2, 6:6, ...
        long lastAgeNumberOfFish = fishsByAge.get(GESTATION_NOUVEAU_NEE);
        for(int i=GESTATION_NOUVEAU_NEE; i>0; i--){
            long tmpValue = fishsByAge.get(i-1);
            fishsByAge.put(i-1, lastAgeNumberOfFish);
            lastAgeNumberOfFish = tmpValue;
        }
        // Le cas age = 0 est particulier, lastAgeNumber contient l'ancienne valeur de 0 => on l'ajoute à l'age 6 et on écrase l'age 8 avec cette valeur
        // Un fish à 0 donne un fish à 6 et un fish à 8, attention l'age 6 contient des fishs qu'il ne faut pas perdre (les ancient 7)
        long finalLastAgeNumberOfFish = lastAgeNumberOfFish;
        fishsByAge.compute(GESTATION_NOMINAL, (age, number) -> number += finalLastAgeNumberOfFish);
        fishsByAge.put(GESTATION_NOUVEAU_NEE, lastAgeNumberOfFish);
    }
}
