package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String filePath) throws IOException {

        if (filePath == null) {
            throw new IllegalArgumentException("Illegal argument");
        }

        Path file1 = Paths.get(filePath);

        var contentFile = Files.readString(file1).trim();

        if (contentFile.isEmpty()) {
            contentFile = "{}";
        }

        Map<String, Object> map = null;

        switch (defineFormat(filePath)) {
            case("json"):
                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(contentFile, new TypeReference<>() {
                });
                break;
            case("yaml"):
                ObjectMapper yamlObjectMapper = new YAMLMapper();
                map = yamlObjectMapper.readValue(contentFile, new TypeReference<>() {
                });
                break;
            default:
                throw new IllegalArgumentException("Illegal format");
        }

        return map;
    }

    public static String defineFormat(String path) {
        var pathSplit = path.split("/");
        var lastElement = pathSplit[pathSplit.length - 1];
        return lastElement.substring(lastElement.lastIndexOf(".") + 1);
    }
}
