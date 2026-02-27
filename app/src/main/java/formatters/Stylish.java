package formatters;

import hexlet.code.Builder;
import hexlet.code.Differ;

import java.util.List;

public class Stylish implements Builder {

    public String build(List<Differ.DiffEntry> entries) {

        var result = new StringBuilder();

        result.append("{\n");

        for (var entry : entries) {
            var status = entry.getStatus();

            switch (status) {
                case "added":
                    buildNormal(result, '+', entry.getKey(), entry.getNewValue());
                    break;
                case "removed":
                    buildNormal(result, '-', entry.getKey(), entry.getValue());
                    break;
                case "unchanged":
                    buildNormal(result, ' ', entry.getKey(), entry.getValue());
                    break;
                case "updated":
                    buildUpdated(result, entry.getKey(), entry.getValue(), entry.getNewValue());
                    break;
                default:
                    break;
            }
        }

        result.append("}");

        return result.toString();
    }

    public static void buildNormal(StringBuilder sb, char status, Object key, Object value) {
        sb.append("  ")
                .append(status)
                .append(" ")
                .append(key)
                .append(": ")
                .append(value)
                .append("\n");
    }


    public static void buildUpdated(StringBuilder sb, String key, Object oldValue, Object newValue) {
        sb.append("  - ")
                .append(key)
                .append(": ")
                .append(oldValue)
                .append("\n")
                .append("  + ")
                .append(key)
                .append(": ")
                .append(newValue)
                .append("\n");
    }

}
