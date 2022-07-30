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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Field localization context.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
public class LocalizationFieldContext
{
    /**
     * Field.
     */
    @Getter
    @Setter
    private Field field;

    /**
     * Field's localization annotation.
     */
    @Getter
    @Setter
    private Annotation fieldAnnotation;

    /**
     * Create a field localization context.
     * @param field Field.
     * @param annotation Localization annotation.
     */
    public LocalizationFieldContext(final @NonNull Field field, final @NonNull Annotation annotation)
    {
        this.field = field;
        this.fieldAnnotation = annotation;
    }
}
