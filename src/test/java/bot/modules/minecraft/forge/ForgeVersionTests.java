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
                Arguments.of("1.1-latest", "1.3.2.6", new SemVer(1, 1, 0, -1), new SemVer(1, 3, 2, 6), true)
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
        Assertions.assertEquals(minecraftVersion, version.minecraft, "Minecraft version didn't parse as expected");
        Assertions.assertEquals(forgeVersion, version.forge, "Forge version didn't parse as expected");
        Assertions.assertEquals(latest, version.latest);
    }
}
