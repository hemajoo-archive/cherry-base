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
package com.hemajoo.commerce.cherry.base.i18n.translation.engine;

import com.hemajoo.commerce.cherry.base.i18n.translation.Translation;
import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.NonNull;

import java.util.Locale;

/**
 * Interface defining the behavior of a translator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ITranslator
{
    /**
     * Translate some text.
     * @param text Text to translate.
     * @param source Source locale.
     * @param target Target locale.
     * @return Translated text.
     * @throws TranslationException Thrown to indicate an error occurred while trying to translate some text.
     */
    String translate(final @NonNull Translation text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException;
}
