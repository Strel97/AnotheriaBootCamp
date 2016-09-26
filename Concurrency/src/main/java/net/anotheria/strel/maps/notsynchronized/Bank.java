package net.anotheria.strel.maps.notsynchronized;

/**
 * @author Strel97
 */
public interface Bank {

    /**
     * Adds specified amount of money to each account in bank.
     * @param amount    Money to add
     */
    void addFunds(int amount);

    /**
     * Resets balance at each account
     */
    void resetAccounts();

    /**
     * Prints all bank info including name and balance
     * at each user account.
     *
     * Yeah, bank provides methods for getting private
     * information :)
     */
    void printSecureInfo();

    /**
     * @return  Name of bank
     */
    String getName();
}
