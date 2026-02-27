package hexlet.code;

import formatters.Json;
import formatters.Plain;
import formatters.Stylish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DifferTest {

    public static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    public static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }

    @Test
    public void generateJsonTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("file2.json").toString(), new Stylish());
        var expected = readFixture("Right.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("file2.yaml").toString(), new Stylish());
        var expected = readFixture("Right.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateNestedYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file3.yaml").toString(),
                getFixturePath("file4.yaml").toString(), new Stylish());
        var expected = readFixture("Right2.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generatePlainFormatTest() throws Exception {
        var actual = Differ.generate(getFixturePath("plain1.json").toString(),
                getFixturePath("plain2.json").toString(), new Plain());
        var expected = readFixture("Right3.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateJsonFormatTest() throws Exception {
        var actual = Differ.generate(getFixturePath("json1.json").toString(),
                getFixturePath("json2.json").toString(), new Json());
        var expected = readFixture("Right4.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void missingJsonTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(getFixturePath("file1.json").toString(), null, new Stylish());
        });
    }

    @Test
    public void missingYamlTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(getFixturePath("file1.yaml").toString(), null, new Stylish());
        });
    }

    @Test
    public void emptyJsonTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("emptyFile.json").toString(), new Stylish());
        var expected = readFixture("EmptyRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void emptyYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("emptyFile.yaml").toString(), new Stylish());
        var expected = readFixture("EmptyRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sameJsonTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("file1.json").toString(), new Stylish());
        var expected = readFixture("SameRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sameYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("file1.yaml").toString(), new Stylish());
        var expected = readFixture("SameRight.txt");

        Assertions.assertEquals(expected, actual);
    }
}
