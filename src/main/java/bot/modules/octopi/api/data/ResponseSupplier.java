package bot.modules.octopi.api.data;

import com.mashape.unirest.http.HttpResponse;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Robin Seifert on 4/5/2021.
 */
@Data
public class ResponseSupplier<T> implements Supplier<Optional<T>>
{
    public static final ResponseSupplier EMPTY = new ResponseSupplier() {
        @Nullable
        @Override
        public Optional get()
        {
            return Optional.empty();
        }
    };

    //Data object
    private Optional<T> data = Optional.empty();

    //Response info
    /** API call status string */
    private String status;
    /** API status code, Example: {@link HttpURLConnection#HTTP_OK} */
    private int statusCode;

    //Error handling
    /** Basic error message */
    private String errorText;
    /** True if the call completed and we got a response back from the server. False if the call threw an error. */
    private boolean completedCall;

    //Debug timings
    /** Millisecond timing of when the call started */
    private Long lastCallTime;
    /** Millisecond timing of when the call ended */
    private Long lastReceivedTime;

    @Nonnull
    public static <T> ResponseSupplier<T> create(
            @Nonnull final HttpResponse<String> response,
            @Nonnull final Function<String, T> builder,
            @Nonnull final Function<Exception, String> errorHandler)
    {

        final ResponseSupplier<T> supplier = new ResponseSupplier<>();
        supplier.status = response.getStatusText();
        supplier.statusCode = response.getStatus();
        supplier.completedCall = true;
        if (supplier.statusCode == HttpURLConnection.HTTP_OK)
        {
            try
            {
                supplier.data = Optional.ofNullable(builder.apply(response.getBody()));
            }
            catch (Exception e)
            {
                supplier.errorText = errorHandler.apply(e);
                if (supplier.errorText == null)
                {
                    throw e;
                }
            }
        }
        return supplier;
    }

    @Nonnull
    @Override
    public Optional<T> get()
    {
        return data;
    }
}
