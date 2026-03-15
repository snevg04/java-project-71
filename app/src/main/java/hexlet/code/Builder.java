package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Builder {

    String build(List<DiffBuilder.DiffEntry> entries) throws JsonProcessingException;

}
