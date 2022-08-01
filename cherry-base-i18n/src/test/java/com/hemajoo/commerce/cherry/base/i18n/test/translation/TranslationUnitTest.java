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
package com.hemajoo.commerce.cherry.base.i18n.test.translation;

import com.hemajoo.commerce.cherry.base.commons.test.AbstractCherryUnitTest;
import com.hemajoo.commerce.cherry.base.i18n.translation.Translation;
import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A unit test class for testing the <b>Translation</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
class TranslationUnitTest extends AbstractCherryUnitTest
{
    /**
     * Testing purpose constant for a word in english.
     */
    private static final String TEST_ENGLISH_WORD = "Philosophy";

    /**
     * Testing purpose constant for a sentence in english.
     */
    private static final String TEST_ENGLISH_SENTENCE = "A spoken language is a language produced by articulate sounds, as opposed to a written language. An oral language or vocal language is a language produced with the vocal tract, as opposed to a sign language, which is produced with the hands and face.";

    @Test
    @DisplayName("Create a translation")
    void testCreateTranslation() throws TranslationException
    {
        // Create a translation using the no arg constructor.
        Translation empty = new Translation();

        assertThat(empty).isNotNull();
        assertThat(empty.getSourceLocale().getDisplayLanguage()).isEqualTo(Locale.ENGLISH.getDisplayLanguage());
        assertThat(empty.getTargetLocale().getDisplayLanguage()).isEqualTo(Locale.ENGLISH.getDisplayLanguage());
        assertThat(empty.getText()).isNull();
        assertThat(empty.getTranslated()).isNull();

        empty.setSourceLocale(Locale.GERMAN);
        empty.setTargetLocale(Locale.CHINESE);
        empty.setText(TEST_ENGLISH_SENTENCE);
        assertThat(empty.getTranslated()).isNull();
        assertThat(empty.getText()).isNotNull();
        assertThat(empty.getSourceLocale()).isEqualTo(Locale.GERMAN);
        assertThat(empty.getTargetLocale()).isEqualTo(Locale.CHINESE);

        // Create a translation using the builder.
        Translation translation = Translation.builder()
                .withSource(Locale.ENGLISH)
                .withTarget(Locale.FRENCH)
                .withText(TEST_ENGLISH_WORD)
                .build();

        assertThat(translation).isNotNull();
        assertThat(translation.getSourceLocale().getDisplayLanguage()).isEqualTo(Locale.ENGLISH.getDisplayLanguage());
        assertThat(translation.getTargetLocale().getDisplayLanguage()).isEqualTo(Locale.FRENCH.getDisplayLanguage());
        assertThat(translation.getText()).isNotNull();
        assertThat(translation.getTranslated()).isNull();
    }

    @Test
    @DisplayName("Translate a word")
    void testTranslateWord() throws TranslationException
    {
        // Direct translation
        String translated = Translation.translate(TEST_ENGLISH_WORD, Locale.ENGLISH, Locale.FRENCH);
        assertThat(translated).isNotNull();

        // Using the translation instance
        Translation translation = Translation.builder()
                .withSource(Locale.ENGLISH)
                .withTarget(Locale.FRENCH)
                .withText(TEST_ENGLISH_WORD)
                .build();

        assertThat(translated).isNotNull();
        translation.translate();
        assertThat(translation.getTranslated()).isNotNull();
    }

    @Test
    @DisplayName("Translate a sentence")
    void testTranslateSentence() throws TranslationException
    {
        // Direct translation
        String translated = Translation.translate(TEST_ENGLISH_SENTENCE, Locale.ENGLISH, Locale.FRENCH);
        assertThat(translated).isNotNull();

        // Using the translation instance
        Translation translation = Translation.builder()
                .withSource(Locale.ENGLISH)
                .withTarget(Locale.FRENCH)
                .withText(TEST_ENGLISH_SENTENCE)
                .build();

        assertThat(translated).isNotNull();
        translation.translate();
        assertThat(translation.getTranslated()).isNotNull();
    }

    @Test
    @DisplayName("Translate multiple Back To The Future quotes")
    void testTranslateQuotes() throws TranslationException
    {
        final int COUNT = 20;
        String translated;
        String original;

        // Translate using the static service
        for (int i = 0; i < COUNT; i++)
        {
            translated = null;
            original = FAKER.backToTheFuture().quote();
            translated = Translation.translate(original, Locale.ENGLISH, Locale.FRENCH);
            assertThat(translated).isNotNull();
        }
    }

    @Test
    @DisplayName("Translate multiple Chuck Norris quotes")
    void testTranslateChuckNorrisQuotes() throws TranslationException
    {
        String original;

        final int COUNT = 20;

        // Translate using the instance
        Translation translation = Translation.builder()
                .withSource(Locale.ENGLISH)
                .withTarget(Locale.FRENCH)
                .build();

        for (int i = 0; i < COUNT; i++)
        {
            original = FAKER.chuckNorris().fact();
            translation.setText(original);
            translation.translate();
            assertThat(translation).isNotNull();
        }
    }
}
