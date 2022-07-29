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
package com.hemajoo.commerce.cherry.base.i18n.localization;

import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Locale;

/**
 * Enumerations implementing this interface gain the ability to have their enumerated values automatically localized through invocation of the <b>getName</b> services.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface LocalizeEnum extends Serializable
{
    /**
     * Return the enumerated value localized name.
     * @return Localized enumerated value name.
     */
//    default String getName() throws StringExpanderException, AnnotationException, ResourceException
    default String getName() throws LocalizationException
    {
        return I18nManager.getInstance().resolveEnum(this, I18nManager.getInstance().getLocale());
        //return I18nManager.getInstance().resolveDirect(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the enumerated value localized name.
     * @param locale Locale to use for localization resolving.
     * @return Localized enumerated value name.
     */
    default String getName(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().resolveEnum(this, locale);
        //return I18nManager.getInstance().resolveDirect(this, locale);
    }
}
