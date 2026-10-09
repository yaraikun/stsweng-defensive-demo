import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public static List<Event> importEvents(String filename)
            throws IOException {

        List<Event> events = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {
            if (line == null || line.trim().isEmpty())
                continue;

            String[] values = line.split(",", -1);

            if (values.length < 3)
                continue;

            String dateStr = values[0].trim();
            String title = values[1].trim();
            String color = values[2].trim();

            if (dateStr.isEmpty() || title.isEmpty() || color.isEmpty())
                continue;

            if (!isValidDate(dateStr, FORMAT))
                continue;

            LocalDate date = LocalDate.parse(dateStr, FORMAT);
            events.add(new Event(date, title, color));
        }

        return events;
    }

    public static boolean isValidDate(String dateStr, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
