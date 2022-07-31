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

import com.hemajoo.commerce.cherry.base.i18n.localization.I18nManager;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.type.LanguageType;
import com.hemajoo.commerce.cherry.base.i18n.localization.type.time.MonthType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for unit testing the localization on enum types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest
class EnumLocalizationUnitTest
{
    @Test
    void testLocalizeMonth() throws LocalizationException
    {
        assertThat(MonthType.MARCH.getName(Locale.ITALIAN)).isEqualTo("Marzo");
        assertThat(MonthType.JANUARY.getName(Locale.FRENCH)).isEqualTo("Janvier");
        assertThat(MonthType.DECEMBER.getName(Locale.GERMAN)).isEqualTo("Dezember");
        assertThat(MonthType.JULY.getName(Locale.forLanguageTag("es"))).isEqualTo("Julio");
    }

    @Test
    void testLocalizeLanguage() throws LocalizationException
    {
        LanguageType language = LanguageType.BULGARIAN;
        String description = language.getDescription(Locale.ITALIAN);
        String name = language.getName(Locale.FRENCH);

        I18nManager.getInstance().setLocale(Locale.GERMAN);
        String definition = LanguageType.getDefinition();

        assertThat(MonthType.MARCH.getName(Locale.ITALIAN)).isEqualTo("Marzo");

        System.out.println(LanguageType.HEBREW.getName(Locale.FRENCH));
        System.out.println(LanguageType.HEBREW.getDescription(Locale.FRENCH));
    }
}
