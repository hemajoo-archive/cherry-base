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
package com.hemajoo.commerce.cherry.base.i18n.localization.type.time;

import com.hemajoo.commerce.cherry.base.i18n.localization.LocalizeEnum;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18nEnum;

/**
 * A localized enumeration representing the <b>months</b> of the year.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since 0.3.0
 * @version 1.0.0
 * @see I18n
 */
@I18nEnum(bundle = "i18n/time/month", key = "month.${this}.name")
public enum MonthType implements LocalizeEnum
{
    /**
     * January.
     */
    JANUARY,

    /**
     * February.
     */
    FEBRUARY,

    /**
     * March.
     */
    MARCH,

    /**
     * April.
     */
    APRIL,

    /**
     * May.
     */
    MAY,

    /**
     * June.
     */
    JUNE,

    /**
     * July.
     */
    JULY,

    /**
     * August.
     */
    AUGUST,

    /**
     * September.
     */
    SEPTEMBER,

    /**
     * October.
     */
    OCTOBER,

    /**
     * November.
     */
    NOVEMBER,

    /**
     * December.
     */
    DECEMBER
}
