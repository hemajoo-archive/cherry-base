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
import com.hemajoo.commerce.cherry.base.i18n.localization.type.time.MonthType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for unit testing the localized {@link MonthType} enumeration.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class I18nMonthUnitTest
{
    private static I18nManager i18n;

    @BeforeAll
    public static void beforeAll()
    {
        i18n = I18nManager.getInstance();
        i18n.setLocale(Locale.GERMAN);
    }

    @Test
    void testLocalizeMonth() throws LocalizationException
    {
        assertThat(MonthType.JANUARY.getName()).isEqualTo("januar");
        assertThat(MonthType.JANUARY.getName(Locale.FRENCH)).isEqualTo("Janvier");

//        Assert.assertEquals("janvier", MonthType.JANUARY.getName(Locale.FRENCH));
//        Assert.assertEquals("gennaio",MonthType.JANUARY.getName(Locale.ITALIAN));
//        Assert.assertEquals("january",MonthType.JANUARY.getName(Locale.ENGLISH));
//        Assert.assertEquals("januar",MonthType.JANUARY.getName(Locale.GERMAN));
//        Assert.assertEquals("enero",MonthType.JANUARY.getName(new Locale("es")));
    }

//    /**
//     * Test the retrieval of {@link MonthType#FEBRUARY} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeFebruary()
//    {
//        Assert.assertEquals("février", MonthType.FEBRUARY.getName(Locale.FRENCH));
//        Assert.assertEquals("febbraio",MonthType.FEBRUARY.getName(Locale.ITALIAN));
//        Assert.assertEquals("february",MonthType.FEBRUARY.getName(Locale.ENGLISH));
//        Assert.assertEquals("februar",MonthType.FEBRUARY.getName(Locale.GERMAN));
//        Assert.assertEquals("febrero",MonthType.FEBRUARY.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#MARCH} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeMarch()
//    {
//        Assert.assertEquals("mars", MonthType.MARCH.getName(Locale.FRENCH));
//        Assert.assertEquals("marzo",MonthType.MARCH.getName(Locale.ITALIAN));
//        Assert.assertEquals("march",MonthType.MARCH.getName(Locale.ENGLISH));
//        Assert.assertEquals("märz",MonthType.MARCH.getName(Locale.GERMAN));
//        Assert.assertEquals("marzo",MonthType.MARCH.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#APRIL} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeApril()
//    {
//        Assert.assertEquals("avril", MonthType.APRIL.getName(Locale.FRENCH));
//        Assert.assertEquals("aprile",MonthType.APRIL.getName(Locale.ITALIAN));
//        Assert.assertEquals("april",MonthType.APRIL.getName(Locale.ENGLISH));
//        Assert.assertEquals("april",MonthType.APRIL.getName(Locale.GERMAN));
//        Assert.assertEquals("abril",MonthType.APRIL.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#MAY} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeMay()
//    {
//        Assert.assertEquals("mai", MonthType.MAY.getName(Locale.FRENCH));
//        Assert.assertEquals("maggio",MonthType.MAY.getName(Locale.ITALIAN));
//        Assert.assertEquals("may",MonthType.MAY.getName(Locale.ENGLISH));
//        Assert.assertEquals("mai",MonthType.MAY.getName(Locale.GERMAN));
//        Assert.assertEquals("mayo",MonthType.MAY.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#JUNE} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeJune()
//    {
//        Assert.assertEquals("juin", MonthType.JUNE.getName(Locale.FRENCH));
//        Assert.assertEquals("giugno",MonthType.JUNE.getName(Locale.ITALIAN));
//        Assert.assertEquals("june",MonthType.JUNE.getName(Locale.ENGLISH));
//        Assert.assertEquals("juni",MonthType.JUNE.getName(Locale.GERMAN));
//        Assert.assertEquals("junio",MonthType.JUNE.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#JULY} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeJuly()
//    {
//        Assert.assertEquals("juillet", MonthType.JULY.getName(Locale.FRENCH));
//        Assert.assertEquals("luglio",MonthType.JULY.getName(Locale.ITALIAN));
//        Assert.assertEquals("july",MonthType.JULY.getName(Locale.ENGLISH));
//        Assert.assertEquals("juli",MonthType.JULY.getName(Locale.GERMAN));
//        Assert.assertEquals("julio",MonthType.JULY.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#AUGUST} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeAugust()
//    {
//        Assert.assertEquals("août", MonthType.AUGUST.getName(Locale.FRENCH));
//        Assert.assertEquals("agosto",MonthType.AUGUST.getName(Locale.ITALIAN));
//        Assert.assertEquals("august",MonthType.AUGUST.getName(Locale.ENGLISH));
//        Assert.assertEquals("august",MonthType.AUGUST.getName(Locale.GERMAN));
//        Assert.assertEquals("agosto",MonthType.AUGUST.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#SEPTEMBER} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeSeptember()
//    {
//        Assert.assertEquals("septembre", MonthType.SEPTEMBER.getName(Locale.FRENCH));
//        Assert.assertEquals("settembre",MonthType.SEPTEMBER.getName(Locale.ITALIAN));
//        Assert.assertEquals("september",MonthType.SEPTEMBER.getName(Locale.ENGLISH));
//        Assert.assertEquals("september",MonthType.SEPTEMBER.getName(Locale.GERMAN));
//        Assert.assertEquals("septiembre",MonthType.SEPTEMBER.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#OCTOBER} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeOctober()
//    {
//        Assert.assertEquals("octobre", MonthType.OCTOBER.getName(Locale.FRENCH));
//        Assert.assertEquals("ottobre",MonthType.OCTOBER.getName(Locale.ITALIAN));
//        Assert.assertEquals("october",MonthType.OCTOBER.getName(Locale.ENGLISH));
//        Assert.assertEquals("oktober",MonthType.OCTOBER.getName(Locale.GERMAN));
//        Assert.assertEquals("octubre",MonthType.OCTOBER.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#NOVEMBER} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeNovember()
//    {
//        Assert.assertEquals("novembre", MonthType.NOVEMBER.getName(Locale.FRENCH));
//        Assert.assertEquals("novembre",MonthType.NOVEMBER.getName(Locale.ITALIAN));
//        Assert.assertEquals("november",MonthType.NOVEMBER.getName(Locale.ENGLISH));
//        Assert.assertEquals("november",MonthType.NOVEMBER.getName(Locale.GERMAN));
//        Assert.assertEquals("noviembre",MonthType.NOVEMBER.getName(new Locale("es")));
//    }
//
//    /**
//     * Test the retrieval of {@link MonthType#DECEMBER} in several languages.
//     */
//    @Test
//    public final void testMonthLocalizeDecember()
//    {
//        Assert.assertEquals("décembre", MonthType.DECEMBER.getName(Locale.FRENCH));
//        Assert.assertEquals("dicembre",MonthType.DECEMBER.getName(Locale.ITALIAN));
//        Assert.assertEquals("december",MonthType.DECEMBER.getName(Locale.ENGLISH));
//        Assert.assertEquals("dezember",MonthType.DECEMBER.getName(Locale.GERMAN));
//        Assert.assertEquals("diciembre",MonthType.DECEMBER.getName(new Locale("es")));
//    }
}
