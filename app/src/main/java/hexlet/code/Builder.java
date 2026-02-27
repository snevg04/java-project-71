package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Builder {

    String build(List<Differ.DiffEntry> entries) throws JsonProcessingException;

}
