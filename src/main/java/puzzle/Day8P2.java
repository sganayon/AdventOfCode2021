package puzzle;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8P2 extends AbstractFilePuzzle {
    private static final Map<Integer, List<Character>> PATTERN = generatePattern();
    private static final Map<Integer, List<Integer>> DIGIT_BY_LENGHT = groupPatternByLength();

    private List<Record> outputValues = new ArrayList<>();

    public Day8P2() {
        super("Day8");
    }

    @Override
    protected void handleInput(String line) {
        List<List<Character>> signalPattern = Arrays.stream(line.substring(0, line.indexOf("|") -1).split(" "))
                .map(Day8P2::turnToChars)
                .collect(Collectors.toList());
        List<List<Character>> output = Arrays.stream(line.substring(line.indexOf("|") + 2).split(" "))
                .map(Day8P2::turnToChars)
                .collect(Collectors.toList());
        outputValues.add(new Record(signalPattern, output));
    }

    @Override
    protected String generateResult() {
        return String.valueOf(outputValues.stream().mapToInt(Record::solve).sum());
    }

    private static Map<Integer, List<Character>> generatePattern(){
        Map<Integer, List<Character>> map = new HashMap<>();
        map.put(0, Stream.of('a', 'b', 'c', 'e', 'f', 'g').collect(Collectors.toList()));
        map.put(1, Stream.of('c', 'f').collect(Collectors.toList()));
        map.put(2, Stream.of('a', 'c', 'd', 'e', 'g').collect(Collectors.toList()));
        map.put(3, Stream.of('a', 'c', 'd', 'f', 'g').collect(Collectors.toList()));
        map.put(4, Stream.of('b', 'c', 'd', 'f').collect(Collectors.toList()));
        map.put(5, Stream.of('a', 'b', 'd', 'f', 'g').collect(Collectors.toList()));
        map.put(6, Stream.of('a', 'b', 'd', 'e', 'f', 'g').collect(Collectors.toList()));
        map.put(7, Stream.of('a', 'c', 'f').collect(Collectors.toList()));
        map.put(8, Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g').collect(Collectors.toList()));
        map.put(9, Stream.of('a', 'b', 'c', 'd', 'f', 'g').collect(Collectors.toList()));
        return Collections.unmodifiableMap(map);
    }

    public static Map<Integer, List<Integer>> groupPatternByLength() {
        return PATTERN.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getValue().size(),
                        e -> new ArrayList<>(e.getKey()),
                        (l1, l2) -> { l1.addAll(l2); return l1;})
                );
    }

    private static List<Character> turnToChars(String s){
        return s.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }

    @Data
    @Slf4j
    private static final class Record {
        private final Map<Character, Character> segmentDecoder = new HashMap<>();
        private final Map<List<Character>, Integer> digitDecoder = new HashMap<>();
        private final Map<Integer, List<Character>> digitDecoderReversed = new HashMap<>();

        private final List<List<Character>> signalPattern;
        private final List<List<Character>> output;


        // Ici match 1, 4, 7, 8
        private void match1478() {
            signalPattern.stream()
                    .filter(u -> u.size() == 2)
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 1 : {}", u);
                        digitDecoder.put(u, 1);
                        digitDecoderReversed.put(1, u);
                    });

            signalPattern.stream()
                    .filter(u -> u.size() == 4)
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 4 : {}", u);
                        digitDecoder.put(u, 4);
                        digitDecoderReversed.put(4, u);
                    });

            signalPattern.stream()
                    .filter(u -> u.size() == 3)
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 7 : {}", u);
                        digitDecoder.put(u, 7);
                        digitDecoderReversed.put(7, u);
                    });

            signalPattern.stream()
                    .filter(u -> u.size() == 7)
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 8 : {}", u);
                        digitDecoder.put(u, 8);
                        digitDecoderReversed.put(8, u);
                    });
        }

        private void match3(){
            signalPattern.stream()
                    .filter(u -> u.size() == 5 && u.containsAll(digitDecoderReversed.get(1)))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 3 : {}", u);
                        digitDecoder.put(u, 3);
                        digitDecoderReversed.put(3, u);
                    });
        }

        private void match9(){
            signalPattern.stream()
                    .filter(u -> u.size() == 6 && u.containsAll(digitDecoderReversed.get(3)))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 9 : {}", u);
                        digitDecoder.put(u, 9);
                        digitDecoderReversed.put(9, u);
                    });
        }

        private void match50(){
            List<Character> four = new ArrayList<>(digitDecoderReversed.get(4));
            four.removeAll(digitDecoderReversed.get(3));
            Character b = four.get(0);
            log.debug("4 - 3 : {} - {} = {}", digitDecoderReversed.get(4), digitDecoderReversed.get(3), b);
            signalPattern.stream()
                    .filter(u -> u.size() == 5 && u.contains(b))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 5 : {}", u);
                        digitDecoder.put(u, 5);
                        digitDecoderReversed.put(5, u);
                    });

            four = new ArrayList<>(digitDecoderReversed.get(4));
            four.removeAll(digitDecoderReversed.get(1));
            four.remove(b);
            Character d = four.get(0);
            log.debug("4 - 1 - d : {} - {} - {} = {}", digitDecoderReversed.get(4), digitDecoderReversed.get(3), b, d);
            signalPattern.stream()
                    .filter(u -> u.size() == 6 && !u.contains(d))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 0 : {}", u);
                        digitDecoder.put(u, 0);
                        digitDecoderReversed.put(0, u);
                    });
        }

        private void match26(){
            signalPattern.stream()
                    .filter(u -> u.size() == 5 && digitDecoderReversed.values().stream().filter(v -> v.size() == 5).noneMatch(l1 -> hasAllChars(l1, u)))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 2 : {}", u);
                        digitDecoder.put(u, 2);
                        digitDecoderReversed.put(2, u);
                    });

            signalPattern.stream()
                    .filter(u -> u.size() == 6 && digitDecoderReversed.values().stream().filter(v -> v.size() == 6).noneMatch(l1 -> hasAllChars(l1, u)))
                    .findFirst()
                    .ifPresent(u ->{
                        log.debug("Digit 6 : {}", u);
                        digitDecoder.put(u, 6);
                        digitDecoderReversed.put(6, u);
                    });
        }

        private void generateMapping(){
            match1478();
            match3();
            match9();
            match50();
            match26();
        }

        public int solve(){
            generateMapping();
            int result = mapOutput(output.get(0)) * 1000 + mapOutput(output.get(1)) * 100 + mapOutput(output.get(2)) * 10 + mapOutput(output.get(3));
            log.debug("result of {} = {}", output.stream().map(l -> l.stream().map(Object::toString).collect(Collectors.joining())).toArray(), result);
            return result;
        }

        private int mapOutput(List<Character> digit){
            return digitDecoderReversed.entrySet()
                    .stream()
                    .filter(e -> e.getValue().size() == digit.size() && hasAllChars(e.getValue(), digit))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get();
        }

        private boolean hasAllChars(List<Character> l1, List<Character> l2){
            return l1.containsAll(l2);
        }

    }
}
