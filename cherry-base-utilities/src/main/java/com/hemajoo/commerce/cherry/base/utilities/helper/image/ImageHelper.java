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

import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import com.twelvemonkeys.image.ResampleOp;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

/**
 * Helper class containing services for image manipulations through the {@code ImgScalr} library.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class ImageHelper
{
    static
    {
        ImageIO.scanForPlugins();
    }

    /**
     * Saves an icon image.
     * @param sourceIconPath Image source path.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @param targetName Destination target name.
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageFileType outputType, final @NonNull String targetPath, final @NonNull String targetName) throws ImageException
    {
        return saveIcon(sourceIconPath, ImageScaleType.IMAGE_SCALE_DEFAULT, outputType, targetPath, targetName);
    }

    /**
     * Saves an icon image.
     * @param sourceIconPath Image source path.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageFileType outputType, final @NonNull String targetPath) throws ImageException
    {
        String filename = getFilenameWithoutExtension(sourceIconPath);

        return saveIcon(sourceIconPath, ImageScaleType.IMAGE_SCALE_DEFAULT, outputType, targetPath, filename);
    }

    /**
     * Saves an icon image.
     * @param sourceIconPath Image path (complete).
     * @param scaleType Image scale.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageScaleType scaleType, final ImageFileType outputType, final @NonNull String targetPath) throws ImageException
    {
        String filename = getFilenameWithoutExtension(sourceIconPath);

        return saveIcon(sourceIconPath, scaleType, outputType, targetPath, filename);
    }

    /**
     * Saves an icon image.
     * @param sourceIconPath Image path (complete).
     * @param scaleType Image scale.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @param targetName Image target name.
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageScaleType scaleType, final ImageFileType outputType, final @NonNull String targetPath, final @NonNull String targetName) throws ImageException
    {
        boolean result;
        BufferedImage converted;

        BufferedImage icon = getIcon(sourceIconPath, scaleType);

        try
        {
            ImageIO.setUseCache(false);
            result = ImageIO.write(icon, outputType.name(), new File(targetPath, targetName + outputType.getExtension()));
            if (!result)
            {
                switch (outputType)
                {
                    case BMP, JPEG:
                    default:
                        converted = new BufferedImage(icon.getWidth(),icon.getHeight(), BufferedImage.TYPE_INT_RGB);
                        converted.createGraphics().drawImage(icon,0,0,Color.WHITE,null);
                        break;
                }

                result = ImageIO.write(converted, outputType.name(), new File(targetPath, targetName + outputType.getExtension()));
                if (!result)
                {
                    throw new ImageException(String.format("Cannot generate image output: '%s', from source: '%s', in format: '%s'", targetPath, sourceIconPath, outputType));
                }
            }
        }
        catch (IOException fe)
        {
            throw new ImageException(String.format("Cannot generate image output: '%s', from source: '%s', in format: '%s'", targetPath, sourceIconPath, outputType));
        }

        return targetPath + File.separator + targetName + outputType.getExtension();
    }

    /**
     * Retrieves an icon.
     * @param sourcePath Icon source path and filename.
     * @return {@link BufferedImage} containing the icon image.
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     */
    public static BufferedImage getIcon(final @NonNull String sourcePath) throws ImageException
    {
        return getIcon(sourcePath, ImageScaleType.IMAGE_SCALE_DEFAULT);
    }

    /**
     * Retrieves an icon.
     * @param sourcePath Icon source path and filename.
     * @param scaleType Icon scale type.
     * @return {@link BufferedImage} containing the icon image.
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     */
    public static BufferedImage getIcon(final String sourcePath, final ImageScaleType scaleType) throws ImageException
    {
        File file;
        BufferedImage icon;
        BufferedImage output;
        BufferedImageOp processor;
        int width = 32;
        int height = 32;

        try
        {
            file = FileHelper.getFile(sourcePath, ImageHelper.class);
            icon = ImageIO.read(file);

            switch (scaleType)
            {
                case IMAGE_SCALE_DEFAULT:
                    width = icon.getWidth();
                    height = icon.getHeight();
                    break;

                case IMAGE_SCALE_CUSTOM:
                    break;

                case IMAGE_SCALE_13X13:
                    width = 13;
                    height = 13;
                    break;

                case IMAGE_SCALE_16X16:
                    width = 16;
                    height = 16;
                    break;

                case IMAGE_SCALE_64X64:
                    width = 64;
                    height = 64;
                    break;

                case IMAGE_SCALE_128X128:
                    width = 128;
                    height = 128;
                    break;

                case IMAGE_SCALE_256X256:
                    width = 256;
                    height = 256;
                    break;

                default:
                case IMAGE_SCALE_32X32:
                    break;
            }

            processor = new ResampleOp(width,height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info
            output = processor.filter(icon, null);
        }
        catch (IOException ioe)
        {
            throw new ImageException(String.format("Cannot convert icon image: '%s' due to: '%s'", sourcePath, ioe.getMessage()));
        }
        catch (NullPointerException e)
        {
            throw new ImageException(String.format("Cannot find icon image: '%s'", sourcePath));
        }

        return output;
    }

    /**
     * Returns the filename contained in the given path name.
     * @param pathname Path name.
     * @return Filename.
     */
    public static String getFilename(final @NonNull String pathname)
    {
        return FilenameUtils.getName(pathname);
    }

    /**
     * Returns the file name (without the '.' character) contained in the given path name.
     * @param pathname Path name.
     * @return Filename with its file extension.
     */
    public static String getFilenameWithoutExtension(final @NonNull String pathname)
    {
        String name = pathname;

        if (name.contains(String.valueOf(File.separatorChar)))
        {
            name = getFilename(name);
        }

        return FilenameUtils.removeExtension(name);
    }

    /**
     * Returns the image file extension of the file contained in the given path name.
     * @param pathname Path name.
     * @return {@link ImageFileType} representing the file extension of the file contained in the given path name.
     */
    public static ImageFileType getExtension(final @NonNull String pathname)
    {
        return ImageFileType.valueOf(FilenameUtils.getExtension(pathname).toUpperCase());
    }
}

