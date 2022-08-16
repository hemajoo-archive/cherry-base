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
package com.hemajoo.commerce.cherry.base.data.model.person.phone;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.utilities.generator.EnumRandomGenerator;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
import com.hemajoo.commerce.cherry.base.utilities.generator.RandomGenerator;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Utility class providing services to randomly generate <b>phone number</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PhoneNumberRandomizer extends AbstractDataModelEntityRandomizer
{
    /**
     * Phone number type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_PHONE_NUMBER_TYPE = new EnumRandomGenerator(PhoneNumberType.class);

    /**
     * Phone number category type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_PHONE_NUMBER_CATEGORY_TYPE = new EnumRandomGenerator(PhoneNumberCategoryType.class);

    /**
     * Generate a random phone number without any document.
     * @param withRandomId Does a random identifier has to be generated?
     * @param isDefault Is a default phone number?
     * @return Phone number.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static IPhoneNumber generate(final boolean withRandomId, final boolean isDefault) throws GeneratorException
    {
        IPhoneNumber phone = new PhoneNumber();
        populateBaseFields(phone);

        if (withRandomId)
        {
            phone.setId(UUID.randomUUID());
        }

        phone.setNumber(getRandomNumber());
        phone.setCountryCode(getRandomCountryCode());
        phone.setPhoneType(getRandomPhoneNumberType());
        phone.setCategoryType(getRandomPhoneNumberCategoryType());
        phone.setIsDefault(isDefault);

        if (phone.getName() == null)
        {
            phone.setName("+" + phone.getCountryCode() + " " + phone.getNumber());
        }

        return phone;
    }

    /**
     * Generate a random phone number.
     * @param withRandomId Does a random identifier has to be generated?
     * @param withDocument Does a random document has to be generated?
     * @param withContent Does a random content (file) has to be attached to the document?
     * @param count Number of documents to generate.
     * @return Phone number.
     * @throws DataModelEntityException Thrown to indicate an error occurred while generating a data model entity.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static IPhoneNumber generate(final boolean withRandomId, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phone = generate(withRandomId, false);
        populateBaseFields(phone);

        if (withRandomId)
        {
            phone.setId(UUID.randomUUID());
        }

        if (withDocument)
        {
            for (int i = 0; i < count; i++)
            {
                phone.addDocument(DocumentRandomizer.generate(withRandomId, withContent));
            }
        }

        return phone;
    }

    /**
     * Returns a random phone number.
     * @return Number.
     */
    public static String getRandomNumber()
    {
        return FAKER.phoneNumber().cellPhone().trim();
    }

    /**
     * Returns a random phone country code.
     * @return Country code.
     */
    public static String getRandomCountryCode()
    {
        return FAKER.address().countryCode().trim();
    }

    /**
     * Returns a random phone number is default.
     * @return Is default.
     */
    public static boolean getRandomIsDefault()
    {
        return RandomGenerator.nextBoolean();
    }

    /**
     * Returns a random phone number type.
     * @return Phone number type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static PhoneNumberType getRandomPhoneNumberType() throws GeneratorException
    {
        return (PhoneNumberType) GENERATOR_PHONE_NUMBER_TYPE.generate();
    }

    /**
     * Returns a random phone number category type.
     * @return Phone number category type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static PhoneNumberCategoryType getRandomPhoneNumberCategoryType() throws GeneratorException
    {
        return (PhoneNumberCategoryType) GENERATOR_PHONE_NUMBER_CATEGORY_TYPE.generate();
    }
}
