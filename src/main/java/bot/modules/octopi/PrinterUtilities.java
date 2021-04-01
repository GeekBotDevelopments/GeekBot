package bot.modules.octopi;

import bot.modules.octopi.models.PrintJobInfo;
import bot.modules.rest.RestUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discord4j.core.spec.EmbedCreateSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrinterUtilities
{

    static Gson gson = new Gson();
    private static final Logger log = LogManager.getLogger();

    private PrinterUtilities()
    {
    }


    public static EmbedCreateSpec createPrinterOutput(EmbedCreateSpec builder, PrinterEnum printer)
    {
        PrintJobInfo info = null;
        Integer estimatedPrintTime = null;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        try
        {
            final JsonObject json = JsonParser.parseString(RestUtil.getString(printer.getUrl() + "/api/job?apikey=" + printer.getKey()))
                    .getAsJsonObject();
            info = gson.fromJson(json, PrintJobInfo.class);
            estimatedPrintTime = info.getJob().getEstimatedPrintTime();

        }
        catch (Exception e)
        {
            log.catching(e);
            builder.addField("Json", "None", true);
            return builder;
        }

        if (estimatedPrintTime != null)
        {
            hours = estimatedPrintTime / 3600;
            minutes = (estimatedPrintTime % 3600) / 60;
            seconds = estimatedPrintTime % 60;
        }

        if (info != null)
        {
            if (info.getJob() != null)
            {
                if (info.getJob().getFile().getName() != null)
                {
                    builder.addField("File name", info.getJob().getFile().getName(), true);
                }

                if (estimatedPrintTime != null)
                {
                    builder.addField("Estimated Print Time", String.format("%02d:%02d:%02d", hours, minutes, seconds), true);
                }

                if (info.getJob().getFilament().getLength() != null)
                {
                    builder.addField("Filament Length", info.getJob().getFilament().getLength().toString(), true);
                    builder.addField("Filament Price",
                            String.format("%.2f", FilamentTypes.PLA.getPrice() * info.getJob().getFilament().getLength()),
                            true);
                }
                else
                {
                    builder.addField("Filament Length", info.getJob().getFilament().getTool0().getLength().toString(),
                            true);
                    builder.addField("Filament Price",
                            String.format("%.2f",
                                    FilamentTypes.PLA.getPrice() * info.getJob().getFilament().getTool0().getLength()),
                            true);
                }

                if (info.getJob().getFilament().getVolume() != null)
                {
                    builder.addField("Filament Volume", info.getJob().getFilament().getVolume().toString(), true);
                }
                else
                {
                    builder.addField("Filament Volume", info.getJob().getFilament().getTool0().getVolume().toString(),
                            true);
                }

                if (info.getProgress().getCompletion() != null)
                {
                    builder.addField("Percent Done", info.getProgress().getCompletion().toString(), true);
                }
            }
            else
            {
                builder.addField("Job", "None", true);
            }

            builder.addField("Printer State", info.getState(), true);
        }
        else
        {
            builder.addField("Info", "Nil", true);
        }

        return builder;
    }

    public static double fillamentPricePerLength(double diameter, double mass, double pricePerMass, double density)
    {
        // Parameters:
        // diameter: Fillament diameter, in millimeters (mm).
        // mass: Spool net mass/weight, in kilograms (kg).
        // pricePerMass: Cost of spool, currency agnostic. No cents.
        // density: Fillament density, in g/cm^3. Not often labelled, Simplify3D values
        // below:
        // PLA: 1.24 g/cm^3
        // ABS: 1.04 g/cm^3
        // PETG: 1.23 g/cm^3
        // TPU: 1.21 g/cm^3 (median)
        //
        // Output is cost per millimetre, in the same unit as input (Dollars = dollars,
        // Cents = cents)

        if (diameter <= 0)
        {
            throw new java.lang.IllegalArgumentException("diameter must be positive");
        }
        if (mass <= 0)
        {
            throw new java.lang.IllegalArgumentException("mass must be positive");
        }
        if (density <= 0)
        {
            throw new java.lang.IllegalArgumentException("density must be positive");
        }
        if (pricePerMass <= 0)
        {
            throw new java.lang.IllegalArgumentException("pricePerMass must be positive");
        }

        double volume = mass / density * 1000.0;
        double length = volume / (java.lang.Math.PI * java.lang.Math.pow(diameter / 2.0, 2)) * 1000.0;
        double pricePerLength = 1.0 / length * pricePerMass;

        return pricePerLength;
    }

}
