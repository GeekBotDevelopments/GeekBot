package bot.models;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Dark(DarkGuardsman, Robert) on 3/8/2021.
 */
@Data
public class SemVer implements Comparable<SemVer>
{
    private final int major;
    private final int minor;
    private final int patch;

    /** Extra bit for versions with build numbers */
    private final int build;

    public SemVer(String version)
    {
        final String[] dots = version.split("\\.");

        major = Integer.parseInt(dots[0]);
        minor = Integer.parseInt(dots[1]);
        patch = dots.length > 2 ? Integer.parseInt(dots[2]) : -1;
        build = dots.length > 3 ? Integer.parseInt(dots[3]) : -1;
    }

    public SemVer(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = -1;
    }

    public SemVer(int major, int minor, int patch, int build) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = build;
    }

    @Override
    public int compareTo(@NotNull SemVer other)
    {
        final int majorCompare = Integer.compare(major, other.major);
        if (majorCompare != 0)
        {
            return majorCompare;
        }
        final int minorCompare = Integer.compare(minor, other.minor);
        if (minorCompare != 0)
        {
            return minorCompare;
        }
        final int pathCompare = Integer.compare(patch, other.patch);
        if (pathCompare != 0)
        {
            return pathCompare;
        }
        return Integer.compare(build, other.build);
    }

    @Override
    public String toString()
    {
        if (patch != -1)
        {
            if (build != -1)
            {
                return String.format("%d.%d.%d.%d", major, minor, patch, build);
            }
            return String.format("%d.%d.%d", major, minor, patch);
        }
        return String.format("%d.%d", major, minor);
    }
}
