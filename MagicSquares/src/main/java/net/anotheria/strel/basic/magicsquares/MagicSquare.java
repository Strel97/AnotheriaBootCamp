package net.anotheria.strel.basic.magicsquares;


/**
 * Represents magic square. Contains methods for
 * converting square to string (for further outputting
 * or storing in database) and vice versa.
 *
 * @author Strel97
 */
public class MagicSquare {

    /**
     * Size of square
     */
    private int size;

    /**
     * Values of square in form of string
     */
    private String  square;

    /**
     * Numbers of magic square
     */
    private int[] numbers;


    public MagicSquare() {
    }

    public MagicSquare(String square) {
        this.square = square;
        String[] nums = square.split(" ");

        numbers = new int[nums.length];
        size = (int) Math.sqrt(nums.length);

        for (int i = 0; i < nums.length; i++) {
            numbers[i] = Integer.valueOf(nums[i]);
        }
    }

    public MagicSquare(int size, int[] numbers) {
        this.size = size;
        this.numbers = numbers.clone();

        StringBuilder builder = new StringBuilder();
        for (int num : numbers) {
            builder.append(num);
            builder.append(" ");
        }

        square = builder.toString();
    }

    public String getSquare() {
        return square;
    }

    public int getSize() {
        return size;
    }

    public int[] getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return square;
    }
}
