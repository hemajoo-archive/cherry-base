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
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import com.hemajoo.commerce.cherry.base.data.model.person.GenderType;
import com.hemajoo.commerce.cherry.base.data.model.person.PersonRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.PersonType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.IPostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.*;
import com.hemajoo.commerce.cherry.base.data.model.test.document.AbstractDocumentUnitTest;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
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
     * Is default (email address).
     */
    protected boolean emailIsDefault;

    /**
     * Address type (email address).
     */
    protected AddressType emailAddressType;

    /**
     * Street name (postal address).
     */
    protected String streetName;

    /**
     * Street number (postal address).
     */
    protected String streetNumber;

    /**
     * Locality (postal address).
     */
    protected String locality;

    /**
     * Country code (postal address).
     */
    protected String countryCode;

    /**
     * Zip code (postal address).
     */
    protected String zipCode;

    /**
     * Area (postal address).
     */
    protected String area;

    /**
     * Is default (postal address).
     */
    protected boolean postalIsDefault;

    /**
     * Address type (postal address).
     */
    protected AddressType postalAddressType;

    /**
     * Postal address type (postal address).
     */
    protected PostalAddressType postalCategoryAddressType;

    /**
     * Number (phone number).
     */
    protected String phoneNumber;

    /**
     * Is default (phone number).
     */
    protected boolean phoneIsDefault;

    /**
     * Phone number type.
     */
    protected PhoneNumberType phoneNumberType;

    /**
     * Phone number category type.
     */
    protected PhoneNumberCategoryType phoneNumberCategoryType;


    @BeforeEach
    protected void beforeEach() throws DocumentException
    {
        super.beforeEach();

        // Person data
        personFirstName = PersonRandomizer.getRandomFirstName();
        personLastName = PersonRandomizer.getRandomLastName();
        personBirthDate = PersonRandomizer.getRandomBirthDate();

        try
        {
            personType = PersonRandomizer.getRandomPersonType();
            genderType = PersonRandomizer.getRandomGenderType();

            // Email address data
            emailAddress = EmailAddressRandomizer.getRandomEmail();
            emailAddressType = EmailAddressRandomizer.getRandomAddressType();
            emailIsDefault = EmailAddressRandomizer.getRandomIsDefault();

            // Postal address data
            streetName = PostalAddressRandomizer.getRandomStreetName();
            streetNumber = PostalAddressRandomizer.getRandomStreetNumber();
            locality = PostalAddressRandomizer.getRandomLocality();
            countryCode = PostalAddressRandomizer.getRandomCountryCode();
            zipCode = PostalAddressRandomizer.getRandomZipCode();
            area = PostalAddressRandomizer.getRandomArea();
            postalIsDefault = PostalAddressRandomizer.getRandomIsDefault();
            postalAddressType = PostalAddressRandomizer.getRandomAddressType();
            postalCategoryAddressType = PostalAddressRandomizer.getRandomPostalAddressType();

            // Phone number data
            phoneNumber = PhoneNumberRandomizer.getRandomNumber();
            phoneIsDefault = PhoneNumberRandomizer.getRandomIsDefault();
            phoneNumberType = PhoneNumberRandomizer.getRandomPhoneNumberType();
            phoneNumberCategoryType = PhoneNumberRandomizer.getRandomPhoneNumberCategoryType();
        }
        catch (GeneratorException e)
        {
            throw new DocumentException(e);
        }
    }

    /**
     * Generate a lightweight random email address.
     * @return Postal address.
     * @throws DataModelEntityException Thrown in case an error occurred while generating a random postal address.
     */
    protected IEmailAddress generateEmailAddress() throws DataModelEntityException
    {
        return EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .build();
    }

    /**
     * Generate a lightweight random postal address.
     * @return Postal address.
     * @throws DataModelEntityException Thrown in case an error occurred while generating a random postal address.
     */
    protected IPostalAddress generatePostalAddress() throws DataModelEntityException
    {
        return PostalAddress.builder()
                .withStreetName(PostalAddressRandomizer.getRandomStreetName())
                .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber())
                .withLocality(PostalAddressRandomizer.getRandomLocality())
                .withCountryCode(PostalAddressRandomizer.getRandomCountryCode())
                .withZipCode(PostalAddressRandomizer.getRandomZipCode())
                .build();
    }

    /**
     * Generate a lightweight random phone number.
     * @return Phone number.
     * @throws DataModelEntityException Thrown in case an error occurred while generating a random phone number.
     */
    protected IPhoneNumber generatePhoneNumber() throws DataModelEntityException
    {
        return PhoneNumber.builder()
            .withNumber(PhoneNumberRandomizer.getRandomNumber())
            .withCountryCode(PhoneNumberRandomizer.getRandomCountryCode())
            .build();
    }
}
