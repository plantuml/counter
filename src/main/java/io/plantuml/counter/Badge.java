package io.plantuml.counter;

public class Badge {

    public static final String COLOR_IMPORTANT = "important";
    public static final String COLOR_INFORMAL = "informal";

    private final String label;
    private final String message;
    private final String color;


    public Badge(String label, String message, String color) {
        this.label = label;
        this.message = message;
        this.color = color;
    }

    public String toJSonString() {

        final String objectToReturn = "{ \"schemaVersion\": 1, " //
                + "\"label\": \"" + label + "\", " //
                + "\"message\": \"" + message + "\", " //
                + "\"color\": \"" + color + "\" }";
        return objectToReturn;
    }
}
