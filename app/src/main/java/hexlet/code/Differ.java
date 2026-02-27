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
        Object value;
        Object newValue;

        public DiffEntry(String key, String status, Object oldValue, Object newValue) {
            this.key = key;
            this.status = status;
            this.value = oldValue;
            this.newValue = newValue;
        }

        public String getKey() {
            return key;
        }

        public String getStatus() {
            return status;
        }

        public Object getValue() {
            return value;
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
                    newEntry = new DiffEntry(key, "updated", map1.get(key), map2.get(key));
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

        return formatter.build(sortedEntries);
    }
}
