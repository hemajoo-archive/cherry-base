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
package com.hemajoo.commerce.cherry.base.i18n.localization.type;

import com.hemajoo.commerce.cherry.base.i18n.localization.I18nManager;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LanguageException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * An internationalized enumeration representing the available languages.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see I18n
 */
public enum LanguageType
{
    /**
     * French language.
     */
    FRENCH(Locale.forLanguageTag("fr")),

    /**
     * English language.
     */
    ENGLISH(Locale.forLanguageTag("en")),

    /**
     * German language.
     */
    GERMAN(Locale.forLanguageTag("de")),

    /**
     * Italian language.
     */
    ITALIAN(Locale.forLanguageTag("it")),

    /**
     * Spanish language.
     */
    SPANISH(Locale.forLanguageTag("es")),

    /**
     * Arabic language.
     */
    ARABIC(Locale.forLanguageTag("ar")),

    /**
     * Armenian language.
     */
    ARMENIAN(Locale.forLanguageTag("hy")),

    /**
     * Bosnian language.
     */
    BOSNIAN(Locale.forLanguageTag("bs")),

    /**
     * Bulgarian language.
     */
    BULGARIAN(Locale.forLanguageTag("bg")),

    /**
     * Chechen language.
     */
    CHECHEN(Locale.forLanguageTag("ce")),

    /**
     * Chinese language.
     */
    CHINESE(Locale.forLanguageTag("zh")),

    /**
     * Croatian language.
     */
    CROATIAN(Locale.forLanguageTag("hr")),

    /**
     * Czech language.
     */
    CZECH(Locale.forLanguageTag("cs")),

    /**
     * Danish language.
     */
    DANISH(Locale.forLanguageTag("da")),

    /**
     * Dutch language.
     */
    DUTCH(Locale.forLanguageTag("nl")),

    /**
     * Estonian language.
     */
    ESTONIAN(Locale.forLanguageTag("et")),

    /**
     * Georgian language.
     */
    GEORGIAN(Locale.forLanguageTag("ka")),

    /**
     * Greek language.
     */
    GREEK(Locale.forLanguageTag("el")),

    /**
     * Hebrew language.
     */
    HEBREW(Locale.forLanguageTag("he")),

    /**
     * Hindi language.
     */
    HINDI(Locale.forLanguageTag("hi")),

    /**
     * Hungarian language.
     */
    HUNGARIAN(Locale.forLanguageTag("hu")),

    /**
     * Icelandic language.
     */
    ICELANDIC(Locale.forLanguageTag("is")),

    /**
     * Indonesian language.
     */
    INDONESIAN(Locale.forLanguageTag("id")),

    /**
     * Japanese language.
     */
    JAPANESE(Locale.forLanguageTag("ja")),

    /**
     * Korean language.
     */
    KOREAN(Locale.forLanguageTag("ko")),

    /**
     * Latvian language.
     */
    LATVIAN(Locale.forLanguageTag("lv")),

    /**
     * Lithuanian language.
     */
    LITHUANIAN(Locale.forLanguageTag("lt")),

    /**
     * Malay language.
     */
    MALAY(Locale.forLanguageTag("ms")),

    /**
     * Mongolian language.
     */
    MONGOLIAN(Locale.forLanguageTag("mn")),

    /**
     * Norwegian language.
     */
    NORWEGIAN(Locale.forLanguageTag("no")),

    /**
     * Polish language.
     */
    POLISH(Locale.forLanguageTag("pl")),

    /**
     * Portuguese language.
     */
    PORTUGUESE(Locale.forLanguageTag("pt")),

    /**
     * Russian language.
     */
    RUSSIAN(Locale.forLanguageTag("ru")),

    /**
     * Serbian language.
     */
    SERBIAN(Locale.forLanguageTag("sr")),

    /**
     * Slovak language.
     */
    SLOVAK(Locale.forLanguageTag("sk")),

    /**
     * Slovenian language.
     */
    SLOVENIAN(Locale.forLanguageTag("sl")),

    /**
     * Swedish language.
     */
    SWEDISH(Locale.forLanguageTag("sv")),

    /**
     * Thai language.
     */
    THAI(Locale.forLanguageTag("th")),

    /**
     * Turkish language.
     */
    TURKISH(Locale.forLanguageTag("tr")),

    /**
     * Ukrainian language.
     */
    UKRAINIAN(Locale.forLanguageTag("uk")),

    /**
     * Vietnamese language.
     */
    VIETNAMESE(Locale.forLanguageTag("vi"));

    /**
     * Resource bundle file.
     */
    private static final transient String RESOURCE_BUNDLE_FILE = "i18n/iso/639/language";

    /**
     * Language locale.
     */
    @Getter
    private final Locale locale;

    /**
     * Creates a {@link LanguageType} entity given its ISO code (2 letters).
     * @param isoCode Language ISO code (2 letters).
     * @return {@link LanguageType}.
     * @throws LanguageException Thrown to indicate that an error occurred while manipulating a language.
     */
    public static LanguageType from(final @NonNull String isoCode) throws LanguageException
    {
        Optional<LanguageType> result = Arrays.stream(LanguageType.values())
                .filter(v -> v.getLocale().toLanguageTag().equals(isoCode)).findAny();

        if (result.isEmpty())
        {
            throw new LanguageException(String.format("Cannot find a language with ISO code (2 letters): '%s'", isoCode));
        }

        return result.get();
    }

    /**
     * Creates a new language type given its locale.
     * @param locale Language locale.
     */
    LanguageType(final @NonNull Locale locale)
    {
        this.locale = locale;
    }

    /**
     * Returns the language name using the current locale set.
     * @return Localized language name.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.name")
    public final String getName() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Returns the language name using the given locale.
     * @param locale Locale.
     * @return Localized language name.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.name")
    public final String getName(final @NonNull Locale locale) throws LocalizationException
    {
        I18nManager.getInstance().localize(this, locale);

        return null;
    }

    /**
     * Returns the language description using the current locale set.
     * @return Localized language description.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.definition")
    public final String getDescription() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Returns the language description using the given locale.
     * @param locale Locale.
     * @return Localized language description.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.definition")
    public final String getDescription(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.term.definition")
    public static String getDefinition() throws LocalizationException
    {
        return I18nManager.getInstance().localize(I18nManager.getInstance().getLocale());
    }
}
