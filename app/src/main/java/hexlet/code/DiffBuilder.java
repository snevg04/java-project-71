package hexlet.code;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Comparator;

public class DiffBuilder {

    public static List<Differ.DiffEntry> buildDiff(Map<String, Object> map1, Map<String, Object> map2) {

        var allKeys = new HashSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        var entries = new ArrayList<Differ.DiffEntry>();

        for (var key : allKeys) {

            Differ.DiffEntry newEntry;

            if (map1.containsKey(key) && map2.containsKey(key)) {

                var change = Objects.equals(map1.get(key), map2.get(key));

                if (change) {
                    newEntry = new Differ.DiffEntry(key, "unchanged", map1.get(key), null);
                } else {
                    newEntry = new Differ.DiffEntry(key, "updated", map1.get(key), map2.get(key));
                }

            } else if (map1.containsKey(key) && !map2.containsKey(key)) {
                newEntry = new Differ.DiffEntry(key, "removed", map1.get(key), null);
            } else {
                newEntry = new Differ.DiffEntry(key, "added", null, map2.get(key));
            }

            entries.add(newEntry);
        }

        return entries.stream()
                .sorted(Comparator.comparing(Differ.DiffEntry::getKey))
                .toList();
    }
}
