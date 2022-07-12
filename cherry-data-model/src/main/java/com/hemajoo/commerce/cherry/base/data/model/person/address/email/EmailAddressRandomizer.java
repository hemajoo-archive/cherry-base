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
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentContentException;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
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
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class);

    /**
     * Generates a new random email address.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * <br>Generally set to <b>true</b> for unit tests.
     * @return Email address.
     */
    public static IEmailAddress generate(final boolean withRandomId)
    {
        IEmailAddress entity = new EmailAddress();
        populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setIsDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Generates a new random email address containing some random documents.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * <br>Generally set to <b>true</b> for unit tests.
     * @param count Number of random documents to generate.
     * @return Email address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static IEmailAddress generateWithDocument(final boolean withRandomId, final int count) throws DataModelEntityException
    {
        IDocument document;
        IEmailAddress email = generate(withRandomId);

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generate(true);
            email.addDocument(document);
        }

        return email;
    }
}
