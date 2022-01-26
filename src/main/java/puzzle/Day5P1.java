package puzzle;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5P1 extends AbstractFilePuzzle {
    private List<Line> lines = new ArrayList<>();

    public Day5P1() {
        super("Day5");
    }

    @Override
    protected void handleInput(String line) {
        int x1 = Integer.parseInt(line.substring(0, line.indexOf(',')));
        int y1 = Integer.parseInt(line.substring(line.indexOf(',')+1, line.indexOf(' ')));
        int x2 = Integer.parseInt(line.substring(line.lastIndexOf(' ')+1, line.lastIndexOf(',')));
        int y2 = Integer.parseInt(line.substring(line.lastIndexOf(',')+1));
        lines.add(new Line(new Point(x1, y1), new Point(x2, y2)));
    }

    @Override
    protected String generateResult() {
       Map<String, List<Point>> map = lines.stream()
               .filter(Line::isHoriOrVerti)
               .map(Line::getPoints)
               .flatMap(List::stream)
               .collect(Collectors.toMap(Point::toString, p -> {List<Point> list = new ArrayList<>(); list.add(p); return list;}, (l1, l2) -> {l1.addAll(l2); return l1;}));

        return String.valueOf(map.values().stream().filter(l -> l.size()>1).count());
    }

    @RequiredArgsConstructor
    private static final class Line{
        final Point first;
        final Point last;

        public boolean isHorizontal() {return first.getY() == last.getY();}
        public boolean isVertical() {return first.getX() == last.getX();}
        public boolean isHoriOrVerti() {return isHorizontal() || isVertical();}
        public int getMinX(){ return Math.min(first.x, last.x);}
        public int getMaxX(){ return Math.max(first.x, last.x);}
        public int getMinY(){ return Math.min(first.y, last.y);}
        public int getMaxY(){ return Math.max(first.y, last.y);}
        public boolean isFirstXSmallerThanLastXAndTheSameGoesForYs() { return (first.y < last.y) && (first.x < last.x) || (first.y >= last.y) && (first.x >= last.x);}

        public List<Point> getPoints() {
            if(isVertical()){
                return IntStream.rangeClosed(getMinY(), getMaxY()).mapToObj(y -> new Point(first.x, y)).collect(Collectors.toList());
            }
            if(isHorizontal()){
                return IntStream.rangeClosed(getMinX(), getMaxX()).mapToObj(x -> new Point(x, first.y)).collect(Collectors.toList());
            }
            List<Integer> xs = IntStream.rangeClosed(getMinX(), getMaxX()).boxed().collect(Collectors.toList());
            return IntStream.rangeClosed(getMinY(), getMaxY()).mapToObj(y -> new Point(xs.remove(isFirstXSmallerThanLastXAndTheSameGoesForYs() ? 0 : xs.size()-1), y)).collect(Collectors.toList());
        }
    }
}
