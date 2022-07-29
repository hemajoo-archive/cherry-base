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

import com.hemajoo.commerce.cherry.base.i18n.localization.Localize;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18nBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@I18nBundle(bundle = "i18n/test")
public class QuoteOfTheDay implements Localize
{
    @Getter
    private final String quoteNumber;

    @Setter
    @Getter
    @I18n(bundle = "i18n/test", key = "com.hemajoo.commerce.cherry.base.i18n.quote.${quoteNumber}.title")
    private String quoteName;

    @Setter
    @Getter
    @I18n(bundle = "i18n/test", key = "com.hemajoo.commerce.cherry.base.i18n.quote.${quoteNumber}.text")
    private String quoteDescription;

    @Builder(setterPrefix = "with")
    public QuoteOfTheDay(final int number)
    {
        this.quoteNumber = String.valueOf(number); // Only 1 or 2 at this time!
    }
//
//    public String getQuoteName() throws StringExpanderException, AnnotationException, ResourceException
//    {
//        I18nManager.getInstance().resolveIndirect(this, I18nManager.getInstance().getLocale());
//        I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
//        return quoteName;
//    }
//
//    public String getQuoteDescription() throws StringExpanderException, AnnotationException, ResourceException
//    {
//        I18nManager.getInstance().resolveIndirect(this, I18nManager.getInstance().getLocale());
//        return quoteDescription;
//    }
}
