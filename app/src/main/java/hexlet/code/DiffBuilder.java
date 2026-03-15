package hexlet.code;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Comparator;

public class DiffBuilder {

    public static final class DiffEntry {

        private String key;
        private String status;
        private Object value;
        private Object newValue;

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

    public static List<DiffEntry> buildDiff(Map<String, Object> map1, Map<String, Object> map2) {

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

        return entries.stream()
                .sorted(Comparator.comparing(DiffEntry::getKey))
                .toList();
    }

}
