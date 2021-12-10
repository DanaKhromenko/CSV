package manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileManagerCsvImpl implements FileManager {
    @Override
    public List<String> getListOfAllRowsFromFile(String csvFilePath, boolean skipTheHeader) throws IOException {
        if (csvFilePath == null) {
            throw new IOException("Null path was achieved!");
        }
        return Files.readAllLines(Path.of(csvFilePath)).stream()
                .skip(skipTheHeader ? 1 : 0)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
