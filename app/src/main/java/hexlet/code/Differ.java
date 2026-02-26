package hexlet.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Differ {

    public static class DiffEntry {

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

    public static String generate(String filePath1, String filePath2, Builder formatter) throws IOException {

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
                newEntry = new DiffEntry(key, "added", null, map2.get(key));
            }

            entries.add(newEntry);
        }

        var sortedEntries = entries.stream()
                .sorted(Comparator.comparing(DiffEntry::getKey))
                .toList();

        var result = new StringBuilder();

        if (formatter instanceof Stylish) {
            result.append("{\n");
        }

        for (var entry : sortedEntries) {
            result.append(formatter.build(entry));
        }

        if (formatter instanceof Stylish) {
            result.append("}");
        }

        if (formatter instanceof Plain && result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
