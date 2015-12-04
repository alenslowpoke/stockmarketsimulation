/*
AUTHOR: Alen Zeinolov

This class initializes all share objects available
 */

import java.io.Serializable;
import java.util.ArrayList;

public class ShareInit implements Serializable {

    ArrayList<Share> stocks = new ArrayList<Share>();

    public ShareInit() {
        Share gold = new Share("Gold", 12000000.0, 5000);
        Share silver = new Share("Silver", 90000000.0, 10000);
        Share platinum = new Share("Platinum", 15000000.0, 4000);
        Share pizzeria = new Property("Pizzeria Pablo", 12000000.0, 1, "Great Pizzeria located in Milan, Italy");
        Share apple = new Company("Apple", 1280000000000.00, 50000000, "$749.88B", "1.88 (1.50%)");
        Share USTreasure = new Bond("US Treasury", 260000000.00, 10000000, 2.51);

        stocks.add(gold);
        stocks.add(silver);
        stocks.add(platinum);
        stocks.add(pizzeria);
        stocks.add(apple);
        stocks.add(USTreasure);
    }

    public Share getShare(int position) {
        return stocks.get(position);
    }
}