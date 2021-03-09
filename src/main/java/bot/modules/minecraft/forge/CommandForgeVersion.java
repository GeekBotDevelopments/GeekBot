package bot.modules.minecraft.forge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

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
		builder.append("Versions: \n");

		ForgeVersions.forgeVersions((version) -> builder.append("\n- " + version));

		//TODO get versions for last 5 MC versions
		//TODO show version, date released, url

		event.getChannel().sendMessage(build.build()).submit();
	}
}
