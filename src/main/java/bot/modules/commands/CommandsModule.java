package bot.modules.commands;

import bot.modules.discord.DiscordModule;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
public class CommandsModule
{
    public static void load() {
        DiscordModule.register(new CmdContribute());
        DiscordModule.register(new CmdHug());
        DiscordModule.register(new CmdInvite());
        DiscordModule.register(new CmdPing());
        DiscordModule.register(new CmdStopBot());
        DiscordModule.register(new CmdUserInfo());
    }
}
