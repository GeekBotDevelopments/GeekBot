package bot.modules.minecraft.forge;

import bot.modules.discord.CommandRoot;
import bot.modules.discord.DiscordModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 3/23/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ForgeModule
{
    public static CommandRoot commandRoot = new CommandRoot("forge");

    public static void load()
    {
        DiscordModule.register(commandRoot);
        commandRoot.register(new CommandForgeVersion());
    }
}
