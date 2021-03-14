package bot.modules.minecraft.forge;

import bot.models.SemVer;
import lombok.Data;

@Data
public class ForgeVersion implements Comparable<ForgeVersion>
{
    private final SemVer minecraft;
    private final SemVer forge;
    private final boolean latest;

    public ForgeVersion(String mcString, String forgeString)
    {
        final String[] split = mcString.split("-");
        minecraft = new SemVer(split[0]);
        latest = "latest".equalsIgnoreCase(split[1]);
        forge = new SemVer(forgeString);
    }

    @Override
    public int compareTo(ForgeVersion other)
    {
        final int minecraftCompare = minecraft.compareTo(other.minecraft);
        if (minecraftCompare != 0)
        {
            return minecraftCompare;
        }
        final int forgeCompare = forge.compareTo(other.forge);
        if (forgeCompare != 0)
        {
            return forgeCompare;
        }
        return Boolean.compare(latest, other.latest);
    }

    @Override
    public String toString()
    {
        return String.format("%s-%s%s", minecraft, forge, (latest ? "L" : "R"));
    }
}
