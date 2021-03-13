package bot.modules.minecraft.forge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class CommandForgeVersion extends Command
{
	public CommandForgeVersion()
	{
		this.name = "forge-version";
	}

	@Override
	protected void execute(CommandEvent event)
	{
		final EmbedBuilder build = new EmbedBuilder();
		final StringBuilder builder = build.getDescriptionBuilder();

		//Create title
		builder.append("Versions:");

		final ArrayList<ForgeVersion> versions = new ArrayList();
		ForgeVersionUtil.fetchForgeVersions(versions::add);
		versions.sort(ForgeVersion::compareTo);

		versions.forEach(version -> {
			builder.append("\n- M:");
			builder.append(version.getMinecraft());
			builder.append("  F:");
			builder.append(version.getForge());
		});

		//TODO get versions for last 5 MC versions
		//TODO show version, date released, url

		event.getChannel().sendMessage(build.build()).submit();
	}
}
