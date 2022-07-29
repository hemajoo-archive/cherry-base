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
package com.hemajoo.commerce.cherry.base.i18n.localization.annotation;

import java.lang.annotation.*;

/**
 * Annotation used to be placed on enum types <u>only</u> which makes this enumeration automatically localized by the framework.
 * <br><br>
 * Such annotated enumerations must implement the <b>LocalizeEnum</b> interface without the need to provide
 * any implementation for the interface's services as it already provides a <b>default</b> one for the <b>getName</b> services.
 * <br><br><b>Example:</b><br>
 * <pre>
 * I18nEnum(bundle = "i18n/geo/continent", key = "com.hemajoo.commerce.cherry.base.i18n.geo.continent.${this}.name")
 * public enum ContinentType implements LocalizeEnum
 * {
 *      AFRICA,
 *      AMERICA,
 *      ASIA,
 *      ...
 * }
 * </pre>
 * Then, to retrieve the localized name of the <i>APRIL</i> month in French, simply invoke its <b>getName</b> service such as in the following example providing the target locale:
 * <br><br><b>Example:</b><br>
 * <pre>
 * String monthNameInFrench = MonthType.APRIL.getName(Locale.FRENCH);
 * </pre>
 * You could also set a default target locale in the <b>I18nManager</b> and retrieve localized strings always in this locale:
 * <br><br><b>Example:</b><br>
 * <pre>
 * I18nManager.getInstance().setLocale(Locale.GERMAN);
 * String monthNameInGerman = MonthType.AUGUST.getName();
 * String otherMonthNameInGerman = MonthType.MAY.getName();
 * </pre>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE } )
public @interface I18nEnum
{
    /**
     * Resource bundle path and name.
     * @return Resource bundle path and name.
     */
    String bundle() default "";

    /**
     * Resource bundle key.
     * @return Resource bundle key.
     */
    String key() default "";
}
