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
package com.hemajoo.commerce.cherry.base.commons.test;

import lombok.Getter;
import lombok.NonNull;

/**
 * Enumeration providing the possible values for a test media type.
 * @author <a href="mailto:christophe.resse@kyndryl.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum TestMediaType
{
    /**
     * Jpeg media file.
     */
    JPG("./media/android-10.jpg"),

    /**
     * Svg media file.
     */
    SVG("./media/canvas.svg"),

    /**
     * Pdf media file.
     */
    PDF("./media/java-8-streams-cheat-sheet.pdf"),

    /**
     * Html media file.
     */
    HTML("./media/license.html"),

    /**
     * Txt media file.
     */
    TXT("./media/sample.txt"),

    /**
     * Png media file.
     */
    PNG("./media/telephone.png");

    /**
     * Media file path.
     */
    @Getter
    private final String path;

    /**
     * Creates a new test media file given its path.
     * @param path Media file path.
     */
    TestMediaType(final @NonNull String path)
    {
        this.path = path;
    }

    /**
     * Creates a new test media file given its path.
     * @param path Media file path.
     * @return Test media file.
     * @throws IllegalArgumentException Thrown in case an error occurred while trying to determine the media file from the given path.
     */
    public static TestMediaType from(final @NonNull String path) throws IllegalArgumentException
    {
        for (TestMediaType type : values())
        {
            if (type.getPath().equals(path))
            {
                return type;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot instantiate test media type from path: %s!", path));
    }
}
