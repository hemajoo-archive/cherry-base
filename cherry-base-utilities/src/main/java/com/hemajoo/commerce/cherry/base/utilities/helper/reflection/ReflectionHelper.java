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
package com.hemajoo.commerce.cherry.base.utilities.helper.reflection;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing convenient services for reflection manipulations.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class ReflectionHelper
{
    /**
     * Finds a list of fields in a class hierarchy annotated with the given annotation.
     * @param type Class in which to start looking for annotated fields.
     * @param annotationType Annotation type.
     * @return List of annotated fields.
     */
    public static List<Field> findAnnotatedFieldsInClassHierarchy(final @NonNull Class<?> type, final @NonNull Class<? extends Annotation> annotationType)
    {
        List<Field> fields = new ArrayList<Field>();

        for (Field field : type.getDeclaredFields())
        {
            if (field.isAnnotationPresent(annotationType))
            {
                fields.add(field);
            }
        }

        if (type.getSuperclass() != null)
        {
            fields.addAll(findAnnotatedFieldsInClassHierarchy(type.getSuperclass(), annotationType));
        }

        return fields;
    }

    /**
     * Finds the field with the given name from the given object instance.
     * <br>
     * If necessary this method traverses the class hierarchy to find the given field.
     * @param instance Object instance in which to find the field.
     * @param name Field name.
     * @return Field if found.
     * @throws NoSuchFieldException Thrown in case no such field has been found.
     */
    public static Field findFieldInObjectInstance(final @NonNull Object instance, final @NonNull String name) throws NoSuchFieldException
    {
        return findFieldInClassHierarchy(instance.getClass(), name);
    }

    /**
     * Finds the field with the given field name from the given class type.
     * <br>
     * If necessary this method traverses the class hierarchy to find the given field.
     * @param type Class in which to find the field.
     * @param name Field name.
     * @return Field if found.
     * @throws NoSuchFieldException Thrown in case no such field has been found.
     */
    public static Field findFieldInClassHierarchy(final @NonNull Class<?> type, final @NonNull String name) throws NoSuchFieldException
    {
        for (Field field : type.getDeclaredFields())
        {
            if (field.getName().equals(name))
            {
                return field;
            }
        }

        if (type.getSuperclass() != null)
        {
            return findFieldInClassHierarchy(type.getSuperclass(), name);
        }

        throw new NoSuchFieldException(String.format("Cannot find field name: '%s' annotated with I18n annotation in hierarchy of class: '%s'", name, type.getName()));
    }
}
