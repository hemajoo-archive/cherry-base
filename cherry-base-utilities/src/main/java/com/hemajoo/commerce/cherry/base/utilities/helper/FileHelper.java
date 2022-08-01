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
package com.hemajoo.commerce.cherry.base.utilities.helper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
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
@UtilityClass
public class FileHelper
{
    /**
     * POSIX file attribute for temporary file.
     */
    private static final String POSIX_FILE_ATTRIBUTES = "rwx------";

    /**
     * Temporary dedicated folder.
     */
    private static final String TEMPORARY_FOLDER = "/folder";

    /**
     * Temporary file prefix.
     */
    private static final String TEMPORARY_FILE_PREFIX = "_file";

    /**
     * Temporary file suffix.
     */
    private static final String TEMPORARY_FILE_SUFFIX = ".file";

    /**
     * Creates the necessary directory file structure contained in the given file path.
     * @param file File.
     * @return Path.
     */
    public static Path createFileWithDirs(final @NonNull File file)
    {
        return createFileWithDirs(file.getPath());
    }

    /**
     * Creates the necessary directory file structure contained in the given file path.
     * @param filePath Complete file path and name.
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
     * Returns a file given its filename.
     * <br>
     * This service is able to retrieve a file from the classpath, from a jar file or from an url.
     * @param filename File name to retrieve.
     * @param type Class type to use to load the file.
     * @return {@link File} representing the retrieved file.
     */
    public static File getFile(final @NonNull String filename, final @NonNull Class<?> type)
    {
        File file;
        boolean isTemporaryFile = false;

        // 1 Try to load the given file from the file system
        file = new File(filename);
        if (file.isFile() && !file.isDirectory())
        {
            return file;
        }

        // Try to load the file from a JAR file.
        try
        {
            URL url = type.getClassLoader().getResource(filename);
            if (Objects.requireNonNull(url).toString().startsWith("jar:"))
            {
                // No chance to get a file handle if the file is located inside a jar!
                // Throw an exception and force the creation of a temporary file.
                throw new FileException("");
            }

            // Try to load the file from the classpath.
            String path = Objects.requireNonNull(url).getFile();
            file = new File(path);
        }
        catch (Exception e)
        {
            try
            {
                // If not successful, then try to load it from a JAR file.
//                @Cleanup
//                InputStream stream = FileHelper.class.getResourceAsStream(filename);
                Path path = createTemporaryFile();
                Files.copy(Objects.requireNonNull(FileHelper.class.getResourceAsStream(filename)), path, StandardCopyOption.REPLACE_EXISTING);
                file = path.toFile();
                isTemporaryFile = true;
            }
            catch (Exception ex)
            {
                try
                {
                    // Still not successful, then try to load it from an URL.
//                    @Cleanup
//                    InputStream stream = new URL(filename).openStream();
                    Path path = createTemporaryFile();
                    Files.copy(new URL(filename).openStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    file = path.toFile();
                    isTemporaryFile = true;
                }
                catch (Exception exception)
                {
                    // Try to load it from the file system.
                    file = new File(filename);
                }
            }
            finally
            {
                if (isTemporaryFile)
                {
                    file.deleteOnExit();
                }
            }
        }

        return file;
    }

    /**
     * Returns a file given its filename.
     * <br>
     * This service is able to retrieve a file from the classpath, from a jar file or from an url.
     * @param filename File name to retrieve.
     * @return {@link File} representing the retrieved file.
     * @throws FileException Thrown when an error occurred while trying to retrieve the file.
     */
    public static File getFile(final @NonNull String filename) throws FileException
    {
        return getFile(filename, FileHelper.class);
    }

    /**
     * Loads the content of a text file.
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
     * Loads a text file given its filename.
     * @param filename File name to load.
     * @return List of lines.
     * @throws FileException Thrown when an error occurred while trying to load the file.
     */
    public static List<String> loadFileContentAsList(final @NonNull String filename) throws FileException
    {
        return Arrays.asList(loadFileContentAsString(filename).split("\\n"));
    }

    /**
     * Checks if the given file name exist?
     * @param pathname File name to check.
     * @return True if the file exist, false otherwise.
     */
    public static boolean existFile(final @NonNull String pathname)
    {
        try
        {
            File file = FileHelper.getFile(pathname);
            if(file.isFile() && !file.isDirectory())
            {
                return true;
            }
        }
        catch (FileException e)
        {
            return false;
        }

        return false;
    }

    /**
     *
     * @param filename
     */
    private File loadFileFromJar(final @NonNull String filename)
    {
        File file = null;
        Path path;
        boolean isTemporaryFile = false;

        try
        {
            path = createTemporaryFile();
            Files.copy(Objects.requireNonNull(FileHelper.class.getResourceAsStream(filename)), path, StandardCopyOption.REPLACE_EXISTING);
            file = path.toFile();
            isTemporaryFile = true;
        }
        catch (IOException ex)
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
     *
     * @return
     * @throws IOException
     */
    public static Path createTemporaryFile() throws IOException
    {
        File file;
        Path path;

        if (SystemUtils.IS_OS_UNIX)
        {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(POSIX_FILE_ATTRIBUTES));
            path = Files.createTempFile(TEMPORARY_FILE_PREFIX, TEMPORARY_FILE_SUFFIX, attr);
        }
        else
        {
            path = Files.createTempFile(TEMPORARY_FILE_PREFIX, TEMPORARY_FILE_SUFFIX);
            file = path.toFile();
            file.setReadable(true, true);
            file.setWritable(true, true);
            file.setExecutable(true, true);
        }

        return path;
    }
}
