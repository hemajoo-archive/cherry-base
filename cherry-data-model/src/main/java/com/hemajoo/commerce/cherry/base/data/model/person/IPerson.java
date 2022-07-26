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
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.IPostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.IPhoneNumber;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberException;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberType;
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

    // Email Address services

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
     * @throws DataModelEntityException Thrown in case an error occurred while delete email addresses.
     */
    void deleteAllEmailAddress() throws DataModelEntityException;

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
     * Return if the person has a default email address?
     * @return <b>True</b> if the person has a default email address, <b>false</b> otherwise.
     */
    boolean hasDefaultEmailAddress();

    /**
     * Return the number of email addresses.
     * @return Number of email addresses.
     */
    int getEmailAddressCount();

    /**
     * Retrieve a list of email addresses matching the given address type.
     * @param type Address type.
     * @return Set of matching email addresses.
     */
    Set<IEmailAddress> findEmailAddressByType(final AddressType type);

    /**
     * Retrieve a list of email addresses matching the given status type.
     * @param status Status type.
     * @return Set of matching email addresses.
     */
    Set<IEmailAddress> findEmailAddressByStatus(final EntityStatusType status);


    //
    // Postal Address services
    //

    /**
     * Add a postal address.
     * @param address Postal address.
     * @throws PostalAddressException Thrown in case an error occurred while trying to add a postal address.
     */
    void addPostalAddress(final @NonNull IPostalAddress address) throws DataModelEntityException;

    /**
     * Delete a postal address.
     * @param address Postal address.
     * @throws PostalAddressException Thrown in case an error occurred while trying to delete a postal address.
     */
    void deletePostalAddress(final @NonNull IPostalAddress address) throws PostalAddressException;

    /**
     * Delete a postal address given its identifier.
     * @param id Postal address identifier.
     * @throws PostalAddressException Thrown in case an error occurred while trying to delete a postal address.
     */
    void deletePostalAddressById(final @NonNull UUID id) throws PostalAddressException;

    /**
     * Delete a postal address given its identifier.
     * @param id Postal address identifier.
     * @throws PostalAddressException Thrown in case an error occurred while trying to delete a postal address.
     */
    void deletePostalAddressById(final @NonNull String id) throws PostalAddressException;

    /**
     * Delete all postal addresses.
     * @throws DataModelEntityException Thrown in case an error occurred while delete postal addresses.
     */
    void deleteAllPostalAddress() throws DataModelEntityException;

    /**
     * Check if a postal address exist.
     * @param address Postal address.
     * @return <b>True</b> if the postal address exist, <b>false</b> otherwise.
     * @throws PostalAddressException Thrown in case an error occurred while trying to check the postal address.
     */
    boolean existPostalAddress(final @NonNull IPostalAddress address) throws PostalAddressException;

    /**
     * Check if a postal address exist.
     * @param id Postal address identifier.
     * @return <b>True</b> if the postal address exist, <b>false</b> otherwise.
     * @throws PostalAddressException Thrown in case an error occurred while trying to check the postal address.
     */
    boolean existPostalAddressById(final @NonNull UUID id) throws PostalAddressException;

    /**
     * Check if a postal address exist.
     * @param id Postal address identifier.
     * @return <b>True</b> if the postal address exist, <b>false</b> otherwise.
     * @throws PostalAddressException Thrown in case an error occurred while trying to check the postal address.
     */
    boolean existPostalAddressById(final @NonNull String id) throws PostalAddressException;

    /**
     * Return an unmodifiable collection of postal addresses.
     * @return Postal addresses.
     */
    Set<IPostalAddress> getPostalAddresses();

    /**
     * Retrieve a postal address.
     * @param address Postal address.
     * @return Matching postal address, <b>null</b> otherwise.
     */
    IPostalAddress getPostalAddress(final @NonNull IPostalAddress address);

    /**
     * Retrieve a postal address.
     * @param id Postal address identifier.
     * @return Matching postal address, <b>null</b> otherwise.
     */
    IPostalAddress getPostalAddressById(final @NonNull UUID id);

    /**
     * Retrieve a postal address.
     * @param id Postal address identifier.
     * @return Matching postal address, <b>null</b> otherwise.
     */
    IPostalAddress getPostalAddressById(final @NonNull String id);

    /**
     * Return the default postal address.
     * @return Default postal address if one, <b>null</b> otherwise.
     */
    IPostalAddress getDefaultPostalAddress();

    /**
     * Return if the person has a default postal address?
     * @return <b>True</b> if the person has a default postal address, <b>false</b> otherwise.
     */
    boolean hasDefaultPostalAddress();

    /**
     * Return the number of postal addresses.
     * @return Number of postal addresses.
     */
    int getPostalAddressCount();

    /**
     * Retrieve postal addresses matching the given address type.
     * @param type Address type.
     * @return Set of matching postal addresses.
     */
    Set<IPostalAddress> findPostalAddressByType(final AddressType type);

    /**
     * Retrieve postal addresses matching the given status type.
     * @param status Status type.
     * @return Set of matching postal addresses.
     */
    Set<IPostalAddress> findPostalAddressByStatus(final EntityStatusType status);

    //
    // Phone Number services
    //

    /**
     * Add a phone number.
     * @param phone Phone number.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to add a phone number.
     */
    void addPhoneNumber(final @NonNull IPhoneNumber phone) throws DataModelEntityException;

    /**
     * Delete a phone number.
     * @param phone Phone number.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to delete a phone number.
     */
    void deletePhoneNumber(final @NonNull IPhoneNumber phone) throws PhoneNumberException;

    /**
     * Delete a phone number given its identifier.
     * @param id Phone number identifier.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to delete a phone number.
     */
    void deletePhoneNumberById(final @NonNull UUID id) throws PhoneNumberException;

    /**
     * Delete a phone number given its identifier.
     * @param id Phone number identifier.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to delete a phone number.
     */
    void deletePhoneNumberById(final @NonNull String id) throws PhoneNumberException;

    /**
     * Delete all phone numbers.
     * @throws DataModelEntityException Thrown in case an error occurred while delete email addresses.
     */
    void deleteAllPhoneNumber() throws DataModelEntityException;

    /**
     * Check if a phone number exist.
     * @param phone Phone number.
     * @return <b>True</b> if the phone number exist, <b>false</b> otherwise.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to check the phone number.
     */
    boolean existPhoneNumber(final @NonNull IPhoneNumber phone) throws PhoneNumberException;

    /**
     * Check if a phone number exist.
     * @param id Phone number identifier.
     * @return <b>True</b> if the phone number exist, <b>false</b> otherwise.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to check the phone number.
     */
    boolean existPhoneNumberById(final @NonNull UUID id) throws PhoneNumberException;

    /**
     * Check if a phone number exist.
     * @param id Phone number identifier.
     * @return <b>True</b> if the phone number exist, <b>false</b> otherwise.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to check the phone number.
     */
    boolean existPhoneNumberById(final @NonNull String id) throws PhoneNumberException;

    /**
     * Check if a phone number exist.
     * @param number Phone number.
     * @return <b>True</b> if the phone number exist, <b>false</b> otherwise.
     * @throws PhoneNumberException Thrown in case an error occurred while trying to check the phone number.
     */
    boolean existPhoneNumberByValue(final @NonNull String number) throws PhoneNumberException;

    /**
     * Return an unmodifiable collection of phone numbers.
     * @return Phone numbers.
     */
    Set<IPhoneNumber> getPhoneNumbers();

    /**
     * Retrieve a phone number.
     * @param address Phone number.
     * @return Matching phone number, <b>null</b> otherwise.
     */
    IPhoneNumber getPhoneNumber(final @NonNull IPhoneNumber address);

    /**
     * Retrieve a phone number.
     * @param id Phone number identifier.
     * @return Matching phone number, <b>null</b> otherwise.
     */
    IPhoneNumber getPhoneNumberById(final @NonNull UUID id);

    /**
     * Retrieve a phone number.
     * @param id Phone number identifier.
     * @return Matching phone number, <b>null</b> otherwise.
     */
    IPhoneNumber getPhoneNumberById(final @NonNull String id);

    /**
     * Return the default phone number.
     * @return Default phone number if one, <b>null</b> otherwise.
     */
    IPhoneNumber getDefaultPhoneNumber();

    /**
     * Return if the person has a default phone number?
     * @return <b>True</b> if the person has a default phone number, <b>false</b> otherwise.
     */
    boolean hasDefaultPhoneNumber();

    /**
     * Return the number of phone numbers.
     * @return Number of phone numbers.
     */
    int getPhoneNumberCount();

    /**
     * Retrieve phone numbers matching the given phone number type.
     * @param type Phone number type.
     * @return Set of matching phone numbers.
     */
    Set<IPhoneNumber> findPhoneNumberByType(final PhoneNumberType type);

    /**
     * Retrieve phone numbers matching the given status type.
     * @param status Status type.
     * @return Set of matching phone numbers.
     */
    Set<IPhoneNumber> findPhoneNumberByStatus(final EntityStatusType status);
}
