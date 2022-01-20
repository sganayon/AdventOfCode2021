package puzzle;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4P2 extends AbstractFilePuzzle{
    private List<Integer> numberDrawn;
    private final List<Board> boards = new ArrayList<>();
    private final List<String> boardLines = new ArrayList<>();
    boolean firstLine = true;

    public Day4P2() {
        super("Day4");
    }

    @Override
    protected void handleInput(String line) {
        if(firstLine){
            numberDrawn = parseNumbersInline(line, ",");
            firstLine = false;
        } else {
            if(line.isEmpty()){
                generateBoard();
            } else {
                boardLines.add(line);
            }
        }
    }

    private void generateBoard(){
        if(!boardLines.isEmpty()){
            Board board = new Board();
            boardLines.stream()
                    .map(line -> parseNumbersInline(line, " "))
                    .forEach(board::addRow);
            board.buildColumns();
            boards.add(board);
            boardLines.clear();
        }
    }

    private List<Integer> parseNumbersInline(String line, String delimiter){
        return Stream.of(line.split(delimiter))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    protected String generateResult() {
        for (int number : numberDrawn) {
            for (Board board : boards) {
                board.markNumber(number);
            }

            List<Board> winningBoards = boards.stream().filter(Board::checkVictory).collect(Collectors.toList());
            boards.removeAll(winningBoards);

            if(boards.isEmpty()){
                if(winningBoards.size() > 1){
                    return "several winners";
                } else {
                    int sum = winningBoards.get(0).sumLeftNumber();
                    return String.valueOf(sum * number);
                }
            }
        }
        return "No winner";
    }

    @NoArgsConstructor
    private static final class Board {
        private final List<List<Integer>> rows = new ArrayList<>();
        private final List<List<Integer>> columns = new ArrayList<>();

        public void addRow(List<Integer> row){
            rows.add(row);
        }

        public void buildColumns(){
            for(int i=0; i<rows.get(0).size(); i++){
                int finalI = i;
                columns.add(rows.stream().map(row -> row.get(finalI)).collect(Collectors.toList()));
            }
        }

        public void markNumber(Integer number){
            rows.forEach(row -> row.remove(number));
            columns.forEach(row -> row.remove(number));
        }

        public boolean checkVictory(){
            return rows.stream().anyMatch(List::isEmpty) || columns.stream().anyMatch(List::isEmpty);
        }

        public int sumLeftNumber(){
            return rows.stream().flatMap(List::stream).mapToInt(Integer::intValue).sum();
        }
    }
}
