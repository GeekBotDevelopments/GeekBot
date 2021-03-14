package bot.modules.minecraft.forge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandForgeVersion extends Command
{
    public CommandForgeVersion()
    {
        this.name = "forge-version";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Forge Versions", "http://files.minecraftforge.net/");
        generateList(embedBuilder);

        //Build and submit
        event.getChannel().sendMessage(embedBuilder.build()).submit();
    }

    private void generateList(EmbedBuilder embedBuilder)
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
            embedBuilder.addField(version.getMinecraft().toString(), url, true);
        });
    }
}
