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

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * A translation request entry.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationRequestEntry implements ITranslationRequestEntry
{
    /**
     * Key of the request entry.
     */
    @Getter
    private final String key;

    /**
     * Source of the request entry.
     */
    @Getter
    private final String source;

    /**
     * Translation of the request entry.
     */
    @Getter
    @Setter
    private String translation;

    /**
     * Does this request entry requires a translation?
     */
    private boolean requireTranslation;

    /**
     * Time stamp of the translation.
     */
    @Getter
    private LocalDateTime translationTimeStamp = null;

    /**
     * Result of the translation of the request entry.
     */
    @Getter
    private ITranslationResult result;

    /**
     * Create a translation request entry.
     * @param key Key.
     * @param source Source.
     * @param requireTranslation Does it require a translation?
     */
    public TranslationRequestEntry(final @NonNull String key, final @NonNull String source, final boolean requireTranslation)
    {
        this.key = key;
        this.source = source;
        this.requireTranslation = requireTranslation;
    }

    @Override
    public void setResult(@NonNull ITranslationResult result)
    {
        this.result = result;

        StringBuilder text = new StringBuilder();

        for (ITranslationResultSentence sentence : result.getSentences())
        {
            if (sentence.getTranslation() != null && !sentence.getTranslation().isEmpty())
            {
                text.append(sentence.getTranslation());
                requireTranslation = false;
                translationTimeStamp = LocalDateTime.now();
            }
        }

        // Remove null sentences (do not know why the free Google Translate API add some null sentences!
        result.getSentences().removeIf(s -> s.getOriginal() == null);

        translation = text.toString();
    }

    @Override
    public final boolean requireTranslation()
    {
        return requireTranslation;
    }
}
