package bot.modules.minecraft.forge;

import bot.modules.rest.RestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Robin Seifert on 3/13/2021.
 */
class ForgeVersionUtilTests
{
    String promotionSlimJson;

    @BeforeAll
    void beforeAll() throws IOException
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        final Path path = Paths.get(classLoader.getResource("promotions_slim.json").getFile());
        promotionSlimJson = String.join("", Files.readAllLines(path));
    }

    @Test
    void testFetchForgeVersions() throws IOException
    {
        //Mock web call
        try (MockedStatic mocked = Mockito.mockStatic(RestUtil.class))
        {
            mocked.when(() -> RestUtil.getString(ForgeVersionUtil.VERSION_URL)).thenReturn(promotionSlimJson);

            //Create data
            ArrayList<ForgeVersion> versionCheck = new ArrayList();
            versionCheck.add(new ForgeVersion("1.1-latest", "1.3.2.6"));
            versionCheck.add(new ForgeVersion("1.10-latest", "12.18.0.2000"));
            //...It will ignore the "latest-1.7.10" data point

            //Collect
            ArrayList<ForgeVersion> versions = new ArrayList();
            ForgeVersionUtil.fetchForgeVersions((version) -> versions.add(version));

            //Verify we called the web url
            mocked.verify(Mockito.times(1), () -> RestUtil.getString(ForgeVersionUtil.VERSION_URL));

            //Validate data
            Assertions.assertIterableEquals(versionCheck, versions);
        }
    }
}
