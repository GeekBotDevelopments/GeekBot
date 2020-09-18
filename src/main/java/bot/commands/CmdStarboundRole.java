package bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.entities.Role;

public class CmdStarboundRole extends Command {

	public CmdStarboundRole() {
		this.name = "starbound";
		this.hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		Role starbound = event.getGuild().getRoleById(755597397710733352l);
		if (event.getMember().getRoles().contains(starbound)) {
			event.getGuild().removeRoleFromMember(event.getMember().getIdLong(), starbound).submit();
			event.getChannel().sendMessage("sucessfully removed the starbound update role").submit();
		} else {
			event.getGuild().addRoleToMember(event.getMember().getIdLong(), starbound).submit();
			event.getChannel().sendMessage("sucessfully given the starbound role").submit();
		}
	}

}
