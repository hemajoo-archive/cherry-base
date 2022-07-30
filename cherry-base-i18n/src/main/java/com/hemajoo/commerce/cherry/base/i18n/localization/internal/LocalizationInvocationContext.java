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
package com.hemajoo.commerce.cherry.base.i18n.localization.internal;

import com.hemajoo.commerce.cherry.base.i18n.localization.type.LocalizationInvocationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Localization context.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@NoArgsConstructor
public class LocalizationInvocationContext
{
    /**
     * Method having invoked the localization.
     */
    @Getter
    @Setter
    private Method method;

    /**
     * Localization method's annotation.
     */
    @Getter
    @Setter
    private Annotation methodAnnotation;

    /**
     * Method's interface.
     */
    @Getter
    @Setter
    private Class<?> methodInterface;

    /**
     * Declaring class.
     */
    @Getter
    @Setter
    private Class<?> declaringClass;

    /**
     * Declaring class's localization annotation.
     */
    @Getter
    @Setter
    private Annotation declaringClassAnnotation;

    /**
     * Instance class.
     */
    @Getter
    @Setter
    private Class<?> instanceClass;

    /**
     * Instance class's localization annotation.
     */
    @Getter
    @Setter
    private Annotation instanceClassAnnotation;

    /**
     * Field localization contexts.
     */
    @Getter
    private final Set<LocalizationFieldContext> fields = new HashSet<>();

    /**
     * Localization invocation type.
     */
    @Getter
    @Setter
    private LocalizationInvocationType invocationType;

    /**
     * Add a field localization context.
     * @param field Field localization context.
     */
    public void addField(final @NonNull LocalizationFieldContext field)
    {
        fields.add(field);
    }
}
