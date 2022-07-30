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
public interface LocalizeEnumAware extends Serializable
{
    /**
     * Return the enumerated value localized <b>name</b>.
     * @return Localized enumerated value name.
     */
    default String getName() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the enumerated value localized <b>name</b>.
     * @param locale Locale to use.
     * @return Localized enumerated value name.
     */
    default String getName(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }
}
