/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.base.data.model.person;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberRandomizer;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility class providing services to randomly generate <b>person</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PersonRandomizer extends AbstractDataModelEntityRandomizer
{
    /**
     * Person type enumeration generator.
     */
    private static final EnumRandomGenerator PERSON_TYPE_GENERATOR = new EnumRandomGenerator(PersonType.class);

    /**
     * Gender type enumeration generator.
     */
    private static final EnumRandomGenerator GENDER_TYPE_GENERATOR = new EnumRandomGenerator(GenderType.class);

    /**
     * Generates a list of new random person entities.
     * @param withRandomId Does a random identifier has to be generated?
     * @param count Number of persons to generate.
     * @return List of random persons.
     */
    public static List<IPerson> generateMultiple(final boolean withRandomId, final int count)
    {
        List<IPerson> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generate(withRandomId));
        }

        return persons;
    }

    /**
     * Generates a new random person entity.
     * @param withRandomId Does a random identifier has to be generated?
     * @return Random person.
     */
    public static IPerson generate(final boolean withRandomId)
    {
        IPerson person = new Person();
        populateBaseFields(person);

        if (withRandomId)
        {
            person.setId(UUID.randomUUID());
        }

        person.setFirstName(FAKER.name().firstName());
        person.setLastName(FAKER.name().lastName());
        person.setBirthDate(FAKER.date().birthday(18, 70));
        person.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        person.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return person;
    }

    /**
     * Generates a list of new random person entities.
     * @param withRandomId Does a random identifier has to be generated?
     * @param count Number of persons and documents to generate.
     * @return List of random persons.
     * @throws NoSuchAlgorithmException Thrown in case an error occurred while getting a random number.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static List<IPerson> generateMultipleWithDocuments(final boolean withRandomId, final int count) throws DataModelEntityException, NoSuchAlgorithmException
    {
        List<IPerson> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generateWithDocument(withRandomId, SecureRandom.getInstanceStrong().nextInt(10)));
        }

        return persons;
    }

    /**
     * Generates a random person entity.
     * @param withRandomId Does a random identifier has to be generated?
     * @param count Number of random documents to generate.
     * @return Person.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static IPerson generateWithDocument(final boolean withRandomId, final int count) throws DataModelEntityException
    {
        IDocument document;

        IPerson person = new Person();
        populateBaseFields(person);

        if (withRandomId)
        {
            person.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generate(true);
            person.addDocument(document);
        }

        person.setFirstName(FAKER.name().firstName());
        person.setLastName(FAKER.name().lastName());
        person.setBirthDate(FAKER.date().birthday(18, 70));
        person.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        person.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return person;
    }

    /**
     * Create a random person with its dependencies (i.e: email addresses, postal addresses and phone numbers).
     * @param withRandomId Does a random identifier has to be generated?
     * @param bound Maximum number of dependencies to generate.
     * @param withEmailAddress If set to <b>true</b>, some random email addresses will be added to the person.
     * @param withPostalAddress If set to <b>true</b>, some random postal addresses will be added to the person.
     * @param withPhoneNumber If set to <b>true</b>, some random phone numbers will be added to the person.
     * @param withDocuments If set to <b>true</b>, some random documents will be added to each entity.
     * @return Person.
     * @throws EmailAddressException Thrown in case an error occurred while trying to generate a document.
     */
    public static IPerson generateWithDependencies(final boolean withRandomId, final int bound, final boolean withEmailAddress, final boolean withPostalAddress, final boolean withPhoneNumber, final boolean withDocuments) throws DataModelEntityException
    {
        IPerson person = generate(withRandomId);

        int count = bound > 0 ? bound : AbstractDataModelEntityRandomizer.DEFAULT_DEPENDENCY_BOUND;
        for (var i = 0; i < count; i++)
        {
            if (withEmailAddress)
            {
                if (!withDocuments)
                {
                    person.addEmailAddress(EmailAddressRandomizer.generate(withRandomId));
                }
                else
                {
                    person.addEmailAddress(EmailAddressRandomizer.generateWithDocument(withRandomId, count));
                }
            }
            if (withPostalAddress)
            {
                if (!withDocuments)
                {
                    person.addPhoneNumber(PhoneNumberRandomizer.generate(withRandomId));
                }
                else
                {
                    person.addPhoneNumber(PhoneNumberRandomizer.generateWithDocument(withRandomId, count));
                }
            }
            if (withPhoneNumber)
            {
                if (!withDocuments)
                {
                    person.addPostalAddress(PostalAddressRandomizer.generate(withRandomId));
                }
                else
                {
                    person.addPostalAddress(PostalAddressRandomizer.generateWithDocument(withRandomId, count));
                }
            }
        }

        return person;
    }
}
