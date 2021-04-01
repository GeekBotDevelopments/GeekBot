package bot.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by Dark(DarkGuardsman, Robert) on 3/8/2021.
 */
class SemVerTests
{

    static Stream<Arguments> versionParsingData() {
        return Stream.of(
                Arguments.of("1.0", 1, 0, -1, -1),
                Arguments.of("1.1", 1, 1, -1, -1),
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
        Assertions.assertEquals(major, version.getMajor());
        Assertions.assertEquals(minor, version.getMinor());
        Assertions.assertEquals(patch, version.getPatch());
        Assertions.assertEquals(build, version.getBuild());
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

    static Stream<Arguments> versionEqualsData() {
        return Stream.of(
                //Not equals
                Arguments.of(new SemVer(1, 1, 0), new SemVer(1, 1, 1), false),
                Arguments.of(new SemVer(1, 1, 0), new SemVer(1, 0, 0), false),
                Arguments.of(new SemVer(1, 1, 0), new SemVer(2, 1, 0), false),
                Arguments.of(new SemVer(1, 1, 0, 0), new SemVer(1, 1, 0, 1), false),
                //Equals
                Arguments.of(new SemVer(1, 1, 0), new SemVer(1, 1, 0), true),
                Arguments.of(new SemVer(1, 1, 0, 1), new SemVer(1, 1, 0, 1), true)
        );
    }

    @ParameterizedTest
    @MethodSource("versionEqualsData")
    void testVersionEquals(SemVer a, SemVer b, boolean shouldMatch) {
        Assertions.assertEquals(shouldMatch, a.equals(b));
    }

    static Stream<Arguments> versionCompareData() {
        return Stream.of(
                //All equal
                Arguments.of(new SemVer(1, 2, 3, 4), new SemVer(1, 2, 3, 4), 0),

                //Build Delta
                Arguments.of(new SemVer(1, 2, 3, 5), new SemVer(1, 2, 3, 6), -1),
                Arguments.of(new SemVer(1, 2, 3, 5), new SemVer(1, 2, 3, 4), 1),

                //Patch Delta
                Arguments.of(new SemVer(1, 2, 3, 5), new SemVer(1, 2, 4, 5), -1),
                Arguments.of(new SemVer(1, 7, 3, 5), new SemVer(1, 7, 2, 5), 1),

                //Minor
                Arguments.of(new SemVer(1, 2, 3, 5), new SemVer(1, 3, 3, 5), -1),
                Arguments.of(new SemVer(7, 2, 3, 5), new SemVer(7, 1, 3, 5), 1),

                //Major
                Arguments.of(new SemVer(0, 2, 3, 5), new SemVer(1, 2, 3, 5), -1),
                Arguments.of(new SemVer(1, 2, 3, 5), new SemVer(0, 2, 3, 5), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("versionCompareData")
    void testVersionCompare(SemVer a, SemVer b, int result) {
        Assertions.assertEquals(result, a.compareTo(b));
    }
}
