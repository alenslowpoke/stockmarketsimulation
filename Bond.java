public class Bond extends Share {

    private double yieldValue;

    public Bond(String name, double value, int available, double yield) {
        super(name, value, available);
        yieldValue = yield;
    }

    @Override
    public String toString() {
        return "Bond Name: " + super.getName() + "\nBond Value: $" + super.getValue() + "\nAvailable: " + super.getNumberAvailable() + "\nYield Profit: " + yieldValue;
    }
}
