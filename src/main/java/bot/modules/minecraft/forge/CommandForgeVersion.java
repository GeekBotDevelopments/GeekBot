package bot.modules.minecraft.forge;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandForgeVersion extends Command
{
    public CommandForgeVersion()
    {
        super("version");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
    {
        return channel.createEmbed(embedCreateSpec -> {
            embedCreateSpec.setTitle("Forge Versions").setUrl("http://files.minecraftforge.net/");
            generateList(embedCreateSpec);
        });
    }

    private void generateList(final EmbedCreateSpec embedCreateSpec)
    {
        final ArrayList<ForgeVersion> versions = new ArrayList<>();
        ForgeVersionUtil.fetchForgeVersions(versions::add);

        //Sort list by version then reverse so latest is at front
        versions.sort(ForgeVersion::compareTo);
        Collections.reverse(versions);

        //Trim to latest 5 versions
        final List<ForgeVersion> last = versions.subList(0, Math.min(6, versions.size()));

        //Generate list
        last.forEach(version -> {
            final String versionString = String.format("%s-%s", version.getMinecraft(), version.getForge());
            final String url = String.format(
                    "%s: [%s](https://files.minecraftforge.net/maven/net/minecraftforge/forge/%s/forge-%s-installer.jar)",
                    version.getForge().toString(),
					(version.isLatest() ? "Latest" : "Recommended"),
                    versionString,
                    versionString
            );
            embedCreateSpec.addField(version.getMinecraft().toString(), url, true);
        });
    }
}
