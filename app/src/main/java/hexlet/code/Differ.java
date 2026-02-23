package hexlet.code;

import java.io.IOException;
import java.util.*;

public class Differ {

    private static class DiffEntry {

        String key;
        String status;
        Object oldValue;
        Object newValue;

        DiffEntry(String key, String status, Object oldValue, Object newValue) {
            this.key = key;
            this.status = status;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public String getKey() {
            return key;
        }

        public String getStatus() {
            return status;
        }

        public Object getOldValue() {
            return oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }
    }

    public static String generate(String filePath1, String filePath2) throws IOException {

        Map<String, Object> map1 = Parser.parse(filePath1);
        Map<String, Object> map2 = Parser.parse(filePath2);

        var allKeys = new HashSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        var entries = new ArrayList<DiffEntry>();

        for (var key : allKeys) {

            DiffEntry newEntry;

            if (map1.containsKey(key) && map2.containsKey(key)) {

                var change = Objects.equals(map1.get(key), map2.get(key));

                if (change) {
                    newEntry = new DiffEntry(key, "unchanged", map1.get(key), null);
                } else {
                    newEntry = new DiffEntry(key, "changed", map1.get(key), map2.get(key));
                }

            } else if (map1.containsKey(key) && !map2.containsKey(key)) {
                newEntry = new DiffEntry(key, "removed", map1.get(key), null);
            } else {
                newEntry = new DiffEntry(key, "added", map2.get(key), null);
            }

            entries.add(newEntry);
        }

        var sortedEntries = entries.stream()
                .sorted(Comparator.comparing(DiffEntry::getKey))
                .toList();

        var result = new StringBuilder();

        result.append("{\n");

        for (var entry : sortedEntries) {

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
        }

        result.append("}");

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
