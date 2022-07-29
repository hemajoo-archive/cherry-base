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
package com.hemajoo.commerce.cherry.base.commons.annotation.support;

import java.lang.annotation.*;

/**
 * Indicates that the annotated element firstly appeared in the specified version of the library, so the code using
 * that element won't be compatible with older versions of the library. This information may be used by IDEs and static
 * analysis tools.
 * This annotation can be used instead of '@since' Javadoc tag if it's needed to keep that information in *.class files
 * or if you need to generate them automatically.
 * <br>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface AvailableSince
{
    /**
     * Version of the library.
     */
    String version() default "";
}
