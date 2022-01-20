package puzzle;

import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

public class Day2P1 extends AbstractFilePuzzle {

    Map<Position, Integer> position = new EnumMap<>(Position.class);

    public Day2P1() {
        super("Day2");
        position.put(Position.X, 0);
        position.put(Position.Y, 0);
    }

    @Override
    protected void handleInput(String line) {
        if(line.contains(Direction.FORWARD.displayLabel)){
            Integer value = parseInput(line, Direction.FORWARD);
            position.compute(Position.X, (k, v) -> v+value);
        } else if (line.contains(Direction.DOWN.displayLabel)){
            Integer value = parseInput(line, Direction.DOWN);
            position.compute(Position.Y, (k,v) -> v+value);
        } else {
            Integer value = parseInput(line, Direction.UP);
            position.compute(Position.Y, (k, v) -> v-value);
        }
    }

    private Integer parseInput(String input, Direction direction){
        return Integer.parseInt(input.substring(direction.displayLabel.length() + 1));
    }

    @Override
    protected String generateResult() {
        return String.valueOf(position.values().stream().mapToInt(Integer::intValue).reduce((x, y) -> x*y).getAsInt());
    }

    protected enum Position {
        X,
        Y;
    }

    @RequiredArgsConstructor
    protected enum Direction {
        FORWARD("forward"),
        UP("up"),
        DOWN("down");

        private final String displayLabel;
    }
}
