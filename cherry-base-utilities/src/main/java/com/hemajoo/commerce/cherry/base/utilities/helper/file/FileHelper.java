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
package com.hemajoo.commerce.cherry.base.utilities.helper.file;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class providing convenient services for manipulating <b>files</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@UtilityClass
public class FileHelper
{
    /**
     * POSIX file attribute for temporary file.
     */
    private static final String POSIX_FILE_ATTRIBUTES = "rwx------";

    /**
     * Temporary file prefix.
     */
    private static final String TEMPORARY_FILE_PREFIX = "_file";

    /**
     * Temporary file suffix.
     */
    private static final String TEMPORARY_FILE_SUFFIX = ".file";

    /**
     * Create the file and the necessary directory structure of a given file.
     * @param file File.
     * @return Path.
     */
    public static Path createFileWithDirs(final @NonNull File file)
    {
        return createFileWithDirs(file.getPath());
    }

    /**
     * Create the file and the necessary directory structure of a given file path.
     * @param filePath File path and name.
     * @return Path.
     */
    public static Path createFileWithDirs(String filePath)
    {
        String directory = filePath.substring(0, filePath.lastIndexOf(File.separator));
        String filename = filePath.substring(filePath.lastIndexOf(File.separator) + 1);

        File dir = new File(directory);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        return Paths.get(directory + File.separatorChar + filename);
    }

    /**
     * Return a file given its filename.
     * <br>
     * This service is able to retrieve a file from the file system, classpath, a jar file or from an url.
     * @param filename File name to retrieve.
     * @param type Class type to use to load the file.
     * @return {@link File} representing the retrieved file if found, <b>null</b> otherwise.
     * @throws FileException Thrown to indicate an error occurred while trying to access a file.
     */
    public static File getFile(final @NonNull String filename, final @NonNull Class<?> type) throws FileException
    {
        File file = loadFromAbsolutePath(filename);
        if (file == null)
        {
            file = loadFromRelativePath(filename, type);
            if (file == null)
            {
                file = loadFromJarPath(filename, type);
                if (file == null)
                {
                    file = loadFromTempPath(filename);
                }
            }
        }

        return file;
    }

    /**
     * Load a file given its filename as if it is an absolute path.
     * @param filename Filename.
     * @return File if found, <b>null</b> otherwise.
     */
    private File loadFromAbsolutePath(final @NonNull String filename)
    {
        File file = new File(filename);
        if (file.isFile() && !file.isDirectory())
        {
            return file;
        }

        return null;
    }

    /**
     * Load a file given its filename as if it is an relative path.
     * @param filename Filename.
     * @param type Class type.
     * @return File if found, <b>null</b> otherwise.
     */
    private File loadFromRelativePath(final @NonNull String filename, final @NonNull Class<?> type)
    {
        URL url = type.getClassLoader().getResource(filename);
        if (url != null)
        {
            try
            {
                return new File(url.toURI());
            }
            catch (URISyntaxException e)
            {
                return null;
            }
        }

        return null;
    }

    /**
     * Load a file given its filename as if the path is a <b>JAR</b> path.
     * @param filename Filename.
     * @return File if found, <b>null</b> otherwise.
     */
    private File loadFromJarPath(final @NonNull String filename, final @NonNull Class<?> type) throws FileException
    {
        URL url = type.getResource(filename);
        if (url != null && url.toString().startsWith("jar:"))
        {
            // No chance to get a file handle if the file is located inside a jar!
            // We need to use a temporary folder.
            return loadFromTempPath(filename);
        }

        return null;
    }

    /**
     * Load a file given its filename as if the path is a temporary folder path.
     * @param filename Filename.
     * @return File if found, <b>null</b> otherwise.
     */
    private File loadFromTempPath(final @NonNull String filename) throws FileException
    {
        boolean isTemporaryFile = false;
        Path path;
        File file = null;

        try
        {
            // If not successful, then try to load it from a JAR file.
            path = createTemporaryFile();
            Files.copy(Objects.requireNonNull(FileHelper.class.getResourceAsStream(filename)), path, StandardCopyOption.REPLACE_EXISTING);
            file = path.toFile();
            isTemporaryFile = true;
        }
        catch (Exception ex)
        {
            try
            {
                // Still not successful, then try to load it from an URL.
                path = createTemporaryFile();
                Files.copy(new URL(filename).openStream(), path, StandardCopyOption.REPLACE_EXISTING);
                file = path.toFile();
                isTemporaryFile = true;
            }
            catch (Exception exception)
            {
                // Try to load it from the file system.
                file = new File(filename);
                if (file.isFile() && !file.isDirectory())
                {
                    return file;
                }
                else
                {
                    throw new FileException(String.format("Cannot find file: '%s'!", filename));
                }
            }
        }
        finally
        {
            if (isTemporaryFile)
            {
                file.deleteOnExit();
            }
        }

        return file;
    }

