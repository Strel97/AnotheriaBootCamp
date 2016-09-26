package net.anotheria.strel.maps.notsynchronized;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents bank, which stores money in different accounts in form of map.
 * @author Strel97
 */
public class SimpleBank implements Bank {

    /**
     * User accounts in bank
     */
    protected Map<Integer, Integer> accounts;


    public SimpleBank() {
        accounts = (new HashMap<>());
        resetAccounts();
    }

    /**
     * Adds specified amount of money to each account in bank.
     * @param amount    Money to add
     */
    public void addFunds(int amount) {
        for (Map.Entry<Integer, Integer> account : accounts.entrySet()) {
            account.setValue(account.getValue() + amount);
        }
    }

    /**
     * Resets balance at each account
     */
    public void resetAccounts() {
        for (int i = 0; i < 10; i++) {
            accounts.put(i, 0);
        }
    }

    public void printSecureInfo() {
        System.out.println(getName() + " " + accounts);
    }

    @Override
    public String getName() {
        return "Simple Bank";
    }
}
