package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

public class Formatter {
    public static Builder addNew(String format) {

        if (format == null) {
            format = "stylish";
        }

        switch (format.toLowerCase()) {
            case "stylish":
                return new Stylish();
            case "plain":
                return new Plain();
            case "json":
                return new Json();
            default:
                throw new RuntimeException("Unknown format: " + format);

        }
    }
}
