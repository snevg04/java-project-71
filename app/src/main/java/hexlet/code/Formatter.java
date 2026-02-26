package hexlet.code;

import formatters.Plain;
import formatters.Stylish;

public class Formatter {
    public static Builder addNew(String format) {

        if (format == null) {
            format = "stylish";
        }

        switch (format) {
            case "stylish":
                return new Stylish();
            case "plain":
                return new Plain();
            default:
                throw new IllegalArgumentException("wrong format");

        }
    }
}
