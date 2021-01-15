package bot.modules.octopi;

public enum FilamentTypes
{

    PLA(1.75, 1, 22.99, 1.24);

    double diameter;
    double mass;
    double pricePerMass;
    double density;

    FilamentTypes(double diameter, double mass, double pricePerMass, double density)
    {
        this.diameter = diameter;
        this.mass = mass;
        this.pricePerMass = pricePerMass;
        this.density = density;
    }

    /**
     * @return the diameter
     */
    public double getDiameter()
    {
        return diameter;
    }

    /**
     * @return the mass
     */
    public double getMass()
    {
        return mass;
    }

    /**
     * @return the pricePerMass
     */
    public double getPricePerMass()
    {
        return pricePerMass;
    }

    /**
     * @return the density
     */
    public double getDensity()
    {
        return density;
    }

    public double getPrice()
    {
        return PrinterUtilities.fillamentPricePerLength(diameter, mass, pricePerMass, density);
    }
}
