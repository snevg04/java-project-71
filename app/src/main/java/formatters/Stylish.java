package formatters;

import hexlet.code.Builder;
import hexlet.code.Differ;

public class Stylish implements Builder {

    public String build(Differ.DiffEntry entry) {

        var result = new StringBuilder();

        var status = entry.getStatus();

        switch (status) {
            case "added":
                buildNormal(result, '+', entry.getKey(), entry.getNewValue());
                break;
            case "removed":
                buildNormal(result, '-', entry.getKey(), entry.getOldValue());
                break;
            case "unchanged":
                buildNormal(result, ' ', entry.getKey(), entry.getOldValue());
                break;
            case "changed":
                buildChanged(result, entry.getKey(), entry.getOldValue(), entry.getNewValue());
                break;
            default:
                break;
        }

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


    public static void buildChanged(StringBuilder sb, String key, Object oldValue, Object newValue) {
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
