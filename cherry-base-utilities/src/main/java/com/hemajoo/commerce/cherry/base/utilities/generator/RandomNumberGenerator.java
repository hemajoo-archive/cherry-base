package com.hemajoo.commerce.cherry.base.utilities.generator;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

/**
 * Pseudo-random number generator class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class RandomNumberGenerator
{
    /**
     * Secure random number generator.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Min and max bounds error.
     */
    public static final String MIN_MAX_ERROR = "lower bound must be less than or equal to higher bound!";

    /**
     * Set the seed of the random number generator.
     * @param seed Seed value.
     */
    public static void setSeed(final int seed)
    {
        RANDOM.generateSeed(seed);
    }

    /**
     * Generate a pseudo-random <b>int</b> in the range [0, max].
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random integer.
     */
    public static int nextInt(final int max)
    {
        return RANDOM.nextInt(max);
    }

    /**
     * Generate a pseudo-random <b>integer</b> in the range [min, max].
     * @param min The starting value of the range (inclusive).
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random integer.
     */
    public static int nextInt(final int min, final int max)
    {
        Preconditions.checkArgument(min < max, MIN_MAX_ERROR);

        return RANDOM.nextInt((max - min) + 1) + min;
    }

    /**
     * Generate a pseudo-random <b>long</b> in the range [0, max].
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random long.
     */
    public static long nextLong(final long max)
    {
        return RANDOM
                .longs(0, max + 1)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Generate a pseudo-random <b>long</b> in the range [min, max].
     * @param min The starting value of the range (inclusive).
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random long.
     */
    public static long nextLong(final long min, final long max)
    {
        Preconditions.checkArgument(min < max, MIN_MAX_ERROR);

        return RANDOM
                .longs(min, max + 1)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Generate a pseudo-random <b>float</b> in the range [0, max].
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random float.
     */
    public static float nextFloat(final float max)
    {
        return max * RANDOM.nextFloat();
    }

    /**
     * Generate a pseudo-random <b>float</b> in the range [min, max].
     * @param min The starting value of the range (inclusive).
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random float.
     */
    public static float nextFloat(final float min, final float max)
    {
        Preconditions.checkArgument(min < max, MIN_MAX_ERROR);

        return min + (max - min) * RANDOM.nextFloat();
    }

    /**
     * Generate a pseudo-random <b>double</b> in the range [0, max].
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random double.
     */
    public static double nextDouble(final double max)
    {
        return RANDOM
                .doubles(0, max + 1)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Generate a pseudo-random <b>double</b> in the range [min, max].
     * @param min The starting value of the range (inclusive).
     * @param max The ending value of the range (inclusive).
     * @return Pseudo-random double.
     */
    public static double nextDouble(final double min, final double max)
    {
        Preconditions.checkArgument(min < max, MIN_MAX_ERROR);

        return RANDOM
                .doubles(min, max + 1)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Generate a pseudo-random <b>boolean</b>.
     * @return Pseudo-random boolean.
     */
    public static boolean nextBoolean()
    {
        return RANDOM.nextBoolean();
    }
}
