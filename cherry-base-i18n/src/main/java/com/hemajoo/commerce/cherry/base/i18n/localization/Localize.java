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

import com.hemajoo.commerce.cherry.base.commons.exception.AnnotationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.ResourceException;
import com.hemajoo.commerce.cherry.base.utilities.helper.StringExpanderException;
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
     * Localizes all entities (annotated fields and annotated methods) implementing the {@link Localize} interface
     * using the locale set in {@link I18nManager}.
     * @return Locale used to realize the localization.
     */
    default Locale localize() throws LocalizationException, StringExpanderException, AnnotationException, ResourceException
    {
        I18nManager.getInstance().resolveIndirect(this, I18nManager.getInstance().getLocale());
        return I18nManager.getInstance().getLocale();
    }

    /**
     * Localizes all entities (annotated fields and annotated methods) implementing the {@link Localize} interface
     * using the provided locale.
     * @param locale Locale.
     * @return Locale used to realize the localization.
     */
    default Locale localize(final @NonNull Locale locale) throws LocalizationException, StringExpanderException, AnnotationException, ResourceException
    {
        I18nManager.getInstance().resolveIndirect(this, locale);
        return locale;
    }

    /**
     * Localizes all entities (annotated fields and annotated methods) implementing the {@link Localize} interface
     * using the provided locale.
     * @param locale Locale.
     * @param previous Previous locale used for a localization.
     * @return Locale used to realize the localization.
     */
    default Locale localize(final @NonNull Locale locale, final @NonNull Locale previous) throws LocalizationException, StringExpanderException, AnnotationException, ResourceException
    {
        if (!locale.equals(previous))
        {
            I18nManager.getInstance().resolveIndirect(this, locale);
        }

        return locale;
    }
}
