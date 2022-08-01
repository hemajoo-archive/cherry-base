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
package com.hemajoo.commerce.cherry.base.utilities.test.helper;

import com.hemajoo.commerce.cherry.base.utilities.helper.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.FileHelper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test class for unit testing the {@link FileHelper} services.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
class FileHelperUnitTest extends BaseUnitTest
{
    /**
     * Sets up the test case.
     */
    @BeforeEach
    public final void setUpBeforeEach()
    {
        // Empty
    }

    /**
     * Tears down the test case.
     */
    @AfterEach
    public final void tearDownAfterEach()
    {
        // Empty
    }

    /**
     * Dumps the content of a file.
     * @param filename File name.
     * @param file File.
     * @throws IOException Thrown in case an error occurred trying to dump the content of the file.
     */
    private static void dumpFile(final @NonNull String filename, final @NonNull File file) throws IOException
    {
        LOGGER.debug("File name is: ");
        LOGGER.debug(filename + "\n");
        LOGGER.debug("URL of file is: ");
        LOGGER.debug(file.toURI().toURL() + "\n");
        LOGGER.debug("File content is: ");
        Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(LOGGER::debug);
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file loaded on the classpath.
     */
    @Test
    final void testFileHelperLoadFileFromClasspath() throws FileException
    {
        String filename = "/log4j2-test.properties";

        String content = FileHelper.loadFileContentAsString(filename);
        assertThat(content).isNotNull();

        LOGGER.debug(content);
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file retrieved on the class path.
     */
    @Test
    final void testFileHelperGetFileFromClasspath() throws FileException, IOException
    {
        String filename = "/log4j2-test.properties";

        File file = FileHelper.getFile(filename);
        assertThat(file).isNotNull();

        dumpFile(filename, file);
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file located in a jar file on the class path.
     */
    @Test
    final void testFileHelperGetFileFromJar() throws FileException, IOException
    {
        String filename = "jar:file:/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "/changelog.txt";
        filename = "/META-INF/MANIFEST.MF";

        File file = FileHelper.getFile(filename);
        assertThat(file).isNotNull();

        dumpFile(filename, file);
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file located in a jar file on the class path.
     */
    @Test
    final void testFileHelperLoadFileFromJar() throws FileException
    {
        String filename;
        filename = "/META-INF/MANIFEST.MF"; // Not unique, exist for many JAR files, first one is returned
        filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "/changelog.txt"; // Should be the changelog from lombok jar file.

        String content = FileHelper.loadFileContentAsString(filename);
        assertThat(content).isNotNull();

        System.out.println(content);
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from an http url.
     */
    @Test()
    @Timeout(30000) // 30 secs timeout
    final void testFileHelperGetFileFromHttpUrl() throws FileException, IOException
    {
        String filename = "https://www.w3.org/TR/PNG/iso_8859-1.txt";

        File file = FileHelper.getFile(filename);
        assertThat(file).isNotNull();

        dumpFile(filename, file);
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from an http url.
     */
    @Test()
    @Timeout(30000) // 30 secs timeout
    final void testFileHelperLoadFileFromHttpUrl() throws FileException
    {
        String filename = "https://www.w3.org/TR/PNG/iso_8859-1.txt";

        String content = FileHelper.loadFileContentAsString(filename);
        assertThat(content).isNotNull();

        System.out.println(content);
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from a file url.
     */
    @Test
    final void testFileHelperGetFileFromFileUrl() throws FileException, IOException
    {
        // This test will fail if run on remote machine such as GitLab or Travis!
        String filename = "file:/Volumes/Technology/project/gitlab.com/hemajoo/foundation/hemajoo-foundation/foundation-utility/target/test-classes/log4j2-test.properties";
        filename = "file:../etc/deploy-settings.xml";

        File file = FileHelper.getFile(filename);
        assertThat(file).isNotNull();

        dumpFile(filename, file);
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from a file url.
     */
    @Test
    final void testFileHelperLoadFileFromFileUrl() throws FileException
    {
        // This test will fail if run on remote machine such as GitLab or Travis!
        String filename = "file:/Volumes/Technology/project/gitlab.com/hemajoo/foundation/hemajoo-foundation/foundation-utility/target/test-classes/log4j2-test.properties";
        filename = "file:../etc/deploy-settings.xml";

        String content = FileHelper.loadFileContentAsString(filename);
        assertThat(content).isNotNull();

        System.out.println(content);
    }

    /**
     * Test the {@link FileHelper#getFile(String)} service using a file retrieved from a jar file url.
     */
    @Test
    final void testFileHelperLoadFileFromJarUrl() throws FileException, IOException
    {
        String filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "jar:file:./src/test/resources/google-auth-library-credentials-0.16.1.jar!/META-INF/MANIFEST.MF";

        File file = FileHelper.getFile(filename);
        assertThat(file).isNotNull();

        dumpFile(filename, file);
    }

    /**
     * Test the {@link FileHelper#loadFileContentAsString(String)} service using a file loaded from a jar file url.
     */
    @Test
    final void testFileHelperGetFileFromJarUrl() throws FileException
    {
        String filename = "jar:file:/Users/christophe/.m2/junit/junit/4.13-beta-3/junit-4.13-beta-3.jar!/META-INF/MANIFEST.MF";
        filename = "jar:file:./src/test/resources/google-auth-library-credentials-0.16.1.jar!/META-INF/MANIFEST.MF";

        String content = FileHelper.loadFileContentAsString(filename);
        assertThat(content).isNotNull();

        System.out.println(content);
    }

    /**
     * Test the ability to load a file using its file system complete path and name.
     */
    @Test
    final void testFileHelperGetFileFromFileSystem() throws IOException, FileException
    {
        Path path = FileHelper.createTemporaryFile();
        File file = path.toFile();
        file.deleteOnExit();
        String content = FileHelper.loadFileContentAsString(file.getPath());

        assertThat(content).isNotNull();
    }
}
