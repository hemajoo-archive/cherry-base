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

import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.NonNull;

/**
 * Provides the basic behavior of a generic translation processor.
 * <br>
 * Generally, a concrete translation processor is dedicated to use a specific translation API such as:
 * <em>Google Free Translate API</em>
 * <em>Google Translate API</em>
 * <em>Azure Translate API</em>
 * <em>IBM Translate API</em>
 * <em>etc.</em>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see ITranslationResultSentence
 */
public interface ITranslationProcessor
{
    /**
     * Translates a translation process (with its underlying request) using this translation processor.
     * @param process Translation process.
     * @param entry Translation request entry to process.
     * @throws TranslationException Thrown in case an error occurred during translation.
     */
    void translate(final @NonNull ITranslationProcess process, final @NonNull ITranslationRequestEntry entry) throws TranslationException;
}
