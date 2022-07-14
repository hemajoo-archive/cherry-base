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
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

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
    private static final EnumRandomGenerator PHONE_NUMBER_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberType.class);

    /**
     * Phone number category type enumeration generator.
     */
    private static final EnumRandomGenerator PHONE_NUMBER_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberCategoryType.class);

    /**
     * Generate a random phone number.
     * @param withRandomId Does a random identifier has to be generated?
     * @param withDocument Does a random document has to be generated?
     * @param withContent Does a random content (file) has to be attached to the document?
     * @return Phone number.
     */
    public static IPhoneNumber generate(final boolean withRandomId, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException
    {
        IPhoneNumber phone = new PhoneNumber();
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

        phone.setNumber(FAKER.phoneNumber().cellPhone());
        phone.setCountryCode(FAKER.address().countryCode().toUpperCase());
        phone.setPhoneType((PhoneNumberType) PHONE_NUMBER_TYPE_GENERATOR.gen());
        phone.setCategoryType((PhoneNumberCategoryType) PHONE_NUMBER_CATEGORY_TYPE_GENERATOR.gen());
        phone.setIsDefault(RANDOM.nextBoolean());

        return phone;
    }
}
