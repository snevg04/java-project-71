package hexlet.code;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stylish implements Builder {

    public String build(Differ.DiffEntry entry) {

        var result = new StringBuilder();

        var status = entry.getStatus();

        switch (status) {
            case "added":
                buildNormal(result, '+', entry.getKey(), entry.getOldValue());
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

    public static void buildNormal(StringBuilder sb, char status, Object key, Object oldValue) {
        sb.append("  ")
                .append(status)
                .append(" ")
                .append(key)
                .append(": ")
                .append(oldValue)
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


//Object oldValue = null;
//        if ((entry.getOldValue()) instanceof String) {
//            oldValue = entry.getOldValue();
//        }