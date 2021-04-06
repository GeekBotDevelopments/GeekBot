package bot.modules.octopi.api.models.api.state;

import lombok.Data;

/**
 * Created by Robin Seifert on 4/1/2021.
 */
@Data
public class PrinterTemperatureState
{
    private TemperatureStateValue tool0; //TODO map tools 0..n to an array
    private TemperatureStateValue tool1;
    private TemperatureStateValue bed; //TODO map beds 0...n to an array, its rare but API supports more than 1 bed

    /** Only exists on {@link #history} objects */
    private long time;

    /** Only exists on root temperature data */
    private PrinterTemperatureState[] history;
}
