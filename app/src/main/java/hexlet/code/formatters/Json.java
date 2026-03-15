package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.Builder;
import hexlet.code.DiffBuilder;

import java.util.List;

public final class Json implements Builder {

    public String build(List<DiffBuilder.DiffEntry> entries) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(entries);
    }
}
