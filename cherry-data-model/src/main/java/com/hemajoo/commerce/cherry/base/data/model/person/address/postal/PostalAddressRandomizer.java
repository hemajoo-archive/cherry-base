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
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentContentException;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

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
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class);

    /**
     * Address category type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(PostalAddressCategoryType.class);

    /**
     * Generates a new random postal address.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * <br>Generally set to <b>true</b> for unit tests.
     * @return Postal address.
     */
    public static IPostalAddress generate(final boolean withRandomId)
    {
        IPostalAddress postal = new PostalAddress();
        populateBaseFields(postal);

        if (withRandomId)
        {
            postal.setId(UUID.randomUUID());
        }

        postal.setIsDefault(RANDOM.nextBoolean());
        postal.setStreetName(FAKER.address().streetName());
        postal.setStreetNumber(FAKER.address().streetAddressNumber());
        postal.setLocality(FAKER.address().cityName());
        postal.setArea(FAKER.address().state());
        String zipCode = FAKER.address().zipCode();
        postal.setZipCode(zipCode.length() <= 7 ? zipCode : zipCode.substring(0, 7));
        postal.setCountryCode(FAKER.address().countryCode().toUpperCase());
        postal.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        postal.setCategoryType((PostalAddressCategoryType) ADDRESS_CATEGORY_TYPE_GENERATOR.gen());

        return postal;
    }

    /**
     * Generates a new random postal address containing some random documents.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * <br>Generally set to <b>true</b> for unit tests.
     * @param count Number of random documents to generate.
     * @return Postal address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static IPostalAddress generateWithDocument(final boolean withRandomId, final int count) throws DataModelEntityException
    {
        IDocument document;

        IPostalAddress postal = new PostalAddress();
        populateBaseFields(postal);

        if (withRandomId)
        {
            postal.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generate(true);
            postal.addDocument(document);
        }

        postal.setIsDefault(RANDOM.nextBoolean());
        postal.setStreetName(FAKER.address().streetName());
        postal.setStreetNumber(FAKER.address().streetAddressNumber());
        postal.setLocality(FAKER.address().cityName());
        postal.setArea(FAKER.address().state());
        String zipCode = FAKER.address().zipCode();
        postal.setZipCode(zipCode.length() <= 7 ? zipCode : zipCode.substring(0, 7));
        postal.setCountryCode(FAKER.address().countryCode().toUpperCase());
        postal.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        postal.setCategoryType((PostalAddressCategoryType) ADDRESS_CATEGORY_TYPE_GENERATOR.gen());

        return postal;
    }
}
