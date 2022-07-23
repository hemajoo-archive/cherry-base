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
package com.hemajoo.commerce.cherry.base.data.model.person.address.email;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Utility class providing services to randomly generate <b>email address</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class EmailAddressRandomizer extends AbstractDataModelEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator GENERATOR_ADDRESS_TYPE = new EnumRandomGenerator(AddressType.class);

    /**
     * Generate a random email address.
     * @param withRandomId Does a random entity identifier has to be generated?
     * @param withDocument Does a random document has to be generated?
     * @param withContent Does a random content (file) has to be attached to the document?
     * @param count Number of documents to generate.
     * @return Email address.
     * @throws DataModelEntityException Thrown to indicate an error occurred while generating a data model entity.
     */
    public static IEmailAddress generate(final boolean withRandomId, final boolean withDocument, final boolean withContent, final int count) throws DataModelEntityException
    {
        IEmailAddress emailAddress = new EmailAddress();
        populateBaseFields(emailAddress);

        if (withRandomId)
        {
            emailAddress.setId(UUID.randomUUID());
        }

        if (withDocument)
        {
            for (int i = 0; i < count; i++)
            {
                emailAddress.addDocument(DocumentRandomizer.generate(withRandomId, withContent));
            }
        }

        emailAddress.setEmail(getRandomEmail());
        emailAddress.setAddressType(getRandomAddressType());
        emailAddress.setIsDefaultEmail(getRandomIsDefault());

        return emailAddress;
    }

    /**
     * Returns a random email address.
     * @return Email address.
     */
    public static String getRandomEmail()
    {
        return FAKER.internet().emailAddress().trim();
    }

    /**
     * Returns a random address type.
     * @return Address type.
     */
    public static AddressType getRandomAddressType()
    {
        return (AddressType) GENERATOR_ADDRESS_TYPE.gen();
    }

    /**
     * Returns a random is a default email address.
     * @return Is default email address.
     */
    public boolean getRandomIsDefault()
    {
        return RANDOM.nextBoolean();
    }
}
