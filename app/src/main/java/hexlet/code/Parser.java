package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String content, String format) throws IOException {

        Map<String, Object> map;
        YAMLMapper yamlMapper = new YAMLMapper();

        switch (format) {
            case ("json"):
                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(content, new TypeReference<>() {
                });
                break;
            case ("yaml"), ("yml"):
                map = yamlMapper.readValue(content, new TypeReference<>() {
                });
                break;
            default:
                throw new RuntimeException("Unknown format: " + format);
        }

        return map;
    }
}
