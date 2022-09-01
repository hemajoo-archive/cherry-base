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

/**
 * Generator for random enumerated values.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class RandomEnumGenerator
{
    /**
     * Enumeration class.
     */
    private final Class<? extends Enum<?>> enumClass;

    /**
     * Minimal value.
     */
    private final int min;

    /**
     * Maximal value.
     */
    private final int max;

    /**
     * List of excluded values.
     */
    private List<Enum<?>> excludes;

    /**
     * Create a new enumeration generator.
     * @param enumClass Enumeration class.
     */
    public RandomEnumGenerator(final @NonNull Class<? extends Enum<?>> enumClass)
    {
        this.enumClass = enumClass;
        this.min = 0;
        this.max = enumClass.getEnumConstants().length - 1;
    }

    /**
     * Exclude a value from the generation of possible enumerated values.
     * @param value Enumerated value.
     * @return {@link RandomEnumGenerator}.
     */
    public RandomEnumGenerator exclude(final @NonNull Enum<?> value)
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
                value = enumClass.getEnumConstants()[ RandomNumberGenerator.nextInt(min, max) ];
                if (!excludes.contains(value))
                {
                    isValid = true;
                }
            }

            return value;
        }

        return enumClass.getEnumConstants()[ RandomNumberGenerator.nextInt(min, max) ];
    }
}
