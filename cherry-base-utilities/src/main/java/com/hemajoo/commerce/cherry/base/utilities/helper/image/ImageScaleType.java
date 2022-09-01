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
package com.hemajoo.commerce.cherry.base.utilities.helper.image;

import lombok.Getter;

/**
 * Enumeration providing values for the <b>image scale</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum ImageScaleType
{
    /**
     * Use a custom width and height to resize the image.
     */
    IMAGE_SCALE_CUSTOM(0, 0),

    /**
     * Use the original width and height of the image.
     */
    IMAGE_SCALE_ORIGINAL(0, 0),

    /**
     * Use the original image width and height.
     */
    IMAGE_SCALE_DEFAULT(32, 32),

    /**
     * Resize image to 13x13 pixels.
     */
    IMAGE_SCALE_13X13(13, 13),

    /**
     * Resize image to 16x16 pixels.
     */
    IMAGE_SCALE_16X16(16, 16),

    /**
     * Resize image to 32x32 pixels.
     */
    IMAGE_SCALE_32X32(32, 32),

    /**
     * Resize image to 64x64 pixels.
     */
    IMAGE_SCALE_64X64(64, 64),

    /**
     * Resize image to 128x128 pixels.
     */
    IMAGE_SCALE_128X128(128, 128),

    /**
     * Resize image to 256x256 pixels.
     */
    IMAGE_SCALE_256X256(256, 256);

    @Getter
    private final int width;

    @Getter
    private final int height;

    ImageScaleType(final int width, final int height)
    {
        this.width = width;
        this.height = height;
    }
}
