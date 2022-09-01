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

import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import com.twelvemonkeys.image.ResampleOp;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

/**
 * Utility class providing services to ease image manipulation through the use of the {@code ImgScalr} library.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@UtilityClass
public final class ImageHelper
{
    static
    {
        ImageIO.scanForPlugins(); // Load extensions if some are available!
    }

    /**
     * Load an image.
     * @param filename Image filename.
     * @return {@link BufferedImage} containing the loaded image.
     * @throws ImageException Thrown in case an error occurred while trying to load the icon.
     */
    public static BufferedImage loadImage(final @NonNull String filename) throws ImageException
    {
        return loadImage(filename, ImageScaleType.IMAGE_SCALE_DEFAULT);
    }

    /**
     * Save an image to a given path.
     * @param image Image to save.
     * @param outputType Output type.
     * @param targetPath Target path name.
     * @param targetName Target file name (without extension).
     * @return Full path and name of the saved image.
     * @throws ImageException Thrown to indicate an error occurred while trying to save an image.
     * @see ImageFileType
     */
    public static String save(final @NonNull BufferedImage image, final @NonNull ImageFileType outputType, final @NonNull String targetPath, final @NonNull String targetName) throws ImageException
    {
        String fullTargetPath = targetPath + File.separator + targetName + outputType.getExtension();

        ImageIO.setUseCache(false);
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImage.createGraphics().drawImage(image, 0, 0,Color.WHITE,null);

        try
        {
            boolean result = ImageIO.write(convertedImage, outputType.name(), new File(targetPath, targetName + outputType.getExtension()));
            if (!result)
            {
                throw new ImageException(String.format("Cannot save image: '%s' in format: '%s'", fullTargetPath, outputType.name()));
            }
        }
        catch (IOException e)
        {
            throw new ImageException(String.format("Cannot save image: '%s' in format: '%s' due to: %s", fullTargetPath, outputType.name(), e.getMessage()));
        }

        return fullTargetPath;
    }

    /**
     * Re-scale an image.
     * @param image Image to re-scale.
     * @param scaleType Scale type.
     * @return {@link BufferedImage} containing the re-scaled image.
     * @throws ImageException Thrown in case an error occurred while trying to load or re-scale the image.
     */
    public static BufferedImage rescale(final @NonNull BufferedImage image, final ImageScaleType scaleType) throws ImageException
    {
        BufferedImageOp processor;
        BufferedImage scaledImage;
        int width = 32;
        int height = 32;

        switch (scaleType)
        {
            case IMAGE_SCALE_DEFAULT:
                width = image.getWidth();
                height = image.getHeight();
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

            case IMAGE_SCALE_CUSTOM:
                throw new ImageException("Image scaling of type: CUSTOM is not supported at this time!");

            default:
            case IMAGE_SCALE_32X32:
                break;
        }

        // Do we need re-scaling the image?
        if (width != image.getWidth())
        {
            processor = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info!
            scaledImage = processor.filter(image, null);

            LOGGER.debug(String.format("Re-scaled image from: %sx%s to %sx%s", image.getWidth(), image.getHeight(), width, height));
            return scaledImage;
        }

        return image;
    }







    /**
     * Save an icon image.
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
     * Save an icon image.
     * @param sourceIconPath Image source path.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageFileType outputType, final @NonNull String targetPath) throws ImageException
    {
        String filename = extractFilenameWithoutExtension(sourceIconPath);

        return saveIcon(sourceIconPath, ImageScaleType.IMAGE_SCALE_DEFAULT, outputType, targetPath, filename);
    }

    /**
     * Save an icon image.
     * @param sourceIconPath Image path (complete).
     * @param scaleType Image scale.
     * @param outputType Image output type.
     * @param targetPath Destination target path (can be relative).
     * @throws ImageException Thrown in case an error occurred while manipulating an image.
     * @return Full pathname (containing the file extension) of the saved image file.
     */
    public static String saveIcon(final @NonNull String sourceIconPath, final ImageScaleType scaleType, final ImageFileType outputType, final @NonNull String targetPath) throws ImageException
    {
        String filename = extractFilenameWithoutExtension(sourceIconPath);

        return saveIcon(sourceIconPath, scaleType, outputType, targetPath, filename);
    }

    /**
     * Save an icon of an image (by applying a down-scaling of the image) before saving it.
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

        BufferedImage icon = loadImage(sourceIconPath, ImageScaleType.IMAGE_SCALE_DEFAULT);

        try
        {
            ImageIO.setUseCache(false);
            result = ImageIO.write(icon, outputType.name(), new File(targetPath, targetName + outputType.getExtension()));
            if (!result)
            {
                switch (outputType)
                {
                    case TIFF:
                        throw new IllegalArgumentException();

                    case GIF:
                        throw new IllegalArgumentException();

                    case PNG:
                        throw new IllegalArgumentException();

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
     * Load an image and re-scale it.
     * @param sourcePath Path of the icon image file (full).
     * @param scaleType Scale type.
     * @return {@link BufferedImage} containing the re-scaled image.
     * @throws ImageException Thrown in case an error occurred while trying to load or re-scale the image.
     */
    public static BufferedImage loadImage(final String sourcePath, final ImageScaleType scaleType) throws ImageException
    {
        BufferedImageOp processor;
        File file = null;
        BufferedImage image = null;
        BufferedImage scaledImage;
        int width = 32;
        int height = 32;

        try
        {
            file = FileHelper.getFile(sourcePath, ImageHelper.class);
            image = ImageIO.read(file);
        }
        catch (FileException e)
        {
            throw new ImageException(String.format("Cannot find image: '%s' due to: %s", sourcePath, e.getMessage()));
        }
        catch (IOException e)
        {
            throw new ImageException(String.format("Cannot re-scale image: '%s' due to: '%s'", sourcePath, e.getMessage()));
        }

        if (image == null)
        {
            throw new ImageException(String.format("Image files of type: '%s' is not supported!", ImageHelper.getExtension(sourcePath)));
        }

        switch (scaleType)
        {
            case IMAGE_SCALE_DEFAULT:
                width = image.getWidth();
                height = image.getHeight();
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

            case IMAGE_SCALE_CUSTOM:
                throw new ImageException("Image scaling of type: CUSTOM is not supported at this time!");

            default:
            case IMAGE_SCALE_32X32:
                break;
        }

        // Do we need re-scaling the image?
        if (width != image.getWidth())
        {
            processor = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info!
            scaledImage = processor.filter(image, null);

            return scaledImage;
        }

        return image;
    }

    /**
     * Extract the filename contained in the given full path name.
     * @param pathname Path name.
     * @return Filename.
     */
    public static String extractFilename(final @NonNull String pathname)
    {
        return FilenameUtils.getName(pathname);
    }

    /**
     * Extract the file name (without the '.' character separating the filename from the file extension) contained in the given path name.
     * @param pathname Path name.
     * @return Filename without the file extension.
     */
    public static String extractFilenameWithoutExtension(final @NonNull String pathname)
    {
        String name = pathname;

        if (name.contains(String.valueOf(File.separatorChar)))
        {
            name = extractFilename(name);
        }

        return FilenameUtils.removeExtension(name);
    }

    /**
     * Return the image file extension of the file contained in the given path name.
     * @param pathname Path name.
     * @return {@link ImageFileType} representing the file extension of the file contained in the given path name.
     */
    public static ImageFileType getExtension(final @NonNull String pathname)
    {
        return ImageFileType.valueOf(FilenameUtils.getExtension(pathname).toUpperCase());
    }

    /**
     * Return the filename without its extension.
     * @param filename Filename.
     * @return Filename without extension.
     */
    public static String removeExtension(final @NonNull String filename)
    {
        int index = filename.lastIndexOf('.');
        if (index >= filename.length() - 5)
        {
            return filename.substring(0, index);
        }

        return filename;
    }

    /**
     * Replace the file extension with a given extension.
     * @param filename Filename.
     * @param imageType Image type.
     * @return Filename.
     */
    public static String replaceExtension(final @NonNull String filename, final @NonNull ImageFileType imageType)
    {
        String outputFilename = removeExtension(filename);

        return outputFilename + imageType.getExtension();
    }
}

