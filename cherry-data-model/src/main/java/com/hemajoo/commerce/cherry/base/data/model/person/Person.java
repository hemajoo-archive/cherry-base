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
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityValidationException;
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
import com.hemajoo.commons.annotation.EnumNotNull;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a <b>person</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
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
    @Enumerated(EnumType.STRING)
    @EnumNotNull(enumClass = PersonType.class)
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
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = PostalAddress.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostalAddress> postalAddresses = new HashSet<>();

    /**
     * Phone numbers associated to the person.
     */
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = PhoneNumber.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    /**
     * Email addresses associated to the person.
     */
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("owner")
//    @OneToMany(targetEntity = ServerEmailAddressEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(targetEntity = EmailAddress.class, mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IEmailAddress> emailAddresses = new HashSet<>();

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
     * @param document Document.
     * @param emailAddress Email address.
     * @param postalAddress Postal address.
     * @param phoneNumber Phone number.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create a person.
     */
    @Builder(setterPrefix = "with")
    @SuppressWarnings("java:S107")
    public Person(final String lastName, final String firstName, final Date birthDate, final String name, final String description, final String reference, final PersonType personType,
                  final GenderType genderType, final EntityStatusType statusType, final IDataModelEntity parent, final Set<String> tags, final IDocument document,
                  final IEmailAddress emailAddress, final IPostalAddress postalAddress, final IPhoneNumber phoneNumber) throws DataModelEntityException
    {
        super(EntityType.PERSON, name, description, reference, statusType, parent, document, tags);

        setLastName(lastName);
        setFirstName(firstName);
        setBirthDate(birthDate);
        setGenderType(genderType);
        setPersonType(personType);

        if (emailAddress != null)
        {
            addEmailAddress(emailAddress);
        }
        if (postalAddress != null)
        {
            addPostalAddress(postalAddress);
        }
        if (phoneNumber != null)
        {
            addPhoneNumber(phoneNumber);
        }

        try
        {
            super.validate(); // Validate the entity data using annotations
        }
        catch (Exception e)
        {
            throw new PersonException(e.getMessage());
        }
    }

    @Override
    protected final void postValidate() throws DataModelEntityValidationException
    {
        super.postValidate();

        if (personType == PersonType.PHYSICAL && genderType == null)
        {
            throw new DataModelEntityValidationException("Attribute: 'genderType' cannot be null if attribute: 'personType' is set to: 'PHYSICAL'");
        }
    }

    @Override
    public final void setLastName(final String lastName)
    {
        this.lastName = lastName;
        setName(this.lastName + ", " + this.firstName);
    }

    @Override
    public final void setFirstName(final String firstName)
    {
        this.firstName = firstName;
        setName(this.lastName + ", " + this.firstName);
    }

    @Override
    public final Set<IEmailAddress> getEmailAddresses()
    {
        return Collections.unmodifiableSet(emailAddresses);
    }

    @Override
    public final IEmailAddress getDefaultEmailAddress()
    {
        return emailAddresses.stream()
                .filter(IEmailAddress::getIsDefault).findFirst().orElse(null);
    }

    @Override
    public final boolean hasDefaultEmailAddress()
    {
        return emailAddresses.stream().anyMatch(IEmailAddress::getIsDefault);
    }

    @Override
    public final boolean existEmailAddress(final @NonNull IEmailAddress email)
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.equals(email));
    }

    @Override
    public final boolean existEmailAddressById(final @NonNull UUID id)
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.getId().equals(id));
    }

    @Override
    public final boolean existEmailAddressById(final @NonNull String id)
    {
        return existEmailAddressById(UUID.fromString(id));
    }

    @Override
    public final boolean existEmailAddressByName(final @NonNull String name)
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.getName().equals(name));
    }

    @Override
    public final IEmailAddress getEmailAddress(@NonNull IEmailAddress email)
    {
        return emailAddresses.stream()
                .filter(e -> e.equals(email)).findFirst().orElse(null);
    }

    @Override
    public final IEmailAddress getEmailAddressById(@NonNull UUID id)
    {
        return emailAddresses.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public final IEmailAddress getEmailAddressById(@NonNull String id)
    {
        return getEmailAddressById(UUID.fromString(id));
    }

    @Override
    public final IEmailAddress getEmailAddressByName(@NonNull String name)
    {
        return emailAddresses.stream()
                .filter(e -> e.getEmail().equals(name)).findFirst().orElse(null);
    }

    @Override
    public final boolean addEmailAddress(final @NonNull IEmailAddress emailAddress) throws EmailAddressException
    {
        if (existEmailAddress(emailAddress))
        {
            LOGGER.warn(String.format(
                    "Cannot add email address with value: '%s' to person with id: '%s', name: '%s' because it already exist!",
                    emailAddress.toString(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

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

        // Does the email address UUID already exist?
        if (emailAddress.getId() != null && existEmailAddressById(emailAddress.getId()))
        {
            LOGGER.warn(String.format(
                    "Cannot add email address with id: '%s', with email: '%s' to person with id: '%s', with name: '%s' because it already exist!",
                    emailAddress.getId().toString(),
                    emailAddress.getEmail(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        // Does the person already has a default email address?
        if (emailAddress.getIsDefault() && hasDefaultEmailAddress())
        {
            LOGGER.warn(String.format(
                    "Cannot add email address with id: '%s', with email: '%s' to person with id: '%s', with name: '%s' because only one default email address per person is allowed!",
                    emailAddress.getId() != null ? emailAddress.getId().toString() : "<null>",
                    emailAddress.getEmail(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        try
        {
            emailAddress.setParent(this);
        }
        catch (DataModelEntityException e)
        {
            throw new EmailAddressException(e);
        }

        emailAddresses.add(emailAddress);

        return true;
    }

    @Override
    public final void deleteEmailAddress(final @NonNull IEmailAddress email) throws DataModelEntityException
    {
        email.setParent(null);
        emailAddresses.removeIf(e -> e.equals(email));
    }

    @Override
    public final void deleteEmailAddressById(final @NonNull UUID id) throws DataModelEntityException
    {
        IEmailAddress email = getEmailAddressById(id);

        if (email != null)
        {
            email.setParent(null);
            emailAddresses.removeIf(e -> e.getId().equals(id));
        }
    }

    @Override
    public final void deleteEmailAddressById(@NonNull String id) throws DataModelEntityException
    {
        deleteEmailAddressById(UUID.fromString(id));
    }

    @Override
    public final void deleteEmailAddressByName(@NonNull String name) throws DataModelEntityException
    {
        IEmailAddress email = getEmailAddressByName(name);

        if (email != null)
        {
            email.setParent(null);
            emailAddresses.removeIf(e -> e.getName().equals(name));
        }
    }

    @Override
    public final void deleteAllEmailAddress() throws DataModelEntityException
    {
        for (IEmailAddress emailAddress : emailAddresses)
        {
            emailAddress.setParent(null);
        }

        emailAddresses.clear();
    }

    @Override
    public final int getEmailAddressCount()
    {
        return emailAddresses.size();
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IEmailAddress> findEmailAddressByType(final AddressType type)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getAddressType() == type)
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IEmailAddress> findEmailAddressByStatus(final EntityStatusType status)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getStatusType() == status)
                .collect(Collectors.toSet());
    }

    @Override
    public final Set<IPostalAddress> getPostalAddresses()
    {
        return Collections.unmodifiableSet(postalAddresses);
    }

    @Override
    public IPostalAddress getPostalAddress(final @NonNull IPostalAddress address)
    {
        return postalAddresses.stream()
                .filter(postalAddress -> postalAddress.equals(address))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IPostalAddress getPostalAddressById(final @NonNull UUID id)
    {
        return postalAddresses.stream()
                .filter(postalAddress -> postalAddress.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IPostalAddress getPostalAddressById(final @NonNull String id)
    {
        return getPostalAddressById(UUID.fromString(id));
    }

    @Override
    public IPostalAddress getPostalAddressByName(final @NonNull String name)
    {
        return postalAddresses.stream()
                .filter(postalAddress -> postalAddress.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public final IPostalAddress getDefaultPostalAddress()
    {
        return postalAddresses.stream()
                .filter(IPostalAddress::getIsDefault).findFirst().orElse(null);
    }

    @Override
    public final boolean hasDefaultPostalAddress()
    {
        return postalAddresses.stream().anyMatch(IPostalAddress::getIsDefault);
    }

    @Override
    public final boolean existPostalAddress(final @NonNull IPostalAddress address)
    {
        return postalAddresses.stream().anyMatch(postalAddress -> postalAddress.equals(address));
    }

    @Override
    public boolean existPostalAddressById(final @NonNull UUID id)
    {
        return postalAddresses.stream().anyMatch(postalAddress -> postalAddress.getId().equals(id));
    }

    @Override
    public boolean existPostalAddressById(final @NonNull String id)
    {
        return existPostalAddressById(UUID.fromString(id));
    }

    @Override
    public final boolean existPostalAddressByName(final @NonNull String name)
    {
        return postalAddresses.stream()
                .anyMatch(e -> e.getName().equals(name));
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IPostalAddress> findPostalAddressByType(final AddressType type)
    {
        return postalAddresses.stream()
                .filter(e -> e.getAddressType() == type)
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IPostalAddress> findPostalAddressByStatus(final EntityStatusType status)
    {
        return postalAddresses.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toSet());
    }

    @Override
    public final boolean addPostalAddress(final @NonNull IPostalAddress postalAddress) throws DataModelEntityException
    {
        if (existPostalAddress(postalAddress))
        {
            LOGGER.warn(String.format(
                    "Cannot add postal address with value: '%s' to person with id: '%s', name: '%s' because it already exist!",
                    postalAddress.toString(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        // Does the postal address UUID already exist?
        if (postalAddress.getId() != null && existPostalAddressById(postalAddress.getId()))
        {
            LOGGER.warn(String.format(
                    "Cannot add postal address with id: '%s', with name: '%s' to person with id: '%s', with name: '%s' because it already exist!",
                    postalAddress.getId().toString(),
                    postalAddress.getName(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }
        // Does the postal address name already exist?
        if (existPostalAddressByName(postalAddress.getName()))
        {
            LOGGER.warn(String.format(
                    "Cannot add postal address with id: '%s', with name: '%s' to person with id: '%s', with name: '%s' because it already exist!",
                    postalAddress.getId() != null ? postalAddress.getId().toString() : "<null>",
                    postalAddress.getName(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }
        // Does the person already has a default postal address?
        if (postalAddress.getIsDefault() && hasDefaultPostalAddress())
        {
            LOGGER.warn(String.format(
                    "Cannot add postal address with id: '%s', with value: '%s' to person with id: '%s', with name: '%s' because only one default postal address per person is allowed!",
                    postalAddress.getId() != null ? postalAddress.getId().toString() : "<null>",
                    postalAddress.getName(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        postalAddress.setParent(this);
        postalAddresses.add((PostalAddress) postalAddress);

        return true;
    }

    @Override
    public final void deletePostalAddress(final @NonNull IPostalAddress address)
    {
        postalAddresses.removeIf(postalAddress -> postalAddress.equals(address));
    }

    @Override
    public final void deletePostalAddressById(final @NonNull UUID id)
    {
        postalAddresses.removeIf(postalAddress -> postalAddress.getId().equals(id));
    }

    @Override
    public final void deletePostalAddressById(final @NonNull String id)
    {
        deletePostalAddressById(UUID.fromString(id));
    }

    @Override
    public final void deleteAllPostalAddress() throws DataModelEntityException
    {
        for (IPostalAddress postalAddress : postalAddresses)
        {
            postalAddress.setParent(null);
        }

        postalAddresses.clear();
    }

    @Override
    public final int getPostalAddressCount()
    {
        return postalAddresses.size();
    }

    @Override
    public final Set<IPhoneNumber> getPhoneNumbers()
    {
        return Collections.unmodifiableSet(phoneNumbers);
    }

    @Override
    public final int getPhoneNumberCount()
    {
        return phoneNumbers.size();
    }

    @Override
    public final PhoneNumber getDefaultPhoneNumber()
    {
        return phoneNumbers.stream()
                .filter(PhoneNumber::getIsDefault).findFirst().orElse(null);
    }

    @Override
    public final boolean hasDefaultPhoneNumber()
    {
        return phoneNumbers.stream().anyMatch(IPhoneNumber::getIsDefault);
    }

    @Override
    public final boolean existPhoneNumber(final @NonNull IPhoneNumber phoneNumber)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.equals(phoneNumber));
    }

    @Override
    public final boolean existPhoneNumberById(@NonNull UUID id)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.getId().equals(id));
    }

    @Override
    public final boolean existPhoneNumberById(@NonNull String id)
    {
        return existPhoneNumberById(UUID.fromString(id));
    }


    @Override
    public final boolean existPhoneNumberByName(final @NonNull String name)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    @Override
    public final IPhoneNumber getPhoneNumber(@NonNull IPhoneNumber phone)
    {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.equals(phone))
                .findFirst()
                .orElse(null);
    }

    @Override
    public final IPhoneNumber getPhoneNumberById(@NonNull UUID id)
    {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public final IPhoneNumber getPhoneNumberById(@NonNull String id)
    {
        return getPhoneNumberById(UUID.fromString(id));
    }

    @Override
    public final IPhoneNumber getPhoneNumberByName(@NonNull String name)
    {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public final boolean addPhoneNumber(final @NonNull IPhoneNumber phoneNumber) throws DataModelEntityException
    {
        if (existPhoneNumber(phoneNumber))
        {
            LOGGER.warn(String.format(
                    "Cannot add phone number with value: '%s' to person with id: '%s', name: '%s' because it already exist!",
                    phoneNumber.toString(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        // UUID is unique
        if (phoneNumber.getId() != null && existPhoneNumberById(phoneNumber.getId()))
        {
            LOGGER.warn(String.format(
                    "Cannot add phone number with value: '%s' to person with id: '%s', with name: '%s' because it already exist!",
                    phoneNumber.toString(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        // Name is unique
        if (phoneNumber.getName() != null && existPhoneNumberByName(phoneNumber.getName()))
        {
            LOGGER.warn(String.format(
                    "Cannot add phone number with id: '%s', with number: '%s' to person with id: '%s', with name: '%s' because it already exist!",
                    phoneNumber.getId() != null ? phoneNumber.getId().toString() : "<null>",
                    phoneNumber.getNumber(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        // Only one default phone number per person?
        if (phoneNumber.getIsDefault() && hasDefaultPhoneNumber())
        {
            LOGGER.warn(String.format(
                    "Cannot add phone number with id: '%s', with number: '%s' to person with id: '%s', with name: '%s' because only one default phone number per person is allowed!",
                    phoneNumber.getId() != null ? phoneNumber.getId().toString() : "<null>",
                    phoneNumber.getNumber(),
                    this.getId() != null ? this.getId().toString() : "<null>",
                    this.getName()));

            return false;
        }

        phoneNumber.setParent(this);
        phoneNumbers.add((PhoneNumber) phoneNumber);

        return true;
    }

    @Override
    public final void deletePhoneNumber(@NonNull IPhoneNumber phone)
    {
        phoneNumbers.removeIf(phoneNumber -> phoneNumber.equals(phone));
    }

    @Override
    public final void deletePhoneNumberById(@NonNull UUID id)
    {
        phoneNumbers.removeIf(phoneNumber -> phoneNumber.getId().equals(id));
    }

    @Override
    public final void deletePhoneNumberById(@NonNull String id)
    {
        deletePostalAddressById(UUID.fromString(id));
    }

    @Override
    public final void deleteAllPhoneNumber() throws DataModelEntityException
    {
        for (IPhoneNumber phoneNumber : phoneNumbers)
        {
            phoneNumber.setParent(null);
        }

        phoneNumbers.clear();
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IPhoneNumber> findPhoneNumberByType(final PhoneNumberType type)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getPhoneType() == type)
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("java:S6204")
    public final Set<IPhoneNumber> findPhoneNumberByStatus(final EntityStatusType status)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toSet());
    }
}
