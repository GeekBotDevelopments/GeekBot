package bot.modules.minecraft.forge;

import bot.modules.rest.RestUtil;
import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Robin Seifert on 3/13/2021.
 */
@TestWithResources
class CommandForgeVersionTests
{
    @GivenTextResource("promotions_slim.json")
    String promotionSlimJson;

    @Test
    void testCommand()
    {
        //Mock send message implementation to gather generated message
        final AtomicReference<MessageEmbed> embed = new AtomicReference();

        final CommandEvent commandEvent = Mockito.mock(CommandEvent.class);
        final MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
        final MessageAction messageAction = Mockito.mock(MessageAction.class);

        Mockito.when(commandEvent.getChannel()).thenReturn(messageChannel);
        Mockito.when(messageChannel.sendMessage(Mockito.any(MessageEmbed.class)))
                .then(answer -> {
                    embed.set(answer.getArgument(0));
                    return messageAction;
                });

        try (MockedStatic mocked = Mockito.mockStatic(RestUtil.class))
        {
            mocked.when(() -> RestUtil.getString(ForgeVersionUtil.VERSION_URL)).thenReturn(promotionSlimJson);

            //Make call
            CommandForgeVersion commandForgeVersion = new CommandForgeVersion();
            commandForgeVersion.execute(commandEvent);

            //Validate data
            final MessageEmbed message = embed.get();
            Assertions.assertNotNull(message);
            Assertions.assertEquals("Forge Versions", message.getTitle());
            Assertions.assertEquals("http://files.minecraftforge.net/", message.getUrl());

            //Expect 2 entries only
            Assertions.assertEquals(2, message.getFields().size());

            //Expect field 1 to match
            Assertions.assertEquals("1.1", message.getFields().get(1).getName());
            Assertions.assertEquals("1.3.2.6: [Latest](https://files.minecraftforge.net/maven/net/minecraftforge/forge/1.1-1.3.2.6/forge-1.1-1.3.2.6-installer.jar)", message.getFields().get(1).getValue());

            //Expect field 0 to match
            Assertions.assertEquals("1.10", message.getFields().get(0).getName());
            Assertions.assertEquals("12.18.0.2000: [Latest](https://files.minecraftforge.net/maven/net/minecraftforge/forge/1.10-12.18.0.2000/forge-1.10-12.18.0.2000-installer.jar)", message.getFields().get(0).getValue());


            //Validate we sent the message
            Mockito.verify(messageAction).submit();
        }
    }
}
