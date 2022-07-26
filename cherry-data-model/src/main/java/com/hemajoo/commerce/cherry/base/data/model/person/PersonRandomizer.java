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
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberRandomizer;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.ArrayList;
import java.util.Date;
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
    private static final EnumRandomGenerator GENERATOR_PERSON_TYPE = new EnumRandomGenerator(PersonType.class);

    /**
     * Gender type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_GENDER_TYPE = new EnumRandomGenerator(GenderType.class);

    /**
     * Generate a random person without any document nor email address, postal address and phone number.
     * @param withRandomId Random identifier has to be generated?
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

        person.setFirstName(getRandomFirstName());
        person.setLastName(getRandomLastName());
        person.setBirthDate(FAKER.date().birthday(18, 70));
        person.setPersonType(getRandomPersonType());
        person.setGenderType(getRandomGenderType());

        return person;
    }

    /**
     * Generate a random person.
     * @param withRandomId Random identifier has to be generated?
     * @param withEmailAddress Email addresses have to be generated?
     * @param withPostalAddress Postal addresses have to be generated?
     * @param withPhoneNumber Phone number have to be generated?
     * @param withDocument Random documents have to be generated?
     * @param withContent Documents content (file) have to be attached to the document?
     * @param count Number of child entities to generate.
     * @return Random person.
     * @throws DataModelEntityException Thrown to indicate an error occurred while generating a data model entity.
     */
    public static IPerson generate(final boolean withRandomId, final boolean withEmailAddress, final boolean withPostalAddress, final boolean withPhoneNumber, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException
    {
        IPerson person = generate(withRandomId);

        if (withDocument)
        {
            for (int i = 0; i < count; i++)
            {
                person.addDocument(DocumentRandomizer.generate(withRandomId, withContent));
            }
        }

        for (var i = 0; i < count; i++)
        {
            if (withEmailAddress)
            {
                person.addEmailAddress(EmailAddressRandomizer.generate(withRandomId, withDocument, withContent, count));
            }
            if (withPostalAddress)
            {
                person.addPostalAddress(PostalAddressRandomizer.generate(withRandomId, withDocument, withContent, count));
            }
            if (withPhoneNumber)
            {
                person.addPhoneNumber(PhoneNumberRandomizer.generate(withRandomId, withDocument, withContent, count));
            }
        }

        return person;
    }

    /**
     * Generate a list of random person entities.
     * @param withRandomId Random identifier has to be generated?
     * @param withEmailAddress Email addresses have to be generated?
     * @param withPostalAddress Postal addresses have to be generated?
     * @param withPhoneNumber Phone number have to be generated?
     * @param withDocument Random documents have to be generated?
     * @param withContent Documents content (file) have to be attached to the document?
     * @param count Number of entities to generate.
     * @return List of random persons.
     * @throws DataModelEntityException Thrown to indicate an error occurred while generating a data model entity.
     */
    public static List<IPerson> generateList(final boolean withRandomId, final boolean withEmailAddress, final boolean withPostalAddress, final boolean withPhoneNumber, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException
    {
        List<IPerson> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generate(withRandomId, withEmailAddress, withPostalAddress, withPhoneNumber, withDocument, withContent, count));
        }

        return persons;
    }

    /**
     * Returns a random person type.
     * @return Person type.
     */
    public static PersonType getRandomPersonType()
    {
        return (PersonType) GENERATOR_PERSON_TYPE.gen();
    }

    /**
     * Returns a random gender type.
     * @return Gender type.
     */
    public static GenderType getRandomGenderType()
    {
        return (GenderType) GENERATOR_GENDER_TYPE.gen();
    }

    /**
     * Returns a random person first name.
     * @return First name.
     */
    public static String getRandomFirstName()
    {
        return FAKER.name().firstName().trim();
    }

    /**
     * Returns a random person last name.
     * @return Last name.
     */
    public static String getRandomLastName()
    {
        return FAKER.name().lastName().trim();
    }

    /**
     * Returns a random person birthdate.
     * @return Birthdate.
     */
    public static Date getRandomBirthDate()
    {
        return FAKER.date().birthday(18, 85);
    }
}
