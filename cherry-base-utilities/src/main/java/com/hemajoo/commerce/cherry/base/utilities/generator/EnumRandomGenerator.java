/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.base.utilities.generator;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator for random enumerated values.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class EnumRandomGenerator
{
    /**
     * Enumeration class.
     */
    private final Class<? extends Enum<?>> enumClass;

    /**
     * Random number generator.
     */
    private final Random generator;

    /**
     * Minimal value.
     */
    private int min;

    /**
     * Maximal value.
     */
    private int max;

    /**
     * List of excluded values.
     */
    private List<Enum<?>> excludes;

    /**
     * Create a new enumeration generator.
     * @param enumClass Enumeration class.
     */
    public EnumRandomGenerator(final @NonNull Class<? extends Enum<?>> enumClass)
    {
        this.enumClass = enumClass;
        this.min = 0;
        this.max = enumClass.getEnumConstants().length - 1;
        this.generator = new Random();
    }

    /**
     * Exclude a value from the generation of possible enumerated values.
     * @param value Enumerated value.
     * @return {@link EnumRandomGenerator}.
     */
    public EnumRandomGenerator exclude(final @NonNull Enum<?> value)
    {
        if (excludes == null)
        {
            excludes = new ArrayList<>();
        }

        if (!excludes.contains(value))
        {
            excludes.add(value);
        }

        return this;
    }

    /**
     * Generate a random enumerated value.
     * @return Random enumerated value.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public Enum<?> generate() throws GeneratorException
    {
        Enum<?> value = null;
        boolean isValid = false;

        if (excludes != null && !excludes.isEmpty())
        {
            if (enumClass.getEnumConstants().length == excludes.size())
            {
                throw new GeneratorException(String.format(
                        "Not able to generate a random enumerated value for enumeration: '%s' as all values have been explicitly excluded!",
                        enumClass.getSimpleName()));
            }

            while (!isValid)
            {
                value = enumClass.getEnumConstants()[generator.nextInt(min, max)];
                if (!excludes.contains(value))
                {
                    isValid = true;
                }
            }

            return value;
        }

        return enumClass.getEnumConstants()[generator.nextInt(min, max)];
    }
}
