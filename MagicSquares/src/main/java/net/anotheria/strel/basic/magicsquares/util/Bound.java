package net.anotheria.strel.basic.magicsquares.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represents numerical range or bound for selection algorithm in
 * {MagicSquareBuilder}.
 *
 * @author Strel97
 */
public class Bound implements Iterable<Integer>, Iterator<Integer>{

    /**
     * Lower bound for numerical range
     */
    private int min;

    /**
     * Upper bound for numerical range
     */
    private int max;

    /**
     * Counter used for iterating through range
     */
    private int count;


    /**
     * Initializes new bound (or new numerical range) with given boundary values.
     * Also correction of boundary values is done, if parameters are given incorrectly.
     *
     * @param min   Lower boundary value
     * @param max   Upper boundary value
     */
    public Bound(int min, int max) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);

        count = 0;
    }

    /**
     * Determines whether current numerical range contains given number.
     * @param number    Number to test
     * @return          Whether number is in current range
     */
    public boolean contains(int number) {
        return number >= min && number <= max;
    }

    /**
     * @return  Number of elements in current range
     */
    public int length() {
        return Math.abs(max - min + 1);
    }

    /**
     * @return  Sum of all elements from current range
     */
    public int sumOfElements() {
        return (min + max) * (max - min + 1) / 2;
    }

    /**
     * @return  All elements from current range in form of array
     */
    public int[] getAllElements() {
        int[] numbers = new int[length()];
        for (int i = 0; i < length(); i++)
            numbers[i] = i + min;

        return numbers;
    }

    /**
     * Finds intersection of current range with given in parameters.
     * @param other Another range
     * @return      Intersection of current range with other, given in parameters
     */
    public Bound intersection(Bound other) {
        return new Bound(Math.max(min, other.min), Math.min(max, other.max));
    }

    public void cutRegion(int from, int to) {
        if (min < from || min > to)
            min = from;

        if (max > to || max < from)
            max = to;
    }

    public static int sumOfElements(int from, int to) {
        return new Bound(from, to).sumOfElements();
    }

    public static int[] getAllElements(int from, int to) {
        return new Bound(from, to).getAllElements();
    }

    public Iterator<Integer> iterator() {
        return this;
    }

    public boolean hasNext() {
        return count < length();
    }

    public Integer next() {
        if (count == length())
            throw new NoSuchElementException();

        return min + count++;
    }

    public void remove() {

    }

    @Override
    public String toString() {
        return String.format("Bound [ %d, %d ]", min, max);
    }
}
