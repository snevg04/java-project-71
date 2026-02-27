package hexlet.code;

import formatters.Json;
import formatters.Plain;
import formatters.Stylish;

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
                throw new IllegalArgumentException("wrong format");

        }
    }
}
