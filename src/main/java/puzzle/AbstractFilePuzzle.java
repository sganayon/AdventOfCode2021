package puzzle;

import utility.FileReader;
import utility.ItemReader;

public abstract class AbstractFilePuzzle implements Puzzle {
    protected final ItemReader reader;

    protected AbstractFilePuzzle(String fileName){
        this.reader = new FileReader("puzzle/"+fileName);
    }

    protected abstract void handleInput(String line);
    protected abstract String generateResult();

    @Override
    public String solve() {
        String input = reader.readLine();
        do {
            handleInput(input);
            input = reader.readLine();
        } while (input != null);
        return generateResult();
    }
}
