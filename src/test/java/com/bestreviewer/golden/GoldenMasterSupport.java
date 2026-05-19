package com.bestreviewer.golden;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Golden Master 기준 파일 로드·비교·갱신.
 * <p>
 * 갱신: {@code mvn test -Dtest=TVControllerGoldenMasterTest -Dgolden.master.update=true}
 */
public final class GoldenMasterSupport {

    public static final String UPDATE_PROPERTY = "golden.master.update";
    public static final String RESOURCE_PATH = "/golden_master/golden_master_expected.txt";
    public static final Path REPO_RELATIVE_PATH =
            Paths.get("src", "test", "resources", "golden_master", "golden_master_expected.txt");

    private GoldenMasterSupport() {
    }

    public static boolean isUpdateMode() {
        return Boolean.parseBoolean(System.getProperty(UPDATE_PROPERTY, "false"));
    }

    public static String loadExpectedFromClasspath() throws IOException {
        try (InputStream in = GoldenMasterSupport.class.getResourceAsStream(RESOURCE_PATH)) {
            if (in == null) {
                throw new IOException("Classpath resource not found: " + RESOURCE_PATH);
            }
            return normalizeNewlines(new String(in.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    public static String loadExpectedFromRepo() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir")).resolve(REPO_RELATIVE_PATH);
        if (!Files.exists(path)) {
            throw new IOException("Golden master file not found: " + path.toAbsolutePath());
        }
        return normalizeNewlines(Files.readString(path, StandardCharsets.UTF_8));
    }

    public static void writeExpectedToRepo(String content) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir")).resolve(REPO_RELATIVE_PATH);
        Files.createDirectories(path.getParent());
        Files.writeString(path, normalizeNewlines(content), StandardCharsets.UTF_8);
    }

    public static void assertMatchesGolden(String actual, String expected) {
        String normalizedActual = normalizeNewlines(actual);
        String normalizedExpected = normalizeNewlines(expected);
        if (normalizedActual.equals(normalizedExpected)) {
            return;
        }
        int line = firstDifferingLine(normalizedActual, normalizedExpected);
        String actualLine = lineAt(normalizedActual, line);
        String expectedLine = lineAt(normalizedExpected, line);
        throw new AssertionError(
                "Golden Master mismatch at line " + line + System.lineSeparator()
                        + "  expected: " + expectedLine + System.lineSeparator()
                        + "  actual  : " + actualLine + System.lineSeparator()
                        + "Update baseline: mvn test -Dtest=TVControllerGoldenMasterTest "
                        + "-D" + UPDATE_PROPERTY + "=true");
    }

    public static String normalizeNewlines(String text) {
        return text.replace("\r\n", "\n").replace('\r', '\n');
    }

    private static int firstDifferingLine(String actual, String expected) {
        String[] actualLines = actual.split("\n", -1);
        String[] expectedLines = expected.split("\n", -1);
        int max = Math.max(actualLines.length, expectedLines.length);
        for (int i = 0; i < max; i++) {
            String a = i < actualLines.length ? actualLines[i] : "<EOF>";
            String e = i < expectedLines.length ? expectedLines[i] : "<EOF>";
            if (!a.equals(e)) {
                return i + 1;
            }
        }
        return max + 1;
    }

    private static String lineAt(String text, int oneBasedLine) {
        String[] lines = text.split("\n", -1);
        if (oneBasedLine < 1 || oneBasedLine > lines.length) {
            return "<EOF>";
        }
        return lines[oneBasedLine - 1];
    }
}
