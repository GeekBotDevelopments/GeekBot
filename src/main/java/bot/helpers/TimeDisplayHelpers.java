package bot.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * Created by Robin Seifert on 4/3/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeDisplayHelpers
{
    /**
     * Formats the seconds into a more readable display
     *
     * @param secondInput value to format, will be rounded up before being converted to a long
     * @return string formatting time display
     */
    public static String convertSecondsToDisplay(final double secondInput)
    {
        if (Double.isNaN(secondInput))
        {
            return "NaN";
        }
        else if (Double.isInfinite(secondInput))
        {
            return "Infinite";
        }

        final boolean neg = secondInput < 0;
        long seconds = (long) Math.abs(Math.ceil(secondInput));
        if (seconds < 60)
        {
            return String.format("%ss", seconds * (neg ? -1 : 1));
        }

        //Get days
        final long days = TimeUnit.SECONDS.toDays(seconds);
        seconds -= TimeUnit.DAYS.toSeconds(days);

        //Get hours
        final long hours = TimeUnit.SECONDS.toHours(seconds);
        seconds -= TimeUnit.HOURS.toSeconds(hours);

        //Get minutes
        final long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        seconds -= TimeUnit.MINUTES.toSeconds(minutes);

        //Handle cases where we have zero of something
        if (days == 0)
        {
            if (hours == 0)
            {
                return String.format("%sm %ss", minutes * (neg ? -1 : 1), seconds * (neg ? -1 : 1));
            }
            return String.format("%sh %sm %ss", hours * (neg ? -1 : 1), minutes * (neg ? -1 : 1), seconds * (neg ? -1 : 1));
        }

        return String.format("%sd %sh %sm %ss", days * (neg ? -1 : 1), hours * (neg ? -1 : 1), minutes * (neg ? -1 : 1), seconds * (neg ? -1 : 1));
    }
}
