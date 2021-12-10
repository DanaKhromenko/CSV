package manager;

import java.io.IOException;
import java.util.List;

public interface FileManager {
    List<String> getListOfAllRowsFromFile(String filePath, boolean skipTheHeader) throws IOException;
}
