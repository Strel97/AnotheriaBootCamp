package net.anotheria.strel.maps;


import net.anotheria.strel.maps.notsynchronized.Bank;

/**
 * Represents bank transaction. SimpleBank transaction adds specified amount
 * of money from all accounts. Each transaction is executed in different thread.
 *
 * @author Strel97
 */
public class BankTransaction implements Runnable {

    /**
     * Reference to the bank
     */
    private Bank bank;


    public BankTransaction(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        bank.addFunds(200);
    }
}
