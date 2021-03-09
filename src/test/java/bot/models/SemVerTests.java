package bot.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by Dark(DarkGuardsman, Robert) on 3/8/2021.
 */
public class SemVerTests
{

    static Stream<Arguments> versionParsingData() {
        return Stream.of(
                Arguments.of("1.0", 1, 0, 0, -1),
                Arguments.of("1.1", 1, 1, 0, -1),
                Arguments.of("1.1.1", 1, 1, 1, -1),
                Arguments.of("1.1.1.1", 1, 1, 1, 1),
                Arguments.of("10.1.1.1", 10, 1, 1, 1),
                Arguments.of("10.10.1.1", 10, 10, 1, 1),
                Arguments.of("10.10.10.1", 10, 10, 10, 1),
                Arguments.of("10.10.10.10", 10, 10, 10, 10),
                Arguments.of("35.1.4", 35, 1, 4, -1),
                Arguments.of("35.1.4.5000", 35, 1, 4, 5000)
        );
    }

    @ParameterizedTest
    @MethodSource("versionParsingData")
    void testVersionParsing(String input, int major, int minor, int patch, int build) {
        //Constructor does the parsing
        final SemVer version = new SemVer(input);

        //Test that we read each value as expected
        Assertions.assertEquals(major, version.major);
        Assertions.assertEquals(minor, version.minor);
        Assertions.assertEquals(patch, version.patch);
        Assertions.assertEquals(build, version.build);
    }

    static Stream<Arguments> versionToStringData() {
        return Stream.of(
                Arguments.of(new SemVer(1, 1, 0), "1.1.0"),
                Arguments.of(new SemVer(1, 1, 1), "1.1.1"),
                Arguments.of(new SemVer(1, 1, 1, 1), "1.1.1.1"),
                Arguments.of(new SemVer(10, 1, 1, 1), "10.1.1.1"),
                Arguments.of(new SemVer(10, 10, 1, 1), "10.10.1.1"),
                Arguments.of(new SemVer(10, 10, 10, 1), "10.10.10.1"),
                Arguments.of(new SemVer(10, 10, 10, 10), "10.10.10.10"),
                Arguments.of(new SemVer(35, 1, 4), "35.1.4"),
                Arguments.of(new SemVer(35, 1, 4, 5000), "35.1.4.5000")
        );
    }

    @ParameterizedTest
    @MethodSource("versionToStringData")
    void testVersionToString(SemVer version, String output) {
        Assertions.assertEquals(version.toString(), output);
    }
}
