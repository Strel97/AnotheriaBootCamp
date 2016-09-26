package net.anotheria.strel.maps.sync;


import net.anotheria.strel.maps.notsynchronized.SimpleBank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Strel97
 */
public class LockedBank extends SimpleBank {

    private Lock lock;


    public LockedBank() {
        lock = new ReentrantLock();
    }

    /**
     * Uses {@link Lock} mechanism for synchronizing access
     * to bank accounts.
     *
     * @param amount    Money to add
     */
    @Override
    public void addFunds(int amount) {
        lock.lock();
        super.addFunds(amount);
        lock.unlock();
    }

    @Override
    public String getName() {
        return "Bank with Locks";
    }
}
