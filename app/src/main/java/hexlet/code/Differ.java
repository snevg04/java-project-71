package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

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

    public static String generate(String filePath1, String filePath2, String format) throws IOException {

        var content1 = getContent(filePath1);
        var content2 = getContent(filePath2);
        var format1 = defineFormat(filePath1);
        var format2 = defineFormat(filePath2);

        Map<String, Object> map1 = Parser.parse(content1, format1);
        Map<String, Object> map2 = Parser.parse(content2, format2);

        var entries = DiffBuilder.buildDiff(map1, map2);

        var formatter = Formatter.addNew(format);

        return formatter.build(entries);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String getContent(String filePath) throws IOException {

        if (filePath == null) {
            throw new IllegalArgumentException("Illegal argument");
        }

        Path file = Paths.get(filePath);

        var content = Files.readString(file).trim();

        if (content.isEmpty()) {
            content = "{}";
        }

        return content;
    }

    public static String defineFormat(String path) {
        Path filePath = Paths.get(path);
        String fileName = filePath.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
