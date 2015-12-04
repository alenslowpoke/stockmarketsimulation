/*
AUTHOR: Alen Zeinolov

This class defines Investor objects. They have two variables that contain their name and balance and also two array
lists that save the information about currently owned shares.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Investor implements Serializable {

    private String investorName;
    private double investorCapital;
    private ArrayList<Share> shareOwned;
    private ArrayList<Integer> numberOwned;

    public Investor(String name, double capital) {
        investorName = name;
        investorCapital = capital;
        shareOwned = new ArrayList<Share>();
        numberOwned = new ArrayList<Integer>();
    }

    // The method for buying shares, price of the share * amount is deducted from investors account
    public void buyShare(double price, int amount, Share stock) {
        investorCapital -= price * amount;
        if(shareOwned.contains(stock)) {
            numberOwned.set(shareOwned.indexOf(stock), numberOwned.get(shareOwned.indexOf(stock)) + amount);
        }
        else {
            shareOwned.add(stock);
            numberOwned.add(amount);
        }
    }

    // The method for selling shares, price of the share * amount is added to the investors account
    public void sellShare(double income, int amount, Share stock) {

        investorCapital += income * amount;
        if((numberOwned.get(shareOwned.indexOf(stock)) - amount) == 0) {
                numberOwned.remove(shareOwned.indexOf(stock));
                shareOwned.remove(shareOwned.indexOf(stock));
        }
        else {
                numberOwned.set(shareOwned.indexOf(stock), numberOwned.get(shareOwned.indexOf(stock)) - amount);
        }
    }

    public String getInvestorName() {
        return investorName;
    }

    public Double getInvestorCapital() {
        return investorCapital;
    }

    // Sets what share investor will own after this method will be run. It goes through array and if it find null (empty) space it will insert the share item in there.
    public void setShareOwned(Share s) {
        for(int i = 0; i <= shareOwned.size(); i++) {
            if(shareOwned.get(i) == null) {
                shareOwned.add(i, s);
                break;
            }
            else {
                continue;
            }
        }
    }


    public Share getShareOwned(int i) {
        return shareOwned.get(i);
    }

    public ArrayList<Share> getShareOwnedArray() {
        return shareOwned;
    }

    // Sets how many shares investor own, directly related to setShareOwned(). Uses same algorithm.
    public void setNumberShareOwned(int n) {
        for(int i = 0; i <= shareOwned.size(); i++) {
            if(numberOwned.get(i) == 0) {
                numberOwned.add(i, n);
                break;
            }
            else {
                continue;
            }
        }
    }

    public int getNumberShareOwned(int i) {
        return numberOwned.get(i);
    }

    public ArrayList<Integer> getNumberShareOwnedArray() {
        return numberOwned;
    }

    public String toString() {
        String details = "Investor Capital: $"+ investorCapital +"\nShares owned:\n";
        for(int i = 0; i < shareOwned.size(); i++) {
            details += shareOwned.get(i).getName() + " - " + numberOwned.get(i) + "\n";
        }
        return details;
    }
}
