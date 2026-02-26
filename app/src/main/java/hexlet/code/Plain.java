package hexlet.code;

import java.util.List;
import java.util.Map;

public class Plain implements Builder {

    public String build(Differ.DiffEntry entry) {

        var result = new StringBuilder();

        var status = entry.getStatus();

        var key = entry.getKey();
        var oldValue = entry.getOldValue();
        var newValue = entry.getNewValue();

        if (oldValue == null) {
            oldValue = "null";
        } else if (oldValue instanceof Map<?, ?> || oldValue instanceof List<?>) {
            oldValue = "[complex value]";
        } else if (oldValue instanceof String) {
            oldValue = "'" + oldValue + "'";
        }

        if (newValue == null) {
            newValue = "null";
        } else if (newValue instanceof Map<?, ?> || newValue instanceof List<?>) {
            newValue = "[complex value]";
        } else if (newValue instanceof String) {
            newValue = "'" + newValue + "'";
        }

        switch (status) {
            case "added":
                result.append("Property " + "'")
                        .append(key)
                        .append("' was added with value: ")
                        .append(newValue)
                        .append("\n");
                break;
            case "removed":
                result.append("Property " + "'")
                        .append(key)
                        .append("' was removed")
                        .append("\n");
                break;
            case "changed":
                result.append("Property " + "'")
                        .append(key)
                        .append("' was updated. From ")
                        .append(oldValue)
                        .append(" to ")
                        .append(newValue)
                        .append("\n");
                break;
            default:
                break;
        }

        return result.toString();
    }
}
