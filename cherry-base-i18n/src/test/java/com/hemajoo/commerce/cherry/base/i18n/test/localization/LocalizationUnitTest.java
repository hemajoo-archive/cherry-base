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
package com.hemajoo.commerce.cherry.base.i18n.test.localization;

import com.hemajoo.commerce.cherry.base.commons.exception.AnnotationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.I18nManager;
import com.hemajoo.commerce.cherry.base.i18n.localization.Localization;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.I18nException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.ResourceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ressec.avocado.core.exception.checked.StringExpanderException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test class for testing the <b>Localization</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */

class LocalizationUnitTest
{
    /**
     * Test resource bundle property key.
     */
    private static final String TEST_KEY_NAME = "com.hemajoo.commerce.cherry.base.i18n.test.highway.name";

    /**
     * Resource bundle pointing at: 'i18n/test'.
     */
    private static final String TEST_RESOURCE_BUNDLE = "i18n/test";

    /**
     * Resource bundle key for name of entry: 'highway'.
     */
    private static final String TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME = "com.hemajoo.commerce.cherry.base.i18n.test.highway.name";

    /**
     * Resource bundle key for description of entry: 'highway'.
     */
    private static final String TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_DESCRIPTION = "com.hemajoo.commerce.cherry.base.i18n.test.highway.description";

    /**
     * Non-existing resource bundle key.
     */
    private static final String TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_DOES_NOT_EXIST = "com.hemajoo.commerce.cherry.base.i18n.test.highway.does-not-exist";

    @Test
    @DisplayName("Create a localized resource")
    void testCreateLocalization() throws I18nException
    {
        Localization i18n;

        // Create an empty localization object using its builder.
        i18n = Localization.builder()
                .build();
        assertThat(i18n).isNotNull();

        i18n = Localization.builder()
                .withBundle(TEST_RESOURCE_BUNDLE)
                .build();
        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNotNull();

        i18n = Localization.builder()
                .withBundle(TEST_RESOURCE_BUNDLE)
                .withKey(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)
                .build();
        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNotNull();
        assertThat(i18n.getKey()).isNotNull();

        i18n = Localization.builder()
                .withBundle(TEST_RESOURCE_BUNDLE)
                .withKey(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)
                .withValue("Initial value")
                .build();
        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNotNull();
        assertThat(i18n.getKey()).isNotNull();
        assertThat(i18n.getValue()).isNotNull();
        assertThat(i18n.getValue()).isEqualTo("Initial value");

        i18n.localize();
        assertThat(i18n.getValue()).isNotNull();
        assertThat(i18n.getValue()).isNotEqualTo("Initial value");

        // Create an empty localization object using its no arg constructor.
        i18n = new Localization();
        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNull();
        assertThat(i18n.getKey()).isNull();
        assertThat(i18n.getValue()).isNull();
    }

    @Test
    @DisplayName("Load a resource bundle")
    void testLoadBundle() throws I18nException
    {
        I18nManager.getInstance().load("i18n/test");

        assertThat(Localization.asString(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)).isEqualTo("Highway");
    }

    @Test
    @DisplayName("clear all registered resource bundles")
    void testClearAllBundle()
    {
        I18nManager.getInstance().load("i18n/test");
        I18nManager.getInstance().clearAll();

        assertThrows(I18nException.class, () -> Localization.asString(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)); // No bundle registered!
    }

    @Test
    @DisplayName("Cannot localize a resource bundle key")
    void testCannotLocalize()
    {
        Localization i18n;

        i18n = Localization.builder()
                .withBundle(TEST_RESOURCE_BUNDLE)
                .build();
        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNotNull();

        assertThrows(I18nException.class, i18n::localize); // No key provided!
        assertThrows(I18nException.class, () -> i18n.localize(Locale.GERMAN)); // No key provided!
        assertThrows(I18nException.class, () -> Localization.from(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_DOES_NOT_EXIST)); // Key does not exist!
        assertThrows(I18nException.class, () -> Localization.from(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_DOES_NOT_EXIST, Locale.ITALIAN)); // Key does not exist!
    }

    @Test
    @DisplayName("Localize a resource bundle key as a localized resource")
    void testLocalize() throws I18nException
    {
        Localization i18n;

        i18n = Localization.builder()
                .withBundle(TEST_RESOURCE_BUNDLE)
                .withKey(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)
                .build();

        assertThat(i18n).isNotNull();
        assertThat(i18n.getBundle()).isNotNull();

        i18n.localize();
        assertThat(i18n.getValue()).isEqualTo("Highway");

        i18n.localize(Locale.FRENCH);
        assertThat(i18n.getValue()).isEqualTo("Autoroute");

        // Directly localize a resource bundle key
        i18n = Localization.from(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME, Locale.forLanguageTag("es"));
        assertThat(i18n.getValue()).isEqualTo("Autopista");
    }

    @Test
    @DisplayName("Localize a resource bundle key as a string")
    void testLocalizeAsString() throws I18nException
    {
        I18nManager.getInstance().load("i18n/test");

        assertThat(Localization.asString(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME)).isEqualTo("Highway");
        assertThat(Localization.asString(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME, Locale.FRENCH)).isEqualTo("Autoroute");
    }

    @Test
    @DisplayName("Localization as free text")
    void testLocalizationFreeText() throws I18nException
    {
        I18nManager.getInstance().load("i18n/test");

        Localization i18n = Localization.builder()
                .withValue("This is a test")
                .build();

        assertThat(i18n.getValue()).isNotNull();
        assertThat(i18n.getValue()).isEqualTo("This is a test");

        assertThrows(I18nException.class, i18n::localize); // No key provided!

        i18n.setKey(TEST_RESOURCE_BUNDLE_KEY_HIGHWAY_NAME);
        i18n.localize();

        assertThat(i18n.getValue()).isNotNull();
        assertThat(i18n.getValue()).isEqualTo("Highway");
    }

    @Test
    @DisplayName("Localize a quote")
    void testLocalizationQuote() throws LocalizationException, StringExpanderException, AnnotationException, ResourceException
    {
        I18nManager.getInstance().load("i18n/test");

        QuoteOfTheDay quote = QuoteOfTheDay.builder()
                .withNumber(2)
                .build();

        assertThat(quote).isNotNull();

        quote.localize(Locale.FRENCH);
        String localized = quote.getQuoteName();
        assertThat(localized).isNotNull();

    }

//    @Test
//    public final void testRopeTranslateText() throws TranslationException
//    {
//        // Set default locale to english
//        I18nManager.getInstance().setLocale(Locale.ENGLISH);
//
//        Localization text = Localization.valueOf("Un petit bout de texte à faire traduire dans une langue étrangère");
//        text.translate(LanguageType.FRENCH.getLocale(), LanguageType.HEBREW.getLocale());
//        Assert.assertNotNull(text.getValue());
//    }
}
