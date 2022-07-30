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
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Log4j2
@NoArgsConstructor
public class LocalizationInvocationContext
{
    @Getter
    @Setter
    private Method method;

    @Getter
    @Setter
    private Annotation methodAnnotation;

    @Getter
    @Setter
    private Class<?> methodInterface;

    @Getter
    @Setter
    private Class<?> declaringClass;

    @Getter
    @Setter
    private Annotation declaringClassAnnotation;

    @Getter
    @Setter
    private Class<?> instanceClass;

    @Getter
    @Setter
    private Annotation instanceClassAnnotation;

    @Getter
    @Setter
    private Field field;

    @Getter
    @Setter
    private LocalizationInvocationType invocationType;
}
