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
package com.hemajoo.commerce.cherry.base.utilities.test.helper.file;

import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import com.hemajoo.commerce.cherry.base.utilities.test.base.BaseUnitTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for unit testing the {@link FileHelper} services.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
class FileHelperUnitTest extends BaseUnitTest
{
    /**
     * Test file.
     */
    private static final String FILE_CLASSPATH = "/log4j2-test.properties";

    /**
     * Test file.
     */
    private static final String FILE_URL = "https://www.w3.org/TR/PNG/iso_8859-1.txt";

    /**
     * Test file.
     */
    private static final String FILE_JAR = "jar:file:./src/test/resources/google-auth-library-credentials-0.16.1.jar!/META-INF/MANIFEST.MF";

    /**
     * Test file.
     */
    private static final String FILE_FILE = "file:../doc/devops.md";

    @Test
    @DisplayName("Get a file from the classpath")
    final void testGetFileFromClassPath() throws FileException
    {
        File file = FileHelper.getFile(FILE_CLASSPATH);
        LOGGER.debug(String.format("Getting file: %s from the classpath", FILE_CLASSPATH));
        assertThat(file).isNotNull();
    }

    @Test
    @DisplayName("Get a file from a JAR file")
    final void testGetFileFromJar() throws FileException
    {
        File file = FileHelper.getFile(FILE_JAR);
        LOGGER.debug(String.format("Getting file: %s from a JAR file", FILE_JAR));
        assertThat(file).isNotNull();
    }

    @Test
    @Timeout(10000)
    @DisplayName("Get a file from an URL")
    final void testGetFileFromUrl() throws FileException
    {
        File file = FileHelper.getFile(FILE_URL);
        LOGGER.debug(String.format("Getting file: %s from an URL", FILE_URL));
        assertThat(file).isNotNull();
    }

    @Test
    @DisplayName("Get a file from a file URL")
    final void testGetFileFromFile() throws FileException
    {
        File file = FileHelper.getFile(FILE_FILE);
        LOGGER.debug(String.format("Getting file: %s from a file URL", FILE_FILE));
        assertThat(file).isNotNull();
    }

    @Test
    @DisplayName("Get a file from the file system")
    final void testGetFileFromFileSystem() throws FileException
    {
        Path path = FileHelper.createTemporaryFile();
        File file = path.toFile();

        assertThat(file).isNotNull();
        file.deleteOnExit();

        String content = FileHelper.loadFileContentAsString(file.getPath());
        LOGGER.debug(String.format("Loading file content: %s from the file system", file.getPath()));
        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("Load a file from the classpath")
    final void testLoadFileFromClassPath() throws FileException
    {
        String content = FileHelper.loadFileContentAsString(FILE_CLASSPATH);
        LOGGER.debug(String.format("Loading file content: %s from the classpath", FILE_CLASSPATH));
        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("Load a file from a JAR file")
    final void testLoadFileFromJar() throws FileException
    {
        String content = FileHelper.loadFileContentAsString(FILE_JAR);
        LOGGER.debug(String.format("Loading file content: %s from a JAR file", FILE_JAR));
        assertThat(content).isNotNull();
    }

    @Test
    @Timeout(10000)
    @DisplayName("Load a file from an URL")
    final void testLoadFileFromUrl() throws FileException
    {
        String content = FileHelper.loadFileContentAsString(FILE_URL);
        LOGGER.debug(String.format("Loading file content: %s from an URL", FILE_URL));
        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("Load a file from a file prefix")
    final void testLoadFileFromFile() throws FileException
    {
        String content = FileHelper.loadFileContentAsString(FILE_FILE);
        LOGGER.debug(String.format("Loading file content: %s from a file URL", FILE_FILE));
        assertThat(content).isNotNull();
    }
}
