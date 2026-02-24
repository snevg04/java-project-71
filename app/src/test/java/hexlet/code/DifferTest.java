package hexlet.code;

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
                getFixturePath("file2.json").toString());
        var expected = readFixture("Right.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("file2.yaml").toString());
        var expected = readFixture("Right.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateNestedYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file3.yaml").toString(),
                getFixturePath("file4.yaml").toString());
        var expected = readFixture("Right2.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void missingJsonTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(getFixturePath("file1.json").toString(), null);
        });
    }

    @Test
    public void missingYamlTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(getFixturePath("file1.yaml").toString(), null);
        });
    }

    @Test
    public void emptyJsonTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("emptyFile.json").toString());
        var expected = readFixture("EmptyRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void emptyYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("emptyFile.yaml").toString());
        var expected = readFixture("EmptyRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sameJsonTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("file1.json").toString());
        var expected = readFixture("SameRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sameYamlTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.yaml").toString(),
                getFixturePath("file1.yaml").toString());
        var expected = readFixture("SameRight.txt");

        Assertions.assertEquals(expected, actual);
    }
}
