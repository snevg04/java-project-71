package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.concurrent.Callable;
import java.util.Map;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import hexlet.code.Differ;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

class App implements Callable<Integer> {

    @Parameters(index = "0", paramLabel = "filePath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filePath1", description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format;

    @Override
    public Integer call() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map1
                = objectMapper.readValue(new File(filePath1), new TypeReference<Map<String,Object>>(){});

        Map<String, Object> map2
                = objectMapper.readValue(new File(filePath2), new TypeReference<Map<String, Object>>(){});

        System.out.println("json1: " + map1);
        System.out.println("json2: " + map2);
        System.out.println("format: " + format);

        var diff = Differ.generate(filePath1, filePath2);
        System.out.println(diff);

        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
