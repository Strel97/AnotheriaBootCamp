package net.anotheria.strel.maps.sync;

import net.anotheria.strel.maps.notsynchronized.Bank;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Uses {@link ConcurrentMap} data structure for synchronizing access for
 * user accounts.
 *
 * @author Strel97
 */
public class ConcurrentBank implements Bank {

    private ConcurrentMap<Integer, Integer> accounts;


    public ConcurrentBank() {
        accounts = new ConcurrentHashMap<>();
        resetAccounts();
    }

    @Override
    public void resetAccounts() {
        for (int i = 0; i < 10; i++) {
            accounts.put(i, 0);
        }
    }

    @Override
    public void addFunds(int amount) {
        for (ConcurrentMap.Entry<Integer, Integer> account : accounts.entrySet()) {
            account.setValue(account.getValue() + amount);
        }
    }

    @Override
    public void printSecureInfo() {
        System.out.println(getName() + " " + accounts);
    }

    @Override
    public String getName() {
        return "ConcurrentMap Bank";
    }
}
