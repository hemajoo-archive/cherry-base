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
 * Enumeration providing values for a <b>language</b> type.
 * <br><br>
 * This enumeration provides localized services for:<br>
 * <ul>
 *    <li>language name (getName)</li>
 *    <li>language description (getDescription)</li>
 * </ul>
 * Also provides localized static service(s) for:
 * <ul>
 *     <li>language term definition (definition)</li>
 * </ul>
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
     * Create a <b>language type</b>.
     * @param isoCode Language ISO Alpha-2 code (2 letters).
     * @return {@link LanguageType} representing the language.
     * @throws LanguageException Thrown to indicate that an error occurred while trying to create the language type.
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
     * Create a <b>language type</b>.
     * @param locale Locale.
     */
    LanguageType(final @NonNull Locale locale)
    {
        this.locale = locale;
    }

    /**
     * Return the localized name of the language.
     * @return Language name.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.name")
    public String getName() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the localized language name.
     * @param locale Locale.
     * @return Language name.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.name")
    public String getName(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }

    /**
     * Return the localized language description.
     * @return Language description.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.definition")
    public String getDescription() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the localized language description.
     * @param locale Locale.
     * @return Language description.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.${this}.definition")
    public String getDescription(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }

    /**
     * Return the localized definition of the term <b>language</b>.
     * @return Language term definition.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public static String definition() throws LocalizationException
    {
        return LanguageType.ENGLISH.getDefinition();
    }

    /**
     * Return the localized definition of the term <b>language</b>.
     * @param locale Locale.
     * @return Localization.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public static String definition(final @NonNull Locale locale) throws LocalizationException
    {
        return LanguageType.ENGLISH.getDefinition(locale);
    }

    /**
     * Return the definition of the term <b>language</b>.
     * @return Localization.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.term.definition")
    public String getDefinition() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the definition of the term <b>language</b>.
     * @param locale Locale.
     * @return Localization.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = LanguageType.RESOURCE_BUNDLE_FILE, key = "language.term.definition")
    public String getDefinition(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }
}
