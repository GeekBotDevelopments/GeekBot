package bot.modules.octopi.api;

import bot.GeekBot;
import bot.modules.octopi.api.data.OctoPrinter;
import bot.modules.octopi.api.data.ResponseSupplier;
import bot.modules.rest.RestUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Robin Seifert on 4/5/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OctoprintEndpoints
{
    private static final String URL_JOB_API = "job";
    private static final String URL_PRINTER_API = "printer";
    private static final String URL_VERSION_API = "version";

    //<editor-fold desc="Url Helpers">
    public String createUrl(String url, String subPath)
    {
        //url >> http://chiron.local
        return String.format("%s/%s", url, subPath);
    }

    public static String createApiUrl(String url, String subPath)
    {
        //url >> http://chiron.local
        return String.format("%s/api/%s", url, subPath);
    }

    public static GetRequest createApiGetRequest(String url, String apiKey, String subPath)
    {
        return (GetRequest) RestUtil.getRequest(createApiUrl(url, subPath))
                .queryString("apikey", apiKey);
    }
    //</editor-fold>

    //<editor-fold desc="Get Requests">
    public static GetRequest newJobCall(OctoPrinter printer)
    {
        return createApiGetRequest(printer.getAccessUrl(), printer.getApiKey(), URL_JOB_API);
    }

    public static GetRequest newPrinterCall(OctoPrinter printer)
    {
        return createApiGetRequest(printer.getAccessUrl(), printer.getApiKey(), URL_PRINTER_API);
    }

    public static GetRequest newVersionAPICall(OctoPrinter printer)
    {
        return createApiGetRequest(printer.getAccessUrl(), printer.getApiKey(), URL_VERSION_API);
    }
    //</editor-fold>

    @Nonnull
    public static <T> ResponseSupplier<T> makeApiCall(
            @Nonnull HttpRequest request,
            @Nonnull Function<String, T> responseMapper,
            @Nonnull Function<Exception, String> errorMapper)
    {
        ResponseSupplier<T> responseSupplier;
        final long start = System.currentTimeMillis();
        try
        {
            final HttpResponse<String> response = request.asString();
            final long end = System.currentTimeMillis();
            responseSupplier = ResponseSupplier.create(response, responseMapper, errorMapper);
            responseSupplier.setLastCallTime(start);
            responseSupplier.setLastReceivedTime(end);
        }
        catch (UnirestException e)
        {
            final String lintedEndpoint = request.getUrl().split("\\?")[0];
            GeekBot.MAIN_LOG.error(String.format("Failed to make API call '%s'", lintedEndpoint), e);

            responseSupplier = new ResponseSupplier<>();
            responseSupplier.setData(Optional.empty());
            responseSupplier.setCompletedCall(false);
            responseSupplier.setLastCallTime(start);
            responseSupplier.setLastReceivedTime(System.currentTimeMillis());
        }
        return responseSupplier;
    }
}
