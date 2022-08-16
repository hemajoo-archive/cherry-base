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
package com.hemajoo.commerce.cherry.base.data.model.person.address.postal;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.utilities.generator.EnumRandomGenerator;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
import com.hemajoo.commerce.cherry.base.utilities.generator.RandomGenerator;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Utility class providing services to randomly generate <b>postal address</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PostalAddressRandomizer extends AbstractDataModelEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_ADDRESS_TYPE = new EnumRandomGenerator(AddressType.class);

    /**
     * Address category type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_POSTAL_ADDRESS_TYPE = new EnumRandomGenerator(PostalAddressType.class);

    /**
     * Generate a random postal address without any document.
     * @param withRandomId Does a random identifier has to be generated?
     * @param isDefault Is a default postal address?
     * @return Postal address.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static IPostalAddress generate(final boolean withRandomId, final boolean isDefault) throws GeneratorException
    {
        IPostalAddress postal = new PostalAddress();
        populateBaseFields(postal);

        if (withRandomId)
        {
            postal.setId(UUID.randomUUID());
        }

        postal.setStreetName(getRandomStreetName());
        postal.setStreetNumber(getRandomStreetNumber());
        postal.setLocality(getRandomLocality());
        postal.setArea(getRandomArea());
        String zipCode = getRandomZipCode();
        postal.setZipCode(zipCode.length() <= 7 ? zipCode : zipCode.substring(0, 7));
        postal.setCountryCode(getRandomCountryCode().toUpperCase());
        postal.setAddressType(getRandomAddressType());
        postal.setPostalAddressType(getRandomPostalAddressType());
        postal.setIsDefault(isDefault);

        if (postal.getName() == null)
        {
            postal.setName(postal.getStreetNumber() + ", " + postal.getStreetName() + " " + zipCode + " " + postal.getLocality() + " - " + postal.getCountryCode());
        }

        return postal;
    }

    /**
     * Generate a random postal address.
     * @param withRandomId Does a random identifier has to be generated?
     * @param withDocument Does a random document has to be generated?
     * @param withContent Does a random content (file) has to be attached to the document?
     * @param count Number of documents to generate.
     * @return Postal address.
     * @throws DataModelEntityException Thrown to indicate an error occurred while generating a data model entity.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static IPostalAddress generate(final boolean withRandomId, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postal = generate(withRandomId, false);

        if (withDocument)
        {
            for (int i = 0; i < count; i++)
            {
                postal.addDocument(DocumentRandomizer.generate(withRandomId, withContent));
            }
        }

        return postal;
    }

    /**
     * Returns a random postal address street name.
     * @return Street name.
     */
    public static String getRandomStreetName()
    {
        return FAKER.address().streetName().trim();
    }

    /**
     * Returns a random postal address street number.
     * @return Street number.
     */
    public static String getRandomStreetNumber()
    {
        return FAKER.address().streetAddressNumber().trim();
    }

    /**
     * Returns a random postal address locality.
     * @return Locality.
     */
    public static String getRandomLocality()
    {
        return FAKER.address().cityName();
    }

    /**
     * Returns a random postal address country code.
     * @return Country code.
     */
    public static String getRandomCountryCode()
    {
        return FAKER.address().countryCode().trim();
    }

    /**
     * Returns a random postal address zip code.
     * @return Zip code.
     */
    public static String getRandomZipCode()
    {
        return FAKER.address().zipCode().trim();
    }

    /**
     * Returns a random postal address area.
     * @return Area.
     */
    public static String getRandomArea()
    {
        return FAKER.address().state().trim();
    }

    /**
     * Returns a random postal address is default.
     * @return Is default.
     */
    public static boolean getRandomIsDefault()
    {
        return RandomGenerator.nextBoolean();
    }

    /**
     * Returns a random address type.
     * @return Address type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static AddressType getRandomAddressType() throws GeneratorException
    {
        return (AddressType) GENERATOR_ADDRESS_TYPE.generate();
    }

    /**
     * Returns a random postal address type.
     * @return Postal address type.
     * @throws GeneratorException Thrown to indicate an error occurred trying to generate a random value.
     */
    public static PostalAddressType getRandomPostalAddressType() throws GeneratorException
    {
        return (PostalAddressType) GENERATOR_POSTAL_ADDRESS_TYPE.generate();
    }
}
