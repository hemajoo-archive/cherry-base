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

import com.google.gson.annotations.SerializedName;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.I18nException;
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
@NoArgsConstructor
@EqualsAndHashCode
public final class Localization implements Serializable
{
    /**
     * Localized value.
     */
    @SerializedName("string:value")
    @Getter
    @Setter
    private String value;

    /**
     * Original value.
     */
    @SerializedName("string:original")
    @Getter
    private String original;

    /**
     * Resource bundle pathname.
     */
    @SerializedName("string:bundle")
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private String bundle;

    /**
     * Resource bundle key.
     */
    @SerializedName("string:key")
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private String key;

    /**
     * Object reference.
     */
    @Getter
    @Setter
    private transient Object reference;

    /**
     * Previous localized value (used to avoid having to localized if already done).
     */
    @Getter
    @Setter
    private transient Locale previousLocalized;

    /**
     * Previous translated value (used to avoid having to translate if already done).
     */
    @Getter
    @Setter
    private transient Locale previousTranslated;

    /**
     * Create a new <b>localized</b> object.
     * @param bundle Resource bundle file path and name or <b>null</b> if object is to be used with a free text string.
     * @param key Resource bundle key or <b>null</b> if object is to be used as a free text string.
     * @param value Free text string (optional).
     */
    @Builder(setterPrefix = "with")
    public Localization(final String bundle, final String key, final String value)
    {
        this.bundle = bundle;
        this.key = key;
        this.value = value;

        if (bundle != null)
        {
            I18nManager.getInstance().load(bundle);
        }
    }

    /**
     * Create a localized string given a free text.
     * @param input Input text.
     * @return {@link Localization} object.
     */
    public static Localization valueOf(final @NonNull String input)
    {
        return new Localization(null, null, input);
    }

    /**
     * Create a localized string given a resource bundle property key (referencing the text to be localized).
     * @param key Resource bundle property key.
     * @return {@link Localization}.
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public static Localization from(final @NonNull String key) throws I18nException
    {
        Localization i18n = new Localization(null, key, null);
        i18n.localize();

        return i18n;
    }

    /**
     * Create a localized string given a resource bundle property key (referencing the text to be localized).
     * @param key Resource bundle property key.
     * @param locale Locale.
     * @return {@link Localization}.
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public static Localization from(final @NonNull String key, final @NonNull Locale locale) throws I18nException
    {
        Localization i18n = new Localization(null, key, null);
        i18n.localize(locale);

        return i18n;
    }

    /**
     * Return a localized string.
     * @param key Resource bundle property key.
     * @return Localized string.
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public static String asString(final @NonNull String key) throws I18nException
    {
        Localization i18n = new Localization(null, key, null);
        i18n.localize();

        return i18n.getValue();
    }

    /**
     * Return a localized string.
     * @param key Resource bundle property key.
     * @param locale Locale.
     * @return Localized string.
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public static String asString(final @NonNull String key, final @NonNull Locale locale) throws I18nException
    {
        Localization i18n = new Localization(null, key, null);
        i18n.localize(locale);

        return i18n.getValue();
    }

    /**
     * Localize the string (using the current {@link I18nManager} locale).
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public void localize() throws I18nException
    {
        Locale current = I18nManager.getInstance().getLocale();

        if (previousLocalized == null || !previousLocalized.equals(current))
        {
            I18nManager.getInstance().resolveI18nString(this, reference, current);
        }

        previousLocalized = current;
    }

    /**
     * Localize the string given a locale.
     * @param locale {@link Locale} to set.
     * @throws I18nException Thrown to indicate an error occurred during the localization process.
     */
    public void localize(final @NonNull Locale locale) throws I18nException
    {
        if (previousLocalized == null || !previousLocalized.equals(locale))
        {
            I18nManager.getInstance().resolveI18nString(this, reference, locale);
        }
        previousLocalized = locale;
    }

//    /**
//     * Translates a string from the given source language to the given target language.
//     * <hr>
//     * Be careful.. as this process involves a call to a remote service to translate the given text, it's a quite long
//     * process that could introduce latency in your application!
//     * @param source Source locale (language).
//     * @param target Target locale (language).
//     * @return {@code True} if the text has been translated, {@code false} otherwise.
//     * @throws TranslationException Thrown to indicate that an error occurred during a translation.
//     */
//    public final boolean translate(final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
//    {
//        if (previousTranslated == null || !previousTranslated.toLanguageTag().equals(target.toLanguageTag()))
//        {
//            if (original == null)
//            {
//                original = value;
//            }
//
//            GoogleTranslationResult result = I18nManager.getInstance().translate(this, source, target);
//            previousTranslated = target;
//            value = result.getTranslation();
//
//            log.info(String.format("Text to translate: [%s (%s)] -> [%s (%s)]",
//                    original, source.getDisplayLanguage(),
//                    value, target.getDisplayLanguage()));
//
//            return true;
//        }
//
//        return false;
//    }
}

