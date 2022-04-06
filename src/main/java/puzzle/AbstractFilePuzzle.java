package puzzle;

import utility.Chrono;
import utility.FileReader;
import utility.ItemReader;

public abstract class AbstractFilePuzzle implements Puzzle {
    private final Chrono chrono = new Chrono();
    protected final ItemReader reader;

    protected AbstractFilePuzzle(String fileName){
        this.reader = new FileReader("puzzle/"+fileName);
    }

    protected abstract void handleInput(String line);
    protected abstract String generateResult();

    @Override
    public String solve() {
        chrono.start();
        String input = reader.readLine();
        do {
            handleInput(input);
            input = reader.readLine();
        } while (input != null);
        chrono.stop("initialisation");

        chrono.start();
        String result = generateResult();
        chrono.stop("computing");

        return result;
    }
}
