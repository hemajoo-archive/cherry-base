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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.base.data.model.base.DataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.IPostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.IPhoneNumber;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumber;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberType;
import dev.fuxing.hibernate.ValidEnum;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a <b>person</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SuppressWarnings("java:S6204")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Hidden
@Table(name = "PERSON")
@EntityListeners(AuditingEntityListener.class)
public class Person extends DataModelEntity implements IPerson
{
    /**
     * Person last name.
     */
    @Getter
    @NotNull
    @NotBlank
    @Column(name = "LASTNAME")
    private String lastName;

    /**
     * Person first name.
     */
    @Getter
    @NotNull
    @NotBlank
    @Column(name = "FIRSTNAME")
    private String firstName;

    /**
     * Person birthdate.
     */
    @Getter
    @Setter
    @Column(name = "BIRTHDATE")
    private Date birthDate;

    /**
     * Person type.
     */
    @Getter
    @Setter
    @ValidEnum(message = "{javax.validation.constraints.ValidEnum.message}")
    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", length = 50)
    private PersonType personType;

    /**
     * Person gender type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER_TYPE", length = 50)
    private GenderType genderType;

    /**
     * Postal addresses associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = PostalAddress.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostalAddress> postalAddresses = new ArrayList<>();

    /**
     * Phone numbers associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = PhoneNumber.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    /**
     * Email addresses associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("owner")
//    @OneToMany(targetEntity = ServerEmailAddressEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(targetEntity = EmailAddress.class, mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailAddress> emailAddresses = new ArrayList<>();

    /**
     * Create a new person.
     */
    public Person()
    {
        super(EntityType.PERSON);
    }

    /**
     * Create a new person.
     * @param lastName Last name.
     * @param firstName First name.
     * @param birthDate Birthdate.
     * @param name Name.
     * @param description Description.
     * @param reference Reference.
     * @param personType Person type.
     * @param genderType Gender type.
     * @param statusType Status type.
     * @param parent Parent.
     * @param tags Person tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create a person.
     */
    @Builder(setterPrefix = "with")
    @SuppressWarnings("java:S107")
    public Person(final String lastName, final String firstName, final Date birthDate, final String name, final String description, final String reference, final PersonType personType, final GenderType genderType, final EntityStatusType statusType, final IDataModelEntity parent, final List<String> tags, final IDocument document) throws DataModelEntityException
    {
        super(EntityType.PERSON, name, description, reference, statusType, parent, document, tags);

        setLastName(lastName);
        setFirstName(firstName);
        setBirthDate(birthDate);

        this.genderType = genderType;

        super.validate(); // Validate the data

        if (personType == PersonType.PHYSICAL && genderType == null)
        {
            throw new ConstraintViolationException("genderType: value cannot be null when attribute: personType is set to: PHYSICAL", null);
        }
    }

