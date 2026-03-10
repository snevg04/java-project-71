package hexlet.code.formatters;

import hexlet.code.Builder;
import hexlet.code.Differ;

import java.util.List;
import java.util.Map;

public final class Plain implements Builder {

    public String build(List<Differ.DiffEntry> entries) {

        var result = new StringBuilder();

        for (var entry : entries) {
            var status = entry.getStatus();

            var key = entry.getKey();
            var oldValue = entry.getValue();
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
                case "unchanged":
                    break;
                case "added":
                    buildAdded(result, key, newValue);
                    break;
                case "removed":
                    buildRemoved(result, key);
                    break;
                case "updated":
                    buildUpdated(result, key, oldValue, newValue);
                    break;
                default:
                    throw new RuntimeException("Unknown status: " + status);
            }
        }

        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    public static void buildAdded(StringBuilder sb, String key, Object value) {
        sb.append("Property " + "'")
                .append(key)
                .append("' was added with value: ")
                .append(value)
                .append("\n");
    }

    public static void buildRemoved(StringBuilder sb, String key) {
        sb.append("Property " + "'")
                .append(key)
                .append("' was removed")
                .append("\n");
    }

    public static void buildUpdated(StringBuilder sb, String key, Object oldValue, Object newValue) {
        sb.append("Property " + "'")
                .append(key)
                .append("' was updated. From ")
                .append(oldValue)
                .append(" to ")
                .append(newValue)
                .append("\n");
    }
}
