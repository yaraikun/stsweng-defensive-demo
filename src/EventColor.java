import java.util.*;

public enum EventColor {
    RED("red"),
    BLUE("blue"),
    GREEN("green");

    private static final Set<String> ALLOWED_NAMES;

    static {
        Set<String> set = new HashSet<>();

        for (EventColor color : EventColor.values()) {
            set.add(color.value);
        }

        ALLOWED_NAMES = Collections.unmodifiableSet(set);
    }

    private final String value;

    EventColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String color) {
        if (color == null || color.isBlank()) {
            return false;
        }

        return ALLOWED_NAMES.contains(color.trim().toLowerCase(Locale.ROOT));
    }
}
