package main;

import lombok.extern.slf4j.Slf4j;
import puzzle.Day3P2;
import puzzle.Day4P1;
import puzzle.Day4P2;
import puzzle.Puzzle;

@Slf4j
public class MainClass {
    public static void main(String[] args) {
        Puzzle puzzle = new Day4P2();
        String result = puzzle.solve();
        log.info("Result is : {}", result);
    }
}
