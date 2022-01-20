package utility;

import java.util.stream.Stream;

public interface ItemReader {
    public String readLine();
    public Stream<String> readAll();
}
