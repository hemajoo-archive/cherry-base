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
package com.hemajoo.commerce.cherry.base.utilities;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class providing services to ease <b>strings</b> manipulation.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class StringHelper
{
    /**
     * Convert a set of values from a string to a set of values.
     * @param values String containing the values separated by a special character.
     * @param separator Separator character.
     * @return Set of values.
     */
    public static Set<String> convertStringValuesAsSet(final String values, final String separator)
    {
        if (values == null || values.isEmpty())
        {
            return new HashSet<>();
        }

        return Arrays.stream(values.split(separator))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    /**
     * Convert a set of values to a string with all values separated by a given separator character.
     * @param values Set of values.
     * @param separator Separator character.
     * @return String with values separated by a separator character.
     */
    public static String convertSetValuesAsString(final Set<String> values, final String separator)
    {
        StringBuilder builder = new StringBuilder();

        for (String tag : values)
        {
            if (builder.length() == 0)
            {
                builder.append(tag);
            }
            else
            {
                builder.append(separator).append(" ").append(tag);
            }
        }

        return builder.toString();
    }

}
