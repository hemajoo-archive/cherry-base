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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Helper class providing convenient services for manipulating annotations.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class AnnotationHelper
{
    /**
     * Changes the value of an annotation property.
     * @param annotation Annotation to change.
     * @param key Key of the annotation property.
     * @param value New value of the property.
     * @return Old property value.
     */
    public static Object changeAnnotationValue(final @NonNull Annotation annotation, final @NonNull String key, final @NonNull Object value)
    {
        Field field;
        Map<String, Object> memberValues;

        Object handler = Proxy.getInvocationHandler(annotation);

        try
        {
            field = handler.getClass().getDeclaredField("memberValues");
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            throw new IllegalStateException(e);
        }

        field.setAccessible(true);

        try
        {
            memberValues = (Map<String, Object>) field.get(handler);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            throw new IllegalStateException(e);
        }

        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != value.getClass())
        {
            throw new IllegalArgumentException();
        }

        memberValues.put(key,value);

        return oldValue;
    }
}
