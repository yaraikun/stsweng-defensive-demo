import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public final class EventImporter {
    private EventImporter() {};

    public static List<Event> importEvents(String filename) throws IOException {
        if (filename == null || filename.isBlank())
            throw new IllegalArgumentException("Filename cannot be null or blank");

        Path path = Path.of(filename);

        if (!Files.exists(path))
            throw new FileNotFoundException("File does not exist: " + filename);
        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("Path is not a regular file: " + filename);
        if (!Files.isReadable(path))
            throw new IOException("File is not readable: " + filename);

        List<Event> events = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1 && line.startsWith("\uFEFF"))
                    line = line.substring(1);
                if (line.trim().isEmpty())
                    continue;

                try {
                    Event event = EventParser.parse(line, lineNumber);
                    events.add(event);
                } catch (InvalidEventException e) {
                    System.err.println("[SKIPPED]" + e.getMessage());
                }
            }
        }

        return Collections.unmodifiableList(events);
    }

    public static boolean isValidDate(String dateStr, DateTimeFormatter formatter) {
        if (dateStr == null || formatter == null)
            return false;

        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}


