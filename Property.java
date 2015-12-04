public class Property extends Share {

    private String propertyInfo;

    public Property(String name, double value, int available, String info) {
        super(name, value, available);
        propertyInfo = info;
        super.setNumberAvailable(1);
    }

    @Override
    public String toString() {
        return "Property Name: " + super.getName() + "\nProperty Value: $" + super.getValue() + "\n\n" + propertyInfo;
    }
}
