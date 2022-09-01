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

import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageException;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageFileType;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageHelper;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageScaleType;
import com.twelvemonkeys.image.ResampleOp;
import ij.IJ;
import ij.ImagePlus;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * A <b>picture</b> represents a graphical image.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public final class Picture implements IPicture
{
    /**
     * File containing the image.
     */
    @Getter
    @Setter
    private File file;

    /**
     * Buffered image.
     */
    @Getter
    @Setter
    private BufferedImage bufferedImage;

    /**
     * Image type.
     */
    private ImageFileType imageType;

    /**
     * Avoid direct instantiation of a picture.
     */
    private Picture() { /* Avoid direct instantiation.*/ }

    /**
     * Create a picture.
     * @param file File containing the image.
     * @param filename Filename containing the image.
     * @param scaleType Scaling type to apply when loading the image.
     * @param width Width of the image when loading.
     * @param height Height of the image when loading.
     * @throws ImageException Thrown to indicate an error occurred while trying to load the image.
     */
    @Builder(setterPrefix = "with")
    public Picture(final File file, final String filename, final ImageScaleType scaleType, final int width, final int height) throws ImageException
    {
        if (filename == null && file == null)
        {
            throw new ImageException("No image file specified!");
        }

        try
        {
            if (filename != null)
            {
                this.file = FileHelper.getFile(filename);
            }
            else
            {
                this.file = file;
            }

            loadImage(this.file, scaleType == null ? ImageScaleType.IMAGE_SCALE_ORIGINAL : scaleType, width, height);
        }
        catch (FileException e)
        {
            throw new ImageException(String.format("Cannot load image file: '%s' due to %s", filename, e.getMessage()));
        }
    }

    /**
     * Load a picture using the native <b>OS</b> file dialog.
     * @return Picture.
     * @throws ImageException Thrown to indicate an error occurred while trying to load the image.
     */
    public static IPicture load() throws ImageException
    {
        ImagePlus imagePlus = IJ.openImage();

        if (imagePlus == null)
        {
            throw new ImageException("No image file selected!");
        }

        return Picture.from(imagePlus);
    }

    /**
     * Create a picture from an <b>ImageJ</b> image.
     * @return Image.
     * @throws ImageException Thrown to indicate an error occurred while trying to load the picture.
     * @see ij.ImageJ
     */
    public static IPicture from(final @NonNull ImagePlus imagePlus) throws ImageException
    {
        IPicture image = new Picture();

        try
        {
            image.setFile(FileHelper.getFile(imagePlus.getOriginalFileInfo().getFilePath()));
            image.setBufferedImage(imagePlus.getBufferedImage());
        }
        catch (FileException e)
        {
            throw new ImageException(String.format("Cannot load image due to: %s ", e.getMessage()));
        }

        return image;
    }

    @Override
    public void load(final @NonNull String filename) throws ImageException
    {
        try
        {
            this.file = FileHelper.getFile(filename);
            loadImage(this.file, ImageScaleType.IMAGE_SCALE_DEFAULT, -1, -1);
        }
        catch (FileException e)
        {
            throw new ImageException(String.format("Cannot load image file: '%s' due to: %s", filename, e.getMessage()));
        }
    }

    @Override
    public String save() throws ImageException
    {
        return save(imageType, ImageScaleType.IMAGE_SCALE_ORIGINAL, file.getAbsolutePath());
    }

    @Override
    public String save(@NonNull String filename) throws ImageException
    {
        return save(imageType, ImageScaleType.IMAGE_SCALE_ORIGINAL, filename);
    }

    @Override
    public String save(ImageFileType type, @NonNull String filename) throws ImageException
    {
        return save(type, ImageScaleType.IMAGE_SCALE_ORIGINAL, filename);
    }

    @Override
    public String save(ImageFileType imageType, ImageScaleType scaleType, @NonNull String filename) throws ImageException
    {
        return save(
                imageType,
                scaleType != ImageScaleType.IMAGE_SCALE_ORIGINAL ? scaleType.getWidth() : bufferedImage.getWidth(),
                scaleType != ImageScaleType.IMAGE_SCALE_ORIGINAL ? scaleType.getHeight() : bufferedImage.getHeight(),
                filename);
    }

    @Override
    public String save(ImageFileType imageType, int width, int height, @NonNull String filename) throws ImageException
    {
        String outputFilename = ImageHelper.replaceExtension(filename, imageType);
        boolean result;
        BufferedImage converted;

        if (bufferedImage == null)
        {
            throw new ImageException("No image content to save!");
        }

        try
        {
            ImageIO.setUseCache(false);

            if (width != bufferedImage.getWidth() || height != bufferedImage.getHeight())
            {
                converted = Picture.clone(bufferedImage);
                converted = toBufferedImage(converted.getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH));
            }
            else
            {
                converted = Picture.clone(bufferedImage);
            }

            result = ImageIO.write(converted, imageType.name(), new File(outputFilename));
            if (!result)
            {
                throw new ImageException(String.format("Cannot generate image output: '%s' in format: '%s'", outputFilename, imageType));
            }
        }
        catch (IOException fe)
        {
            throw new ImageException(String.format("Cannot generate image output: '%s' in format: '%s'", outputFilename, imageType));
        }

        LOGGER.debug(String.format("Saved image to: '%s'", outputFilename));
        return outputFilename;
    }

    @Override
    public String getName()
    {
        return file.getName();
    }

    @Override
    public String getPath()
    {
        return file.getPath().replace(file.getName(), "");
    }

    @Override
    public String getExtension()
    {
        return ImageHelper.getExtension(file.getName()).getExtension();
    }

    @Override
    public ImageFileType getType()
    {
        return ImageHelper.getExtension(file.getName());
    }

    /**
     * Load an image and re-scale it.
     * @param file File representing the image.
     * @param scale Scale type.
     * @param scaleWidth Scale width.
     * @param scaleHeight Scale height.
     * @throws ImageException Thrown in case an error occurred while trying to load or re-scale the image.
     */
    public void loadImage(final File file, final ImageScaleType scale, final int scaleWidth, final int scaleHeight) throws ImageException
    {
        int width;
        int height;

        try
        {
            bufferedImage = ImageIO.read(file);
            LOGGER.debug(String.format("Loaded image from: '%s'", file.getAbsolutePath()));
            imageType = ImageHelper.getExtension(file.getAbsolutePath());
        }
        catch (IOException e)
        {
            throw new ImageException(String.format("Cannot re-scale image: '%s' due to: '%s'", file.getAbsolutePath(), e.getMessage()));
        }

        if (bufferedImage == null)
        {
            throw new ImageException(String.format("Image files of type: '%s' is not supported!", ImageHelper.getExtension(file.getAbsolutePath())));
        }

        switch (scale)
        {
            case IMAGE_SCALE_ORIGINAL:
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();
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
                width = scaleWidth;
                height = scaleHeight;
                break;

            default:
            case IMAGE_SCALE_32X32:
                width = 32;
                height = 32;
                break;
        }

        // Do we need re-scaling the image?
        if (scale != ImageScaleType.IMAGE_SCALE_ORIGINAL)
        {
            rescale(width, height);
        }
    }

    /**
     * Re-scale image.
     * @param width Width.
     * @param height Height.
     * @throws ImageException Thrown to indicate an error occurred when re-scaling an image.
     */
    public void rescale(final int width, final int height) throws ImageException
    {
        if (bufferedImage == null)
        {
            throw new ImageException("No image loaded!");
        }

        BufferedImageOp processor = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info!
        bufferedImage = processor.filter(bufferedImage, null);

        LOGGER.debug(String.format("Re-scaled image as width: '%s', height: '%s'", width, height));
    }

    public static BufferedImage clone(final @NonNull BufferedImage bufferImage)
    {
        ColorModel colorModel = bufferImage.getColorModel();
        WritableRaster raster = bufferImage.copyData(null);

        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
    }

    public static BufferedImage toBufferedImage(final @NonNull java.awt.Image image)
    {
        val bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        val graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage;
    }

    public static BufferedImage toBufferedImage(final @NonNull Picture image)
    {
        return toBufferedImage(image.getBufferedImage());
    }
}
