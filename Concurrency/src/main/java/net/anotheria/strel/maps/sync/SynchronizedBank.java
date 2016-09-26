package net.anotheria.strel.maps.sync;


import net.anotheria.strel.maps.notsynchronized.SimpleBank;

import java.util.Map;

/**
 * @author Strel97
 */
public class SynchronizedBank extends SimpleBank {

    /**
     * Uses {synchronized} keyword for synchronizing access
     * to bank accounts
     *
     * @param amount    Money to add
     */
    public synchronized void addFunds(int amount) {
        for (Map.Entry<Integer, Integer> account : accounts.entrySet()) {
            account.setValue(account.getValue() + amount);
        }
    }

    @Override
    public String getName() {
        return "Bank with synchronized keyword";
    }
}
