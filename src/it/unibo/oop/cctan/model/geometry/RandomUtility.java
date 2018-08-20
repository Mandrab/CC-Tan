package it.unibo.oop.cctan.model.geometry;

import java.util.Random;

/**
 * Some static methods to generate random values.
 */
public final class RandomUtility {

    private static final Random RANDOM = new Random();

    private RandomUtility() { }

    /**
     * Generate new random integer in a given range.
     * @param min
                minimum value for the generated number (inclusive)
     * @param max
                maximum value for the generated number (exclusive)
     * @return
     *          the generated random number
     */
    public static int intInRange(final int min, final int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * Generate new random double in a given range.
     * @param min
                minimum value for the generated number (inclusive)
     * @param max
                maximum value for the generated number (exclusive)
     * @return
     *          the generated random number
     */
    public static double doubleInRange(final double min, final double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}
