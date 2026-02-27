package formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.Builder;
import hexlet.code.Differ;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Json implements Builder {

    public String build(List<Differ.DiffEntry> entries) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(getJsonList(entries));
    }

    private static List<Differ.DiffEntry> getJsonList(List<Differ.DiffEntry> entries) {

        List<Differ.DiffEntry> jsonEntries = new ArrayList<>();

        for (var entry : entries) {
            Differ.DiffEntry jsonEntry;

            if ("updated".equals(entry.getStatus())) {
                jsonEntry = new Differ.DiffEntry(entry.getKey(), entry.getStatus(),
                        entry.getValue(), entry.getNewValue());
            } else {
                jsonEntry = new Differ.DiffEntry(entry.getKey(), entry.getStatus(),
                        entry.getValue(), null);
            }

            jsonEntries.add(jsonEntry);
        }

        return jsonEntries.stream()
                .sorted(Comparator.comparing(Differ.DiffEntry::getKey))
                .toList();
    }
}
