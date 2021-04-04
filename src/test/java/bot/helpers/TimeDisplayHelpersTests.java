package bot.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by Robin Seifert on 4/3/2021.
 */
public class TimeDisplayHelpersTests
{

    static Stream<Arguments> convertSecondsToDisplay()
    {
        return Stream.of(
                //Invalid data
                Arguments.of(Double.NaN, "NaN"),
                Arguments.of(Double.NEGATIVE_INFINITY, "Infinite"),
                Arguments.of(Double.POSITIVE_INFINITY, "Infinite"),

                //Seconds only
                Arguments.of(0, "0s"),
                Arguments.of(1, "1s"),
                Arguments.of(-1, "-1s"),
                Arguments.of(59, "59s"),

                //Minutes
                Arguments.of(60, "1m 0s"),
                Arguments.of(-60, "-1m 0s"),

                //Hours
                Arguments.of(TimeUnit.HOURS.toSeconds(1), "1h 0m 0s"),
                Arguments.of(-TimeUnit.HOURS.toSeconds(1), "-1h 0m 0s"),

                //Days
                Arguments.of(TimeUnit.DAYS.toSeconds(1), "1d 0h 0m 0s"),
                Arguments.of(-TimeUnit.DAYS.toSeconds(1), "-1d 0h 0m 0s"),

                //Real examples
                Arguments.of(6457, "1h 47m 37s")
        );
    }

    @ParameterizedTest(name = "#{index}: {0,number} -> {1}")
    @MethodSource("convertSecondsToDisplay")
    void convertSecondsToDisplay(double input, String output)
    {
        Assertions.assertEquals(output, TimeDisplayHelpers.convertSecondsToDisplay(input));
    }
}
