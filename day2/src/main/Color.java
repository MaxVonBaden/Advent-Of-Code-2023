package main;

public enum Color {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    public static final String REGEX_PATTERN;
    private final String name;

    static {
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < Color.values().length; i++) {
            result.append(Color.values()[i].name);
            if (i != Color.values().length - 1) {
                result.append("|");
            }
        }
        REGEX_PATTERN = result.toString();
    }

    Color (String name) {
        this.name = name;
    }

    public static Color byName(String name) {
        for (Color color : Color.values()) {
            if (color.name.equals(name)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Input has to be a color name.");
    }
}
