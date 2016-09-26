package net.anotheria.strel.maps;


import net.anotheria.strel.maps.notsynchronized.Bank;
import net.anotheria.strel.maps.notsynchronized.SimpleBank;
import net.anotheria.strel.maps.sync.ConcurrentBank;
import net.anotheria.strel.maps.sync.LockedBank;
import net.anotheria.strel.maps.sync.SynchronizedBank;
import net.anotheria.strel.maps.sync.SynchronizedMapBank;

/**
 * Tries to crush bank with a plenty of transactions executed at the same time.
 * SimpleBank that correctly manages multithreaded operations should keep at the accounts
 * equal amount of money.
 *
 * There are different types of banks:
 * - Simple bank without any synchronization
 * - SimpleBank that uses synchronized keyword
 * - SimpleBank that uses SynchronizedMap for accounts storage
 * - SimpleBank that uses locks for synchronization
 *
 * @author Strel97
 */
public class BankCrasher {


    public static void main(String[] args) {
        Bank[] banks = {
                new SimpleBank(), new SynchronizedBank(),
                new SynchronizedMapBank(), new LockedBank(),
                new ConcurrentBank()
        };

        for (Bank bank : banks) {
            // Creating and executing 500 transactions for bank
            for (int i = 0; i < 500; i++) {
                new Thread(new BankTransaction(bank)).start();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bank.printSecureInfo();
        }
    }
}
