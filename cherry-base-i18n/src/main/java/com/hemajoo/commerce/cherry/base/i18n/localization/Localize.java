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
import com.hemajoo.commerce.cherry.base.i18n.localization.type.LanguageType;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Locale;

/**
 * Entities implementing this interface gain the ability to have their elements annotated with the <b>I18n</b> annotation automatically localized.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface Localize extends Serializable
{
    /**
     * Localize fields annotated with the <b>I18n</b> annotation.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize an element.
     */
    default void localize() throws LocalizationException
    {
        I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Localize fields annotated with the <b>I18n</b> annotation.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize an element.
     */
    default void localize(final @NonNull Locale locale) throws LocalizationException
    {
        I18nManager.getInstance().localize(this, locale);
    }

    /**
     * Localize fields annotated with the <b>I18n</b> annotation.
     * @param language Language.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize an element.
     */
    default void localize(final LanguageType language) throws LocalizationException
    {
        localize(language.getLocale());
    }
}
