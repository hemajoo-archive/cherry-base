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
package com.hemajoo.commerce.cherry.base.utilities.helper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.exception.checked.StringExpanderException;
import org.ressec.avocado.core.helper.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing convenient services for manipulating variables contained in <b>strings</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class StringExpander
{
    /**
     * Extracts the variables found in the given text string (a variable has the format: <code>${variable_name})</code>.
     * @param text Text containing variables.
     * @return List of variable names or null if no variable has been found.
     */
    public static List<String> extractVariables(final @NonNull String text)
    {
        List<String> variables = new ArrayList<>();

        final String startPattern = "${";
        final String endPattern = "}";

        int index = -1;
        int start;
        int end;

        while (index < text.length())
        {
            start = text.indexOf(startPattern, index);
            if (start >= index)
            {
                end = text.indexOf(endPattern, start);
                if (end > start)
                {
                    variables.add(text.substring(start + startPattern.length(), end));
                }

                index = end;
            }
            else
            {
                index = text.length();
            }
        }

        return variables;
    }

    /**
     * Returns if the given text contains variables.
     * @param text Text to analyze.
     * @return True if the given text contains some variables, false otherwise.
     */
    public static boolean containsVariable(final @NonNull String text)
    {
        final String startPattern = "${";

        int index = text.indexOf(startPattern);

        return index >= 0;
    }

    /**
     * Expands/replaces variables with real values in the given text.
     * @param instance Object instance containing the real values.
     * @param text Text containing the variables to be replaced/expanded by real variable values.
     * @return Expanded text.
     * @throws StringExpanderException Thrown to indicate an error occurred while trying to expand a string.
     */
    public static String expandVariables(final @NonNull Object instance, final @NonNull String text) throws StringExpanderException
    {
        Object value;
        Field field;
        String result = text;

        if (!containsVariable(text))
        {
            return text;
        }

        for (String name : extractVariables(text))
        {
            if (name.equals("this") && instance.getClass().isEnum())
            {
                result = StringExpander.expandVariable(result, name, ((Enum<?>) instance).name());
            }
            else
            {
                try
                {
                    field = ReflectionHelper.findFieldInObjectInstance(instance, name);
                    if (!field.canAccess(instance))
                    {
                        field.setAccessible(true);
                    }
                    value = field.get(instance);
                    if (value.getClass().isEnum())
                    {
                        value = ((Enum<?>) field.get(instance)).name();
                    }
                    result = StringExpander.expandVariable(result, name, (String) value);
                }
                catch (NoSuchFieldException | IllegalAccessException e)
                {
                    throw new StringExpanderException(e);
                }
            }
        }

        return result;
    }

    /**
     * Expands the given variable by the given value in the given text.
     * @param source Source containing the variable to expand (replaced by the value).
     * @param variable Variable to expand.
     * @param value Value to use to replace the variable in the source text.
     * @return Expanded text.
     */
    private static String expandVariable(final @NonNull String source, final @NonNull String variable, final @NonNull String value)
    {
        String pattern = ("${" + variable + "}");
        return source.replace(pattern, value);
    }

    /**
     * Returns the field name given its getter method name.
     * @param getterName Getter method name.
     * @return Field nane.
     */
    public static String getFieldNameFromGetter(final @NonNull String getterName)
    {
        String result = getterName;

        int index;

        index = result.indexOf("get");
        if (index < 0)
        {
            index = result.indexOf("is");
            if (index < 0)
            {
                return null;
            }
            else
            {
                result = result.replaceFirst("is", "");
            }
        }
        else
        {
            result = result.replaceFirst("get", "");
        }

        result = result.substring(0, 1).toLowerCase() + result.substring(1);

        return result;
    }
}
