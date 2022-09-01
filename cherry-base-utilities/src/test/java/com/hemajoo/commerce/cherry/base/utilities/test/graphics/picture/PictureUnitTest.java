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
package com.hemajoo.commerce.cherry.base.utilities.test.graphics.picture;

import com.hemajoo.commerce.cherry.base.utilities.graphics.picture.IPicture;
import com.hemajoo.commerce.cherry.base.utilities.graphics.picture.Picture;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageFileType;
import com.hemajoo.commerce.cherry.base.utilities.helper.image.ImageScaleType;
import com.hemajoo.commerce.cherry.base.utilities.test.base.BaseUnitTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for unit testing the {@link Picture} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
class PictureUnitTest extends BaseUnitTest
{
    /**
     * Test <b>BMP</b> image file full path.
     */
    private static final String PATH_IMAGE_BMP = "image/sample_640x426_24bits.bmp";

    /**
     * Test <b>PNG</b> image file full path.
     */
    private static final String PATH_IMAGE_PNG = "image/sample_800x500_24bits.png";

    /**
     * Test <b>JPG</b> image file full path.
     */
    private static final String PATH_IMAGE_JPG = "image/sample_2560x1920_24bits.jpg";

    /**
     * Test <b>GIF</b> image file full path.
     */
    private static final String PATH_IMAGE_GIF = "image/sample_400x200_8bits.gif";

    /**
     * Test <b>TIFF</b> image file full path.
     */
    private static final String PATH_IMAGE_TIFF = "image/sample_650x434_32bits.tiff";

    /**
     * Test <b>SVG</b> image file full path.
     */
    private static final String PATH_IMAGE_SVG = "image/sample_1067x800_32bits.svg";

    @Test
    @DisplayName("Load a BMP picture")
    final void testLoadBmpImage() throws FileException
    {
        // Using a file
        IPicture picture = Picture.builder()
                .withFile(FileHelper.getFile(PATH_IMAGE_BMP))
                .build();
        assertThat(picture).isNotNull();
        assertThat(picture.getExtension()).isEqualTo(".bmp");
        assertThat(picture.getName()).isEqualTo("sample_640x426_24bits.bmp");
        assertThat(picture.getPath()).endsWith("/image/");

        assertThat(picture.getBufferedImage().getWidth()).isEqualTo(640);
        assertThat(picture.getBufferedImage().getHeight()).isEqualTo(426);
        assertThat(picture.getBufferedImage().getColorModel().getPixelSize()).isEqualTo(24);

        // Using a filename
        picture = Picture.builder()
                .withFilename(PATH_IMAGE_BMP)
                .build();
        assertThat(picture).isNotNull();

        assertThat(picture.getBufferedImage().getWidth()).isEqualTo(640);
        assertThat(picture.getBufferedImage().getHeight()).isEqualTo(426);
        assertThat(picture.getBufferedImage().getColorModel().getPixelSize()).isEqualTo(24);
    }

    @Test
    @DisplayName("Load and scale a BMP picture")
    final void testLoadAndScaleBmpImage() throws FileException
    {
        final int SCALE_WIDTH = 100;
        final int SCALE_HEIGHT = 100;

        // Scale to 64x64
        IPicture picture = Picture.builder()
                .withFile(FileHelper.getFile(PATH_IMAGE_BMP))
                .withScaleType(ImageScaleType.IMAGE_SCALE_64X64)
                .build();

        assertThat(picture).isNotNull();

        assertThat(picture.getBufferedImage().getWidth()).isEqualTo(64);
        assertThat(picture.getBufferedImage().getHeight()).isEqualTo(64);
        assertThat(picture.getBufferedImage().getColorModel().getPixelSize()).isEqualTo(24);

        // Custom scale: 100x100
        picture = Picture.builder()
                .withFile(FileHelper.getFile(PATH_IMAGE_BMP))
                .withScaleType(ImageScaleType.IMAGE_SCALE_CUSTOM)
                .withWidth(SCALE_WIDTH)
                .withHeight(SCALE_HEIGHT)
                .build();

        assertThat(picture).isNotNull();

        assertThat(picture.getBufferedImage().getWidth()).isEqualTo(SCALE_WIDTH);
        assertThat(picture.getBufferedImage().getHeight()).isEqualTo(SCALE_HEIGHT);
        assertThat(picture.getBufferedImage().getColorModel().getPixelSize()).isEqualTo(24);
    }

    @Test
    @Disabled("Do not run this test in a Junit automated unit test, should be executed manually!")
    final void testLoadBmpImageFileUsingFileChooser() throws FileException
    {
        IPicture picture = Picture.load();

        assertThat(picture).isNotNull();
        assertThat(picture.getFile()).isNotNull();

        assertThat(picture.getBufferedImage().getWidth()).isNotZero();
        assertThat(picture.getBufferedImage().getHeight()).isNotZero();
        assertThat(picture.getBufferedImage().getColorModel().getPixelSize()).isNotZero();
    }

    @Test
    @DisplayName("Save a BMP picture to a file")
    final void testSaveAsBmpImage() throws FileException
    {
        IPicture picture = Picture.builder()
                .withFile(FileHelper.getFile("image/sample.bmp"))
                .build();

        assertThat(picture).isNotNull();

        picture.save(); // Update the existing image file

        picture.save(TEMPORARY_FOLDER + File.separator + "sample_backup"); // Update the existing image with new name

        picture.save(
                ImageFileType.PNG,
                TEMPORARY_FOLDER + File.separator + "sample_backup_bmp_as_png.png"); // Update the existing image file in a different format

        picture.save(
                ImageFileType.PNG,
                ImageScaleType.IMAGE_SCALE_32X32,
                TEMPORARY_FOLDER + File.separator + "sample_backup_bmp_as_png_scale_64x64"); // Update the existing image file, changing the format and the scale
    }
}
