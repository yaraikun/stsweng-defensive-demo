import java.time.*;
import java.time.format.*;
import java.util.*;

public final class EventParser {
    private enum Column {
        DATE, TITLE, COLOR;
        public static final int COUNT = values().length;
    }

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private EventParser() {}

    public static Event parse(String line, int lineNumber) throws InvalidEventException {
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidEventException(lineNumber, "Line is empty");
        }

        String[] values = splitCsvLine(line);
        
        if (values.length != Column.COUNT) {
            throw new InvalidEventException(lineNumber,
                "Expected exactly 3 columns, but found " + values.length + 
                ". (Note: Fields containing commas must be enclosed in double quotes.)");
        }

        for (Column col : Column.values()) {
            if (values[col.ordinal()].trim().isEmpty()) {
                throw new InvalidEventException(lineNumber, 
                        "Missing " + col.name().toLowerCase(Locale.ROOT));
            }
        }

        String dateStr = values[Column.DATE.ordinal()].trim();
        String titleStr = values[Column.TITLE.ordinal()].trim();
        String rawColor = values[Column.COLOR.ordinal()].trim();

        if (!EventColor.isValid(rawColor)) {
            throw new InvalidEventException(lineNumber,
                "Invalid color '" + rawColor + "'. Allowed: red, blue, green");
        }

        LocalDate date;

        try {
            date = LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidEventException(lineNumber,
                    "Invalid date or format '" + dateStr + "' (expected MM/dd/yyyy)");
        }

        String normalizedColor = rawColor.toLowerCase(Locale.ROOT);
        return new Event(date, titleStr, normalizedColor);
    }

    private static String[] splitCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }
}
