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

import com.hemajoo.commerce.cherry.base.commons.test.AbstractCherryUnitTest;
import com.hemajoo.commerce.cherry.base.utilities.helper.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.FileHelper;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
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
public abstract class BaseUnitTest extends AbstractCherryUnitTest
{
    /**
     * Unit test temporary folder for the run.
     */
    protected static String testFolder = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString();

    /**
     * System specific file separator character.
     */
    protected static String fileSeparator = System.getProperty("file.separator");

    /**
     * Random number generator.
     */
    protected final Random random = new Random();

    /**
     * Checks if the given file name exist in the test folder?
     * @param filename File name to check.
     * @return True if the file exist, false otherwise.
     */
    protected static boolean existFile(final @NonNull String filename)
    {
        if (filename.contains(fileSeparator))
        {
            String path = FilenameUtils.getPath(filename);

            if (!normalizeFolderName(path).equals(normalizeFolderName(testFolder)))
            {
                return false;
            }
        }

        try
        {
            if(!FileHelper.getFile(filename).isFile())
            {
                return false;
            }
        }
        catch (FileException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Normalizes the given folder name by removing (if necessary) the leading and training '/' character.
     * @param folderName Folder name to normalize.
     * @return Normalized folder name.
     */
    private static String normalizeFolderName(@NonNull String folderName)
    {
        if (folderName.startsWith(fileSeparator))
        {
            folderName = folderName.substring(1);
        }
        if (folderName.endsWith(fileSeparator))
        {
            folderName = folderName.substring(0, folderName.length() - 1);
        }

        return folderName;
    }

    /**
     * Initializes the test case.
     */
    @BeforeAll
    static void setUpBeforeClass()
    {
        try
        {
            Files.createDirectories(Paths.get(testFolder));
            System.out.printf("Test folder set to: [%s]%n", testFolder);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Finalizes the test case.
     */
    @AfterAll
    public static void tearDownAfterClass()
    {
        try
        {
            FileUtils.deleteDirectory(new File(testFolder));
            System.out.printf("Test folder [%s] deleted%n", testFolder);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

