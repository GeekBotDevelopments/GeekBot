package bot.modules.minecraft.forge;

import bot.models.SemVer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by Robin Seifert on 3/12/2021.
 */
public class ForgeVersionTests
{
    static Stream<Arguments> versionParsingData() {
        return Stream.of(
                Arguments.of("1.1-latest", "1.3.2.6", new SemVer(1, 1, 0, -1), new SemVer(1, 3, 2, 6), true),
                Arguments.of("1.1-recommended", "1.3.2.6", new SemVer(1, 1, 0, -1), new SemVer(1, 3, 2, 6), false),
                Arguments.of("1.1.1-latest", "1.3.2.6", new SemVer(1, 1, 1, -1), new SemVer(1, 3, 2, 6), true)
        );
    }

    @ParameterizedTest
    @MethodSource("versionParsingData")
    void testVersionParsing(
                            //Expected inputs
                            String mcString, String forgeString,
                            //Expected outputs
                            SemVer minecraftVersion, SemVer forgeVersion, boolean latest) {

        //Build object
        final ForgeVersion version = new ForgeVersion(mcString, forgeString);

        //Validate object
        Assertions.assertEquals(minecraftVersion, version.getMinecraft(), "Minecraft version didn't parse as expected");
        Assertions.assertEquals(forgeVersion, version.getForge(), "Forge version didn't parse as expected");
        Assertions.assertEquals(latest, version.isLatest());
    }

    static Stream<Arguments> versionToStringData() {
        return Stream.of(
                Arguments.of(new ForgeVersion("1.1-recommended", "1.3.2.6"), "1.1.0-1.3.2.6R"),
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.6"), "1.1.0-1.3.2.6L")
        );
    }

    @ParameterizedTest
    @MethodSource("versionToStringData")
    void testVersionParsing(ForgeVersion version, String output) {
        Assertions.assertEquals(version.toString(), output);
    }

    static Stream<Arguments> versionCompareData() {
        return Stream.of(
                //Full equal
                Arguments.of(new ForgeVersion("1.1-recommended", "1.3.2.6"), new ForgeVersion("1.1-recommended", "1.3.2.6"), 0),

                //Latest delta
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.6"), new ForgeVersion("1.1-recommended", "1.3.2.6"), 1),
                Arguments.of(new ForgeVersion("1.1-recommended", "1.3.2.6"), new ForgeVersion("1.1-latest", "1.3.2.6"), -1),

                //Forge delta
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.7"), new ForgeVersion("1.1-latest", "1.3.2.6"), 1),
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.5"), new ForgeVersion("1.1-latest", "1.3.2.6"), -1),

                //Minecraft delta
                Arguments.of(new ForgeVersion("1.2-latest", "1.3.2.5"), new ForgeVersion("1.1-latest", "1.3.2.5"), 1),
                Arguments.of(new ForgeVersion("1.0-latest", "1.3.2.5"), new ForgeVersion("1.1-latest", "1.3.2.5"), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("versionCompareData")
    void testVersionCompare(ForgeVersion a, ForgeVersion b, int output) {
        Assertions.assertEquals(a.compareTo(b), output);
    }

    static Stream<Arguments> versionEqualsData() {
        return Stream.of(
                //Not equals
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.7"), new ForgeVersion("1.1-recommended", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.1.1-latest", "1.3.2.7"), new ForgeVersion("1.1.1-recommended", "1.3.2.7"), false),

                Arguments.of(new ForgeVersion("1.1.1-latest", "1.3.2.8"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.1.1-latest", "1.3.4.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.1.1-latest", "1.4.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.1.1-latest", "2.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),

                Arguments.of(new ForgeVersion("1.1.2-latest", "1.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.2.1-latest", "1.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("2.1.1-latest", "1.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),
                Arguments.of(new ForgeVersion("1.1.1-recommends", "1.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), false),

                //Equals
                Arguments.of(new ForgeVersion("1.1-latest", "1.3.2.7"), new ForgeVersion("1.1-latest", "1.3.2.7"), true),
                Arguments.of(new ForgeVersion("1.1.1-latest", "1.3.2.7"), new ForgeVersion("1.1.1-latest", "1.3.2.7"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("versionEqualsData")
    void testVersionEquals(ForgeVersion a, ForgeVersion b, boolean shouldMatch) {
        Assertions.assertEquals(shouldMatch, a.equals(b));
    }
}
