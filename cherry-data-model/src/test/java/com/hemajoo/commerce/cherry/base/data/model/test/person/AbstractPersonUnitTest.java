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
package com.hemajoo.commerce.cherry.base.data.model.test.person;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.configuration.DataModelConfiguration;
import com.hemajoo.commerce.cherry.base.data.model.person.GenderType;
import com.hemajoo.commerce.cherry.base.data.model.person.PersonRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.PersonType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.test.document.AbstractDocumentUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Abstract implementation of a unit test for a <b>person</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractPersonUnitTest extends AbstractDocumentUnitTest
{
    /**
     * Data model configuration.
     */
    @Autowired
    private DataModelConfiguration configuration;

    /**
     * First name.
     */
    protected String personFirstName;

    /**
     * Last name.
     */
    protected String personLastName;

    /**
     * Birthdate.
     */
    protected Date personBirthDate;

    /**
     * Person type.
     */
    protected PersonType personType;

    /**
     * Gender type.
     */
    protected GenderType genderType;

    /**
     * Email.
     */
    protected String emailAddress;

    /**
     * Address type.
     */
    protected AddressType emailAddressType;

    /**
     * Is default email address.
     */
    protected boolean emailIsDefault;

    @BeforeEach
    protected void beforeEach() throws DataModelEntityException
    {
        super.beforeEach();

        personFirstName = PersonRandomizer.getRandomFirstName();
        personLastName = PersonRandomizer.getRandomLastName();
        personBirthDate = PersonRandomizer.getRandomBirthDate();
        personType = PersonRandomizer.getRandomPersonType();
        genderType = PersonRandomizer.getRandomGenderType();
        emailAddress = EmailAddressRandomizer.getRandomEmail();
        emailAddressType = EmailAddressRandomizer.getRandomAddressType();
        emailIsDefault = EmailAddressRandomizer.getRandomIsDefault();
    }
}
