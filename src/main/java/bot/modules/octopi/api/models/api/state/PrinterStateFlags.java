package bot.modules.octopi.api.models.api.state;

import lombok.Data;

/**
 * Created by Robin Seifert on 4/1/2021.
 */
@Data
class PrinterStateFlags
{
    private boolean operational;
    private boolean paused;
    private boolean printing;
    private boolean cancelling;
    private boolean pausing;
    private boolean sdReady;
    private boolean error;
    private boolean ready;
    private boolean closedOrError;
}
