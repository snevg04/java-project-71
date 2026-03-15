package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Differ {



    public static String generate(String filePath1, String filePath2, String format) throws IOException {

        var content1 = getContent(filePath1);
        var content2 = getContent(filePath2);
        var format1 = defineFormat(filePath1);
        var format2 = defineFormat(filePath2);

        Map<String, Object> map1 = Parser.parse(content1, format1);
        Map<String, Object> map2 = Parser.parse(content2, format2);

        List<DiffBuilder.DiffEntry> entries = DiffBuilder.buildDiff(map1, map2);

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
