package net.anotheria.strel.basic.magicsquares.util;

import java.util.NoSuchElementException;

/**
 * Contains numbers from which selection is done.
 *
 * User can select number from Sampler only once. Then its place is
 * occupied by zero.
 *
 * Number can be returned to Sampler and be available for selection.
 *
 * @author Strel97
 */
public class Sampler {

    private int[]   numbers;


    public Sampler(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Returns all possible, not selected numbers for given range.
     * @param bound Range of selection
     * @return      Not currently selected numbers from given range
     */
    public int[] getNumbers(Bound bound) {
        // Cuts current region to allowed values, if it exceeds it
        bound.cutRegion(1, numbers.length);

        int cnt = 0;
        int[] sample = new int[bound.length()];

        for (int num : bound) {
            if (numbers[num - 1] != 0) {
                sample[cnt++] = numbers[num - 1];
            }
        }

        return sample;
    }

    /**
     * Returns given number from selection if it's available (hasn't
     * selected before) or throws NoSuchElementException in other case.
     * @param num   Number to select
     * @return      Searched number
     */
    public int getNumber(int num) {
        if (!checkNumber(num))
            throw new NoSuchElementException();

        if (numbers[num - 1] != 0) {
            int number = numbers[num - 1];
            numbers[num - 1] = 0;

            return number;
        }

        throw new NoSuchElementException();
    }


    /**
     * Determines whether sampler contains requested number.
     * @param num   Number to test
     * @return      Whether sampler contains given number or no
     */
    public boolean contains(int num) {
        return checkNumber(num) && numbers[num - 1] != 0;
    }


    /**
     * Returns number back to the selection and makes it
     * available for further selections.
     *
     * @param num   Number to return
     * @return      Result of operation
     */
    public int returnNumber(int num) {
        if (!checkNumber(num))
            return -1;

        numbers[num - 1] = num;
        return num;
    }

    /**
     * Checks whether given number can be stored in selection.
     * @param num   Number to check
     * @return      Whether given number is stored in selection
     */
    private boolean checkNumber(int num) {
        return num > 0 && num <= numbers.length;
    }
}
