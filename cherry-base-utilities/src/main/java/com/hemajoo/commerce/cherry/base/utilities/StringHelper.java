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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class providing services to ease <b>strings</b> manipulation.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class StringHelper
{
    /**
     * Convert a set of values from a string to a list of values.
     * @param values String containing the values separated by a special character.
     * @param separator Separator character.
     * @return List of values.
     */
    public static List<String> convertStringValuesAsList(final String values, final String separator)
    {
        if (values == null || values.isEmpty())
        {
            return new ArrayList<>();
        }

        return Arrays.stream(values.split(separator))
                .map(String::trim)
                .toList();
    }

    /**
     * Convert a list of values to a string with all values separated by a given separator character.
     * @param values List of values.
     * @param separator Separator character.
     * @return String with values separated by a separator character.
     */
    public static String convertListValuesAsString(final List<String> values, final String separator)
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
