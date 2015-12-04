import java.io.Serializable;

public class Share implements Serializable {

    private String shareName;
    private double shareValue;
    private int shareAvailable;
    private final double SHARE_PURE_VALUE;

    public Share(String name, double value, int available) {
        shareName = name;
        SHARE_PURE_VALUE = value;
        shareValue = value / available;
        shareValue = Math.round(shareValue * 10) / 10;
        shareAvailable = available;
    }

    public String getName() {
        return shareName;
    }

    public void setValue(double value) {
        shareValue = value;
    }

    public double getValue() {
        return shareValue;
    }

    public void setNumberAvailable(int available) {
        shareAvailable = available;
    }

    public int getNumberAvailable() {
        return shareAvailable;
    }

    @Override
    public String toString() {
        return "Share Name: " + shareName + "\nShare Value: $" + shareValue + "\nNumber Available: " + shareAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Share share = (Share) o;

        if (!shareName.equals(share.shareName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public void setMarketValue() {
        // Formula to calculate share market value
        shareValue = SHARE_PURE_VALUE / shareAvailable;
        // Rounding up to one significant figure
        shareValue = Math.round(shareValue * 10) / 10;
    }
}