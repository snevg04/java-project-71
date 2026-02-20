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
    public void generateTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("file2.json").toString());
        var expected = readFixture("Right.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void missingTest() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(getFixturePath("file1.json").toString(), null);
        });
    }

    @Test
    public void emptyTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("emptyFile.json").toString());
        var expected = readFixture("EmptyRight.txt");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sameFileTest() throws Exception {
        var actual = Differ.generate(getFixturePath("file1.json").toString(),
                getFixturePath("file1.json").toString());
        var expected = readFixture("SameRight.txt");

        Assertions.assertEquals(expected, actual);
    }
}
