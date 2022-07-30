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
package com.hemajoo.commerce.cherry.base.data.model.base.random;

import com.github.javafaker.Faker;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentType;
import com.hemajoo.commerce.cherry.base.utilities.generator.EnumRandomGenerator;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
import com.hemajoo.commerce.cherry.base.utilities.helper.FileException;
import com.hemajoo.commerce.cherry.base.utilities.helper.FileHelper;
import com.hemajoo.commerce.cherry.base.utilities.helper.StringHelper;
import lombok.NonNull;

import java.io.File;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Provide an abstract implementation of a data model entity <b>randomizer</b> used to randomly generate data model entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractDataModelEntityRandomizer
{
    /**
     * Test file names.
     */
    private static final List<String> FILENAMES = new ArrayList<>();

    /**
     * Faker generator.
     */
    protected static final Faker FAKER = new Faker();

    /**
     * Random number generator.
     */
    protected static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Entity status type enumeration generator.
     */
    protected static final EnumRandomGenerator GENERATOR_STATUS_TYPE = new EnumRandomGenerator(EntityStatusType.class);

    /**
     * Document type enumeration generator.
     */
    protected static final EnumRandomGenerator GENERATOR_DOCUMENT_TYPE = new EnumRandomGenerator(DocumentType.class);

    /**
     * Creates a new base entity randomizer.
     */
    protected AbstractDataModelEntityRandomizer()
    {
        // Empty!
    }

    /**
     * Returns a random document type.
     * @return Document type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static DocumentType getRandomDocumentType() throws GeneratorException
    {
        return (DocumentType) GENERATOR_DOCUMENT_TYPE.generate();
    }

    /**
     * Returns a random entity sttaus type.
     * @return Status type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static EntityStatusType getRandomStatusType() throws GeneratorException
    {
        return (EntityStatusType) GENERATOR_STATUS_TYPE.generate();
    }

    /**
     * Returns a random name.
     * @return Name.
     */
    public static String getRandomName()
    {
        return FAKER.internet().slug().trim();
    }

    /**
     * Returns a random description.
     * @return Description.
     */
    public static String getRandomDescription()
    {
        return FAKER.ancient().primordial().trim();
    }

    /**
     * Returns a random reference.
     * @return Reference.
     */
    public static String getRandomReference()
    {
        return FAKER.internet().macAddress().trim();
    }

    /**
     * Returns a random tag.
     * @return Tag.
     */
    public static String getRandomTag()
    {
        return FAKER.animal().name().trim();
    }

    /**
     * Returns a set of random tags (from 1 to a maximum of 5) as a comma separated string.
     * @return Tags.
     */
    public static String getRandomTags()
    {
        return StringHelper.convertSetValuesAsString(getRandomTagList(), ",");
    }

    /**
     * Returns a collection of random tags.
     * @return Tags.
     */
    public static Set<String> getRandomTagList()
    {
        Set<String> tags = new HashSet<>();

        final int count = RANDOM.nextInt(1, 5);

        for (int i = 0; i < count; i++)
        {
            tags.add(getRandomTag());
        }

        return tags;
    }

    /**
     * Populate the base fields of a data model entity with random values.
     * @param parent Parent entity.
     * @param <T> Entity type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static <T extends IDataModelEntity> void populateBaseFields(final @NonNull T parent) throws GeneratorException
    {
        final Instant dateEnd = Instant.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse("1970-01-01 00:00:00.001 Europe/Paris", formatter);

        String description = FAKER.hitchhikersGuideToTheGalaxy().marvinQuote();

        if (description.length() > 255)
        {
            description = description.substring(1, 255);
        }

        parent.setDescription(description);
        parent.setReference(FAKER.ancient().hero());
        parent.setTags(FAKER.animal().name() + ", " + FAKER.animal().name());
        parent.setStatusType(getRandomStatusType());

        if (parent.getStatusType() == EntityStatusType.INACTIVE)
        {
            parent.setInactiveSince(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        }

        parent.setCreatedBy(FAKER.internet().emailAddress());
        parent.setModifiedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        parent.setModifiedDate(FAKER.date().between(parent.getCreatedDate(), Date.from(dateEnd)));
    }

    /**
     * Return a random element from a given list.
     * @param list List.
     * @param <T> Element type.
     * @return Random element.
     */
    public static <T> T getRandomElement(final List<T> list)
    {
        return list.get(RANDOM.nextInt(list.size()));
    }

    /**
     * Return a random test filename.
     * @return Test filename.
     * @throws DocumentException Thrown in case an error occurred when trying to get a random document filename.
     */
    public static String getRandomFilename() throws DocumentException
    {
        try
        {
            if (FILENAMES.isEmpty())
            {
                // Load the test files.
                File file = FileHelper.getFile("./media");
                for (File element : Objects.requireNonNull(file.listFiles()))
                {
                    FILENAMES.add(element.getAbsolutePath());
                }
            }
        }
        catch (FileException e)
        {
            throw new DocumentException(e);
        }

        return AbstractDataModelEntityRandomizer.getRandomElement(FILENAMES);
    }

    /**
     * Return a random integer.
     * @return Integer value.
     */
    public static boolean getRandomBoolean()
    {
        return RANDOM.nextBoolean();
    }

    /**
     * Return a random integer.
     * @return Integer value.
     */
    public static int getRandomInt()
    {
        return RANDOM.nextInt();
    }

    /**
     * Return a random integer.
     * @param min Minimal value.
     * @param max Maximal value.
     * @return Integer value.
     */
    public static int getRandomInt(final int min, final int max)
    {
        return RANDOM.nextInt(min, max);
    }

    /**
     * Return a random integer.
     * @param min Minimal value.
     * @return Integer value.
     */
    public static int getRandomInt(final int min)
    {
        return RANDOM.nextInt(min);
    }

    /**
     * Return a random long value.
     * @return Long value.
     */
    public static long getRandomLong()
    {
        return RANDOM.nextLong();
    }

    /**
     * Return a random long value.
     * @param min Minimal value.
     * @param max Maximal value.
     * @return Long value.
     */
    public static long getRandomLong(final long min, final long max)
    {
        return RANDOM.nextLong(min, max);
    }

    /**
     * Return a random long value.
     * @param min Minimal value.
     * @return Long value.
     */
    public static long getRandomLong(final long min)
    {
        return RANDOM.nextLong(min);
    }
}
