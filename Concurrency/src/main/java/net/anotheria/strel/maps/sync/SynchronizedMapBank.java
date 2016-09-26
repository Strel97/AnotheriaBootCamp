package net.anotheria.strel.maps.sync;


import net.anotheria.strel.maps.notsynchronized.SimpleBank;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author Strel97
 */
public class SynchronizedMapBank extends SimpleBank {

    /**
     * Uses {@link Collections.SynchronizedMap} data structure for storing money.
     */
    public SynchronizedMapBank() {
        super();

        accounts = Collections.synchronizedMap(new HashMap<>());
        resetAccounts();
    }

    @Override
    public String getName() {
        return "SynchronizedMap Bank";
    }
}
