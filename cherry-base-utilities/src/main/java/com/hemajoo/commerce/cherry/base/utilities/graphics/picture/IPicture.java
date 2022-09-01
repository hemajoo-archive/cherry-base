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
package com.hemajoo.commerce.cherry.base.utilities.graphics.picture;

import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageException;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageFileType;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageScaleType;
import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Provide the behavior of a picture.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IPicture
{
    /**
     * Return the file containing a picture.
     * @return {@link File} containing a picture, <b>null</b> otherwise.
     */
    File getFile();

    /**
     * Return the buffered image contained in a picture.
     * @return Buffered image.
     * @see javax.imageio.ImageIO
     */
    BufferedImage getBufferedImage();

    /**
     * Return the name of the file containing a picture.
     * @return Name of the file if one exist, <b>null</b> otherwise.
     */
    String getName();

    /**
     * Return the path of the file containing a picture.
     * @return Path of the file if one exist, <b>null</b> otherwise.
     */
    String getPath();

    /**
     * Return the extension of the file containing a picture.
     * @return Extension of the file if one exist, <b>null</b> otherwise.
     */
    String getExtension();

    /**
     * Return the type of the file containing a picture.
     * @return Type of the file if one exist, <b>null</b> otherwise.
     */
    ImageFileType getType();

    /**
     * Load a picture given its filename.
     * @param filename Filename.
     * @throws ImageException Thrown to indicate an error occurred when trying to load a file containing an image.
     */
    void load(final @NonNull String filename) throws ImageException;

    /**
     * Save/update an existing file containing a picture.
     * @return Filename (including path).
     * @throws ImageException Thrown to indicate an error occurred when trying to save/update a file containing an image.
     */
    String save() throws ImageException;

    /**
     * Save a picture given its filename.
     * @param filename Filename.
     * @return Filename (including path).
     * @throws ImageException Thrown to indicate an error occurred when trying to save a file containing an image.
     */
    String save(final @NonNull String filename) throws ImageException;

    /**
     * Save a picture to a given image type.
     * @param imageType Image file type.
     * @param filename Filename.
     * @return Filename (including path).
     * @throws ImageException Thrown to indicate an error occurred when trying to save a file containing an image.
     */
    String save(final ImageFileType imageType, final @NonNull String filename) throws ImageException;

    /**
     * Save a picture to a given image type and a given scaling factor.
     * @param imageType Image file type.
     * @param scaleType Image scaling type.
     * @param filename Filename.
     * @return Filename (including path).
     * @throws ImageException Thrown to indicate an error occurred when trying to save a file containing an image.
     */
    String save(final ImageFileType imageType, final ImageScaleType scaleType, final @NonNull String filename) throws ImageException;

    /**
     * Save a picture to a given image type and a given size.
     * @param imageType Image file type.
     * @param width Target image width.
     * @param height Target image height.
     * @param filename Filename.
     * @return Filename (including path).
     * @throws ImageException Thrown to indicate an error occurred when trying to save a file containing an image.
     */
    String save(final ImageFileType imageType, final int width, final int height, final @NonNull String filename) throws ImageException;

    /**
     * Set the file containing a picture.
     * @param file File.
     */
    void setFile(final @NonNull File file);

    /**
     * Set the underlying buffered image.
     * @param image Buffered image.
     */
    void setBufferedImage(final @NonNull BufferedImage image);
}
