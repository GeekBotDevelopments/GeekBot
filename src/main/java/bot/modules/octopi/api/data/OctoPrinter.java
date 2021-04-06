package bot.modules.octopi.api.data;

import bot.GeekBot;
import bot.modules.octopi.api.OctoprintEndpoints;
import bot.modules.octopi.api.models.api.job.PrinterJobResponse;
import bot.modules.octopi.api.models.api.state.PrinterStateData;
import bot.modules.octopi.api.models.api.state.PrinterStateResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Object for handling printer interactions
 * Created by Robin Seifert on 4/5/2021.
 */
@Data
public final class OctoPrinter
{
    private final String name;
    private final String apiKey;
    private final String accessUrl;

    /** Previous state of field {@link #stateResponse} using data {@link PrinterStateResponse#getState()}  */
    private PrinterStateData previousState = new PrinterStateData();

    public OctoPrinter(final String name, final String apiKey, final String accessUrl)
    {
        this.name = name;
        this.apiKey = apiKey;
        this.accessUrl = accessUrl;
    }

    @Setter(AccessLevel.NONE)
    private ResponseSupplier<PrinterStateResponse> stateResponse = ResponseSupplier.EMPTY;

    @Setter(AccessLevel.NONE)
    private ResponseSupplier<PrinterJobResponse> jobResponse = ResponseSupplier.EMPTY;

    public OctoPrinter refreshEndpointData()
    {
        refreshStateData();
        refreshJobData();
        return this;
    }

    public OctoPrinter refreshStateData()
    {
        stateResponse = OctoprintEndpoints.makeApiCall(
                OctoprintEndpoints.newPrinterCall(this),
                string -> GeekBot.GSON.fromJson(string, PrinterStateResponse.class),
                Throwable::toString
        );
        return this;
    }

    public OctoPrinter refreshJobData()
    {
        jobResponse = OctoprintEndpoints.makeApiCall(
                OctoprintEndpoints.newJobCall(this),
                string -> GeekBot.GSON.fromJson(string, PrinterJobResponse.class),
                Throwable::toString
        );
        return this;
    }
}
