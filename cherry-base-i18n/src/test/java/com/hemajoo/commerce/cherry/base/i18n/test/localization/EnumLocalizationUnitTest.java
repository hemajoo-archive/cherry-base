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
    /**
     * Month 'March' in Italian.
     */
    public static final String MONTH_MARCH_ITALIAN = "Marzo";

    /**
     * Month 'March' in German.
     */
    public static final String MONTH_MARCH_GERMAN = "März";

    @Test
    void testMonthType() throws LocalizationException
    {
        assertThat(MonthType.MARCH.getName(Locale.ITALIAN)).isEqualTo(MONTH_MARCH_ITALIAN);
        assertThat(MonthType.JANUARY.getName(Locale.FRENCH)).isEqualTo("Janvier");
        assertThat(MonthType.DECEMBER.getName(Locale.GERMAN)).isEqualTo("Dezember");
        assertThat(MonthType.JULY.getName(Locale.forLanguageTag("es"))).isEqualTo("Julio");
    }

    @Test
    void testLanguageType() throws LocalizationException
    {
        LanguageType language = LanguageType.BULGARIAN;

        // Static services, by convention not prefixed by: get or set for static services
        assertThat(LanguageType.definition(LanguageType.BOSNIAN.getLocale())).isNotNull();


        assertThat(LanguageType.VIETNAMESE.getName(LanguageType.SPANISH.getLocale())).isNotNull();
        assertThat(LanguageType.HEBREW.getDescription(LanguageType.CHECHEN.getLocale())).isNotNull();

        I18nManager.getInstance().setLocale(Locale.GERMAN);
        assertThat(MonthType.MARCH.getName(Locale.ITALIAN)).isEqualTo(MONTH_MARCH_ITALIAN);
        assertThat(MonthType.MARCH.getName()).isEqualTo(MONTH_MARCH_GERMAN);
    }
}
