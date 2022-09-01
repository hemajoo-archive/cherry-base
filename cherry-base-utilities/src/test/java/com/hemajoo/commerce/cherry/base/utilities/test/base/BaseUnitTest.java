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
package com.hemajoo.commerce.cherry.base.utilities.test.base;

import com.hemajoo.commerce.cherry.base.commons.test.AbstractCherryUnitTest;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.file.FileHelper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * An abstract class for unit testing that provides some additional functionalities.
 * <hr>
 * <ul>
 * <li>a random generator</li>
 * <li>a faker generator</li>
 * <li>a temporary folder for storing files on the file system</li>
 * </ul>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public abstract class BaseUnitTest extends AbstractCherryUnitTest
{
    /**
     * Unit test temporary folder for the run.
     */
    protected static String TEST_TEMP_FOLDER = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString();

    /**
     * System specific file separator character.
     */
    protected static String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Checks if the given file name exist in the test folder?
     * @param filename File name to check.
     * @return True if the file exist, false otherwise.
     * @throws FileException Thrown to indicate an error occurred while trying to access a file.
     */
    protected static boolean existFile(final @NonNull String filename) throws FileException
    {
        if (filename.contains(FILE_SEPARATOR))
        {
            String path = FilenameUtils.getPath(filename);

            if (!normalizeFolderName(path).equals(normalizeFolderName(TEST_TEMP_FOLDER)))
            {
                return false;
            }
        }

        return FileHelper.getFile(filename).isFile();
    }

    /**
     * Normalizes the given folder name by removing (if necessary) the leading and training '/' character.
     * @param folderName Folder name to normalize.
     * @return Normalized folder name.
     */
    private static String normalizeFolderName(@NonNull String folderName)
    {
        if (folderName.startsWith(FILE_SEPARATOR))
        {
            folderName = folderName.substring(1);
        }
        if (folderName.endsWith(FILE_SEPARATOR))
        {
            folderName = folderName.substring(0, folderName.length() - 1);
        }

        return folderName;
    }

    @BeforeAll
    static void setUpBeforeClass() throws IOException
    {
        Files.createDirectories(Paths.get(TEST_TEMP_FOLDER));
        LOGGER.info(String.format("Created temporary unit test folder: '%s'", TEST_TEMP_FOLDER));
    }

    @AfterAll
    public static void tearDownAfterClass() throws IOException
    {
        FileUtils.deleteDirectory(new File(TEST_TEMP_FOLDER));
        LOGGER.info(String.format("Deleted temporary unit test folder: '%s'", TEST_TEMP_FOLDER));
    }
}

