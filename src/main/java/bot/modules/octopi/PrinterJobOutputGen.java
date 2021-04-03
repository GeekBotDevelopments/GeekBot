package bot.modules.octopi;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class PrinterJobOutputGen
{

    static Gson gson = new Gson();
    private static final Logger log = LogManager.getLogger();

    private PrinterJobOutputGen()
    {
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
