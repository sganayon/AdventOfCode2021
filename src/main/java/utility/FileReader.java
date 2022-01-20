package utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class FileReader implements ItemReader{
    private Path path;
    private BufferedReader reader;

    public FileReader(String fileName) {
        try {
            path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(fileName)).toURI());
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de la création du reader : "+e.getLocalizedMessage());
        }
    }

    @Override
    public Stream<String> readAll() {
        try {
            return Files.lines(path);
        } catch (Exception e){
            log.error("Erreur de lecture du fichier (stream) {}, cause {}", path.toString(), e.getLocalizedMessage());
            return Stream.empty();
        }
    }

    @Override
    public String readLine() {
        initReader();
        try {
            return reader.readLine();
        } catch (Exception e){
            log.error("Erreur de lecture du fichier {}, cause {}", path.toString(), e.getLocalizedMessage());
            return null;
        }
    }

    private void initReader() {
        if(reader == null) {
            try {
                reader = Files.newBufferedReader(path);
            } catch (Exception e) {
                throw new IllegalStateException("Impossible de créer le reader, cause : "+e.getLocalizedMessage());
            }
        }
    }
}