    /**
     * Return a file given its filename.
     * <br>
     * This service is able to retrieve a file from the file system, classpath, a jar file or from an url.
     * @param filename File name to retrieve.
     * @return {@link File} representing the retrieved file if found, <b>null</b> otherwise.
     * @throws FileException Thrown to indicate an error occurred while trying to access a file.
     */
    public static File getFile(final @NonNull String filename) throws FileException
    {
        return getFile(filename, FileHelper.class);
    }

    /**
     * Load the content of a text file into a string.
     * @param filename File name to load.
     * @return String representing the content of the file.
     * @throws FileException Thrown when an error occurred while trying to load the file.
     */
    public static String loadFileContentAsString(final @NonNull String filename) throws FileException
    {
        File file = getFile(filename);

        try
        {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8); // This uses the commons-io library to load the file into a string.
        }
        catch (IOException e)
        {
            throw new FileException(e);
        }
    }

    /**
     * Load a text file given its filename.
     * @param filename File name to load.
     * @return List of lines.
     * @throws FileException Thrown when an error occurred while trying to load the file.
     */
    public static List<String> loadFileContentAsList(final @NonNull String filename) throws FileException
    {
        return Arrays.asList(loadFileContentAsString(filename).split("\\n"));
    }

    /**
     * Check if the given file name exist?
     * @param pathname File name to check.
     * @return True if the file exist, false otherwise.
     * @throws FileException Thrown to indicate an error occurred while trying to access a file.
     */
    public static boolean existFile(final @NonNull String pathname) throws FileException
    {
        File file = FileHelper.getFile(pathname);
        return file.isFile() && !file.isDirectory();
    }

    /**
     * Create a temporary file.
     * @return {@link Path} representing the temporary file path, <b>null</b> otherwise.
     * @throws FileException Thrown to indicate an error occurred while creating the temporary file.
     */
    public static Path createTemporaryFile() throws FileException
    {
        File file;
        Path path = null;

        try
        {
            if (SystemUtils.IS_OS_UNIX)
            {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(POSIX_FILE_ATTRIBUTES));
                path = Files.createTempFile(TEMPORARY_FILE_PREFIX, TEMPORARY_FILE_SUFFIX, attr);
            }
            else
            {
                path = Files.createTempFile(TEMPORARY_FILE_PREFIX, TEMPORARY_FILE_SUFFIX);
                file = path.toFile();
                if (!file.setReadable(true, true))
                {
                    throw new FileException(String.format("Cannot set file: %s as readable!", file.getPath()));
                }
                if (!file.setWritable(true, true))
                {
                    throw new FileException(String.format("Cannot set file: %s as writable!", file.getPath()));
                }
                if (!file.setExecutable(true, true))
                {
                    throw new FileException(String.format("Cannot set file: %s as executable!", file.getPath()));
                }
            }
        }
        catch (IOException e)
        {
            throw new FileException(e);
        }

        return path;
    }

    /**
     * Dump the content of a file to the console.
     * @param filename File name.
     * @throws FileException Thrown to indicate an error occurred while trying to access a file.
     */
    public static void dump(final @NonNull String filename) throws FileException
    {
        File file = FileHelper.getFile(filename);

        try
        {
            LOGGER.debug("File name is: ");
            LOGGER.debug(filename + "\n");
            LOGGER.debug("URL of file is: ");
            LOGGER.debug(file.toURI().toURL() + "\n");
            LOGGER.debug("File content is: ");
            Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(LOGGER::debug);
        }
        catch (IOException e)
        {
            throw new FileException(String.format("Cannot dump file: '%s' due to: %s", filename, e.getMessage()));
        }
    }
}
