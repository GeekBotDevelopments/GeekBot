package bot.modules.minecraft.forge;

import bot.modules.rest.RestUtil;
import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Robin Seifert on 3/13/2021.
 */
@TestWithResources
class ForgeVersionUtilTests
{
    @GivenTextResource("promotions_slim.json")
    String promotionSlimJson;

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
