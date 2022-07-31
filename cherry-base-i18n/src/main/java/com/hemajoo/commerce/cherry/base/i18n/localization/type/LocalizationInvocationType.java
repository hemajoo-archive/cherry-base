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

import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;

/**
 * Enumeration representing the several possible <b>localization invocation</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since 0.3.0
 * @version 1.0.0
 * @see I18n
 */
public enum LocalizationInvocationType
{
    /**
     * Localization invocation is unknown.
     */
    UNKNOWN,

    /**
     * Localization invocation has been done through a method.
     */
    METHOD,

    /**
     * Localization invocation has been done through a field.
     */
    FIELD
}
