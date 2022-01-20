package puzzle;

import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

public class Day2P2 extends AbstractFilePuzzle {

    Map<Position, Integer> position = new EnumMap<>(Position.class);

    public Day2P2() {
        super("Day2");
        position.put(Position.HORIZONTAL, 0);
        position.put(Position.AIM, 0);
        position.put(Position.DEPTH, 0);
    }

    @Override
    protected void handleInput(String line) {
        if(line.contains(Direction.FORWARD.displayLabel)){
            Integer value = parseInput(line, Direction.FORWARD);
            position.compute(Position.HORIZONTAL, (k, v) -> v+value);
            Integer aim = position.get(Position.AIM);
            position.compute(Position.DEPTH, (k, v) -> v+aim*value);
        } else if (line.contains(Direction.DOWN.displayLabel)){
            Integer value = parseInput(line, Direction.DOWN);
            position.compute(Position.AIM, (k, v) -> v+value);
        } else {
            Integer value = parseInput(line, Direction.UP);
            position.compute(Position.AIM, (k, v) -> v-value);
        }
    }

    private Integer parseInput(String input, Direction direction){
        return Integer.parseInt(input.substring(direction.displayLabel.length() + 1));
    }

    @Override
    protected String generateResult() {
        return String.valueOf(position.get(Position.DEPTH) * position.get(Position.HORIZONTAL));
    }

    protected enum Position {
        HORIZONTAL,
        AIM,
        DEPTH;
    }

    @RequiredArgsConstructor
    protected enum Direction {
        FORWARD("forward"),
        UP("up"),
        DOWN("down");

        private final String displayLabel;
    }
}
