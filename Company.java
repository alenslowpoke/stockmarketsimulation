public class Company extends Share {

    private String marketCap;
    private String divYield;

    public Company(String name, double value, int available, String marketcap, String divandyeld) {
        super(name, value, available);
        marketCap = marketcap;
        divYield = divandyeld;
    }

    @Override
    public String toString() {
        return "Company Name: " + super.getName() + "\nShare Value: $" + super.getValue() + "\nAvailable: " + super.getNumberAvailable() + "\nMarket Capitalisation: " + marketCap + "\nDiv & Yield: " + divYield;
    }
}
