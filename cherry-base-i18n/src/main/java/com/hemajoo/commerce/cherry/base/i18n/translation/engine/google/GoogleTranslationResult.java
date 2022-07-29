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
package com.hemajoo.commerce.cherry.base.i18n.translation.engine.google;

import com.google.gson.annotations.SerializedName;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.ITranslationResult;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.ITranslationResultSentence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@code Google Free API} translation result.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class GoogleTranslationResult implements ITranslationResult
{
    /**
     * Result translation of sentences.
     */
    @Getter
    @Setter
    @SerializedName("sentences")
    private List<ITranslationResultSentence> sentences;

    /**
     * Source of the translation.
     */
    @Getter
    @Setter
    @SerializedName("src")
    private String source;

    /**
     * Confidence factor of the translation.
     */
    @Getter
    @Setter
    @SerializedName("confidence")
    private double confidence;

    /**
     * Creates a new Google translation result.
     */
    public GoogleTranslationResult()
    {
        // Required for JSON serialization.
    }

    /**
     * Return the translation result.
     * @return Translation result.
     */
    public final String getTranslation()
    {
        String result = sentences.stream()
                .map(ITranslationResultSentence::getTranslation)
                .collect( Collectors.joining(""));

        return result.replace("null", "");
    }

    @Override
    public TranslationProviderType getProviderType()
    {
        return TranslationProviderType.GOOGLE_FREE_TRANSLATE_API;
    }
}
