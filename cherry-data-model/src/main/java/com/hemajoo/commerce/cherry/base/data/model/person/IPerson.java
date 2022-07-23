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
package com.hemajoo.commerce.cherry.base.data.model.person;

import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.IPostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.IPhoneNumber;
import lombok.NonNull;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Provide services to manipulate the data composing a <b>person</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IPerson extends IDataModelEntity
{
    /**
     * Person attribute: <b>last name</b>.
     */
    String PERSON_LASTNAME = "lastName";

    /**
     * Person attribute: <b>first name</b>.
     */
    String PERSON_FIRSTNAME = "firstName";

    /**
     * Person attribute: <b>birth date</b>.
     */
    String PERSON_BIRTHDATE = "birthDate";

    /**
     * Person attribute: <b>gender type</b>.
     */
    String PERSON_GENDER_TYPE = "genderType";

    /**
     * Person attribute: <b>person type</b>.
     */
    String PERSON_PERSON_TYPE = "personType";

    /**
     * Return the person last name.
     * @return Last name.
     */
    String getLastName();

    /**
     * Set the person last name.
     * @param lastName Last name.
     */
    void setLastName(final String lastName);

    /**
     * Return the person first name.
     * @return First name.
     */
    String getFirstName();

    /**
     * Set the person first name.
     * @param firstName First name.
     */
    void setFirstName(final String firstName);

    /**
     * Return the person birthdate.
     * @return Birthdate.
     */
    Date getBirthDate();

    /**
     * Set the person birthdate.
     * @param birthDate Birthdate.
     */
    void setBirthDate(final Date birthDate);

    /**
     * Return the person type.
     * @return Person type.
     */
    PersonType getPersonType();

    /**
     * Set the person type.
     * @param type Person type.
     */
    void setPersonType(final PersonType type);

    /**
     * Return the person gender type.
     * @return Gender type.
     */
    GenderType getGenderType();

    /**
     * Set the person gender type.
     * @param type Gender type.
     */
    void setGenderType(final GenderType type);

    /**
     * Add an email address.
     * @param email Email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to add an email address.
     */
    void addEmailAddress(final @NonNull IEmailAddress email) throws EmailAddressException;

    /**
     * Delete an email address.
     * @param email Email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to delete an email address.
     */
    void deleteEmailAddress(final @NonNull IEmailAddress email) throws DataModelEntityException;

    /**
     * Delete an email address given its identifier.
     * @param id Email address identifier.
     * @throws EmailAddressException Thrown in case an error occurred while trying to delete an email address.
     */
    void deleteEmailAddressById(final @NonNull UUID id) throws DataModelEntityException;

    /**
     * Delete an email address given its identifier.
     * @param id Email address identifier.
     * @throws EmailAddressException Thrown in case an error occurred while trying to delete an email address.
     */
    void deleteEmailAddressById(final @NonNull String id) throws DataModelEntityException;

    /**
     * Delete an email address given its email address value.
     * @param email Email address value.
     * @throws EmailAddressException Thrown in case an error occurred while trying to delete an email address.
     */
    void deleteEmailAddressByValue(final @NonNull String email) throws DataModelEntityException;

    /**
     * Delete all email addresses.
     */
    void deleteAllEmailAddress();

    /**
     * Check if an email address exist.
     * @param email Email address.
     * @return <b>True</b> if the email address exist, <b>false</b> otherwise.
     * @throws EmailAddressException Thrown in case an error occurred while trying to check the email address.
     */
    boolean existEmailAddress(final @NonNull IEmailAddress email) throws EmailAddressException;

    /**
     * Check if an email address exist.
     * @param id Email address identifier.
     * @return <b>True</b> if the email address exist, <b>false</b> otherwise.
     * @throws EmailAddressException Thrown in case an error occurred while trying to check the email address.
     */
    boolean existEmailAddressById(final @NonNull UUID id) throws EmailAddressException;

    /**
     * Check if an email address exist.
     * @param id Email address identifier.
     * @return <b>True</b> if the email address exist, <b>false</b> otherwise.
     * @throws EmailAddressException Thrown in case an error occurred while trying to check the email address.
     */
    boolean existEmailAddressById(final @NonNull String id) throws EmailAddressException;

    /**
     * Check if an email address exist.
     * @param email Email address value.
     * @return <b>True</b> if the email address exist, <b>false</b> otherwise.
     * @throws EmailAddressException Thrown in case an error occurred while trying to check the email address.
     */
    boolean existEmailAddressByValue(final @NonNull String email) throws EmailAddressException;

    /**
     * Return an unmodifiable collection of email addresses.
     * @return Email addresses.
     */
    Set<IEmailAddress> getEmailAddresses();

    /**
     * Retrieve an email address.
     * @param email Email address.
     * @return Matching email address, <b>null</b> otherwise.
     */
    IEmailAddress getEmailAddress(final @NonNull IEmailAddress email);

    /**
     * Retrieve an email address.
     * @param id Email address identifier.
     * @return Matching email address, <b>null</b> otherwise.
     */
    IEmailAddress getEmailAddressById(final @NonNull UUID id);

    /**
     * Retrieve an email address.
     * @param id Email address identifier.
     * @return Matching email address, <b>null</b> otherwise.
     */
    IEmailAddress getEmailAddressById(final @NonNull String id);

    /**
     * Retrieve an email address.
     * @param value Email address value.
     * @return Matching email address, <b>null</b> otherwise.
     */
    IEmailAddress getEmailAddressByValue(final @NonNull String value);

    /**
     * Return the default email address.
     * @return Default email address if one, <b>null</b> otherwise.
     */
    IEmailAddress getDefaultEmailAddress();

    /**
     * Return if a default email address exist?
     * @return <b>True</b> if a default email address exist, <b>false</b> otherwise.
     */
    boolean hasDefaultEmailAddress();

    /**
     * Return the number of email addresses.
     * @return Number of email addresses.
     */
    int getEmailAddressCount();

    /**
     * Add a postal address.
     * @param postal Postal address.
     * @throws DataModelEntityException Thrown in case an error occurred while trying to add a postal address.
     */
    void addPostalAddress(final @NonNull IPostalAddress postal) throws DataModelEntityException;

    /**
     * Return an unmodifiable collection of postal addresses.
     * @return Postal addresses.
     */
    Set<IPostalAddress> getPostalAddresses();

    /**
     * Return the default postal address.
     * @return Default postal address if one, <b>null</b> otherwise.
     */
    IPostalAddress getDefaultPostalAddress();

    /**
     * Return the number of postal addresses.
     * @return Number of postal addresses.
     */
    int getPostalAddressCount();

    /**
     * Return an unmodifiable collection of phone numbers.
     * @return Phone numbers.
     */
    Set<IPhoneNumber> getPhoneNumbers();

    /**
     * Add a phone number.
     * @param phone Phone number.
     * @throws DataModelEntityException Thrown in case an error occurred while trying to add a phone number.
     */
    void addPhoneNumber(final @NonNull IPhoneNumber phone) throws DataModelEntityException;

    /**
     * Return the number of phone numbers.
     * @return Number of phone numbers.
     */
    int getPhoneNumberCount();
}