    /**
     * Set the person last name.
     * @param lastName Last name.
     */
    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
        setName(this.lastName + ", " + this.firstName);
    }

    /**
     * Set the person first name.
     * @param firstName First name.
     */
    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
        setName(this.lastName + ", " + this.firstName);
    }

    /**
     * Return the default email address.
     * @return Optional default email address.
     */
    public final IEmailAddress getDefaultEmailAddress()
    {
        return emailAddresses.stream()
                .filter(IEmailAddress::getIsDefaultEmail).findFirst().orElse(null);
    }

    /**
     * Check if the person has a default email address?
     * @return {@code True} if the person has a default email address, {@code false} otherwise.
     */
    public final boolean hasDefaultEmailAddress()
    {
        return emailAddresses.stream().anyMatch(IEmailAddress::getIsDefaultEmail);
    }

    /**
     * Check if the given email already exist?
     * @param email Email to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existEmail(final @NonNull String email)
    {
        return emailAddresses.stream()
                .anyMatch(emailAddress -> emailAddress.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Check if the given email address already exist?
     * @param emailAddress Email address to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existEmailAddress(final @NonNull IEmailAddress emailAddress) // TODO Not sure it is necessary? Provide a test case.
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.equals(emailAddress));
    }

    /**
     * Return the email address matching the given identifier.
     * @param id Email address identifier.
     * @return Matching email address, <b>null</b> otherwise.
     */
    public final IEmailAddress getEmailById(final @NonNull UUID id)
    {
        return emailAddresses.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieve a list of email addresses matching the given address type.
     * @param type Address type.
     * @return List of matching email addresses.
     */
    @SuppressWarnings("java:S6204")
    public final List<IEmailAddress> findEmailAddressByType(final AddressType type)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getAddressType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of email addresses matching the given status type.
     * @param status Status type.
     * @return List of email addresses.
     */
    @SuppressWarnings("java:S6204")
    public final List<IEmailAddress> findEmailAddressByStatus(final EntityStatusType status)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getStatusType() == status)
                .collect(Collectors.toList());
    }

    @Override
    public final void addEmailAddress(final @NonNull IEmailAddress emailAddress) throws EmailAddressException
    {
        // An email address cannot be shared!
        if (emailAddress.getParent() != null)
        {
            throw new EmailAddressException(String.format(
                    "Cannot add email address: '%s' to entity type: '%s', with id: '%s' because it already belongs to entity type: '%s', with id: '%s'!",
                    emailAddress.getEmail(),
                    this.getEntityType(),
                    this.getId(),
                    emailAddress.getParent().getEntityType(),
                    emailAddress.getParent().getId()));
        }

        try
        {
            emailAddress.setParent(this);
        }
        catch (DataModelEntityException e)
        {
            throw new EmailAddressException(e);
        }

        emailAddresses.add((EmailAddress) emailAddress);
    }

    /**
     * Remove an email address.
     * @param email Email address to remove.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to remove an email address.
     */
    public final void removeEmailAddress(final @NonNull IEmailAddress email) throws DataModelEntityException
    {
        email.setParent(null);
        emailAddresses.remove(email);
    }

    /**
     * Return the default postal address.
     * @return Default postal address.
     */
    public final Optional<PostalAddress> getDefaultPostalAddress()
    {
        return postalAddresses.stream()
                .filter(e -> e.getIsDefault() == Boolean.TRUE).findFirst();
    }

    /**
     * Check if the person has a default postal address?
     * @return {@code True} if the person has a default postal address, {@code false} otherwise.
     */
    public final boolean hasDefaultPostalAddress()
    {
        return postalAddresses.stream().anyMatch(IPostalAddress::getIsDefault);
    }

    /**
     * Check if a postal address matches the given one exist?
     * @param address Postal address.
     * @return {@code True} if a postal address matches, {@code false} otherwise.
     */
    public final boolean existPostalAddress(final @NotNull IPostalAddress address)
    {
        return postalAddresses.stream().anyMatch(e -> e.equals(address));
    }

    /**
     * Retrieve postal addresses matching the given {@link AddressType}.
     * @param type Address type.
     * @return List of postal addresses.
     */
    @SuppressWarnings("java:S6204")
    public final List<IPostalAddress> findPostalAddressByType(final AddressType type)
    {
        return postalAddresses.stream()
                .filter(e -> e.getAddressType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve postal addresses matching the given {@link EntityStatusType}.
     * @param status Status type.
     * @return List of postal addresses.
     */
    @SuppressWarnings("java:S6204")
    public final List<IPostalAddress> findPostalAddressByStatus(final EntityStatusType status)
    {
        return postalAddresses.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toList());
    }

    /**
     * Add a postal address.
     * @param postalAddress Postal address.
     * @throws DataModelEntityException Thrown to indicate an error occurred while adding a postal address.
     */
    public final void addPostalAddress(final @NonNull IPostalAddress postalAddress) throws DataModelEntityException
    {
        postalAddress.setParent(this);
        postalAddresses.add((PostalAddress) postalAddress);
    }

    /**
     * Return the default phone number.
     * @return Optional default phone number.
     */
    public final Optional<PhoneNumber> getDefaultPhoneNumber()
    {
        return phoneNumbers.stream()
                .filter(PhoneNumber::getIsDefault).findFirst();
    }

    /**
     * Check if the person has a default phone number?
     * @return {@code True} if the person has a default phone number, {@code false} otherwise.
     */
    public final boolean hasDefaultPhoneNumber()
    {
        return phoneNumbers.stream().anyMatch(IPhoneNumber::getIsDefault);
    }

    /**
     * Check if the given phone number (only the number) already exist?
     * @param phoneNumber Phone number to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull String phoneNumber)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.getNumber().equalsIgnoreCase(phoneNumber));
    }

    /**
     * Check if the given phone number already exist?
     * @param phoneNumber Phone number to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull IPhoneNumber phoneNumber) // TODO Not sure it is necessary? Provide a test case.
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.equals(phoneNumber));
    }

    /**
     * Retrieve phone numbers matching the given {@link PhoneNumberType}.
     * @param type Address type.
     * @return List of phone numbers.
     */
    @SuppressWarnings("java:S6204")
    public final List<IPhoneNumber> findPhoneNumberByType(final PhoneNumberType type)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getPhoneType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve phone numbers matching the given {@link EntityStatusType}.
     * @param status Status type.
     * @return List of phone numbers.
     */
    @SuppressWarnings("java:S6204")
    public final List<IPhoneNumber> findPhoneNumberByStatus(final EntityStatusType status)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toList());
    }

    /**
     * Add a phone number.
     * @param phoneNumber Phone number.
     * @throws DataModelEntityException Thrown to indicate an error occurred while adding a postal address.
     */
    public final void addPhoneNumber(final @NonNull IPhoneNumber phoneNumber) throws DataModelEntityException
    {
        phoneNumber.setParent(this);
        phoneNumbers.add((PhoneNumber) phoneNumber);
    }
}
