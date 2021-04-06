package bot.modules.octopi.api.models.api.state;

import lombok.Data;

/**
 * Created by Robin Seifert on 4/1/2021.
 */
@Data
public class PrinterStateData
{
    private String text;
    private PrinterStateFlags flags;
}
