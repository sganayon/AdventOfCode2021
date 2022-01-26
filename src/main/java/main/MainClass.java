package main;

import lombok.extern.slf4j.Slf4j;
import puzzle.*;

@Slf4j
public class MainClass {
    public static void main(String[] args) {
        Puzzle puzzle = new Day5P1();
        String result = puzzle.solve();
        log.info("Result is : {}", result);
    }
}
