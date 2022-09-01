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
 * Enumeration providing values for the <b>image file</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum ImageFileType
{
    /**
     * Image format is PNG (see: <a href="https://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>).
     */
    PNG(".png"),

    /**
     * Image format is GIF (see: <a href="https://en.wikipedia.org/wiki/GIF">GIF</a>).
     */
    GIF(".gif"),

    /**
     * Image format is TIFF (see: <a href="https://en.wikipedia.org/wiki/TIFF">TIFF</a>).
     */
    TIFF(".tif"),

    /**
     * Image format is BMP (see: <a href="https://en.wikipedia.org/wiki/BMP_file_format">BMP</a>).
     */
    BMP(".bmp"),

    /**
     * Image format is SVG (see: <a href="https://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>).
     */
    SVG(".svg"),

    /**
     * Image format is JPEG (see: <a href="https://en.wikipedia.org/wiki/JPEG">JPEG</a>).
     */
    JPEG(".jpg");

    /**
     * Image format extension
     */
    @Getter
    private final String extension;

    /**
     * Creates a new image format type.
     * @param extension Image format file extension.
     */
    ImageFileType(String extension)
    {
        this.extension = extension;
    }
}
