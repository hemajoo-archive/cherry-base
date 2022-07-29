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
package com.hemajoo.commerce.cherry.base.i18n.translation;

import com.google.gson.annotations.SerializedName;
import com.hemajoo.commerce.cherry.base.i18n.localization.I18nManager;
import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Locale;

/**
 * Entity providing services for a string to be easily localized (see: i18n, g11n, L10n).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@EqualsAndHashCode
public final class Translation implements Serializable
{
    /**
     * Original text.
     */
    @SerializedName("string:text")
    @Getter
    @Setter
    private String text;

    /**
     * Translated text.
     */
    @SerializedName("string:translated")
    @Getter
    @Setter
    private String translated;

    @Getter
    @Setter
    private Locale sourceLocale;

    @Getter
    @Setter
    private Locale targetLocale;

    private boolean isTranslated;

    /**
     * Previous original text.
     */
    private transient String previousText;

    /**
     * Locale used for a previous translation.
     */
    private transient Locale previousLocale;

    public Translation()
    {
        setSourceLocale(Locale.ENGLISH);
        setTargetLocale(Locale.ENGLISH);
    }

    /**
     * Create a new <b>localized</b> object.
     * @param text Text to be translated.
     * @param source Source locale. If not specified or set to <b>null</b>, {@link Locale#ENGLISH} is considered the default source locale.
     * @param target Target locale. If not specified or set to <b>null</b>, {@link Locale#ENGLISH} is considered the default source locale.
     */
    @Builder(setterPrefix = "with")
    public Translation(final String text, final Locale source, final Locale target)
    {
        this.text = text;
        this.sourceLocale = source != null ? source : Locale.ENGLISH;
        this.targetLocale = target != null ? target : Locale.ENGLISH;
    }

    public static String translate(final @NonNull String text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
    {
        Translation translation = Translation.builder()
                .withSource(source)
                .withTarget(target)
                .withText(text)
                .build();

        translation.translate();

        return translation.getTranslated();
    }

    /**
     * Translate a text from a given source language to a target language.
     * <hr>
     * Be careful as this process involves invocation of a remote service to translate the given text, it's quite a long process that could introduce latency in your application!
     * @return <b>True</b> if the text has been translated, <b>false</b> otherwise.
     * @throws TranslationException Thrown to indicate that an error occurred during a translation.
     */
    public boolean translate() throws TranslationException
    {
        return translate(sourceLocale, targetLocale);
    }

    /**
     * Translate a text from a given source language to a target language.
     * <hr>
     * Be careful as this process involves invocation of a remote service to translate the given text, it's quite a long process that could introduce latency in your application!
     * @param source Source locale.
     * @param target Target locale.
     * @return <b>True</b> if the text has been translated, <b>false</b> otherwise.
     * @throws TranslationException Thrown to indicate that an error occurred during a translation.
     */
    public boolean translate(final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
    {
        isTranslated = false;

        if (previousText == null || !previousText.equals(text))
        {
            // No previous text to translate or previous text is different from new one to translate.
            previousText = text;
            translated = I18nManager.getInstance().translate(this, source, target);
            previousLocale = target;
            setTranslated(translated);
            isTranslated = true;

            return true;
        }
        else
        {
            // Text to translate is same as previous one, are the locale different?
            if (previousLocale == null || !previousLocale.toLanguageTag().equals(target.toLanguageTag()))
            {
                if (text == null || text.isBlank())
                {
                    previousText = text;
                    setTranslated(text);
                    previousLocale = target;
                    isTranslated = true;

                    return true;
                }

                previousText = text;
                translated = I18nManager.getInstance().translate(this, source, target);
                previousLocale = target;
                setTranslated(translated);
                isTranslated = true;

                return true;
            }
        }

        return false;
    }
}

