package bot.modules.octopi.models.api.state;

import lombok.Data;

/**
 * Data model for printer state API
 *
 * @see <a href="https://docs.octoprint.org/en/master/api/printer.html#data-model">Printer API Documentation</a>
 *
 * Created by Robin Seifert on 4/1/2021.
 */
@Data
public class PrinterStateResponse
{
    private PrinterTemperatureState temperature;
    private SDCardState sd;
    private PrinterStateData state;
}
