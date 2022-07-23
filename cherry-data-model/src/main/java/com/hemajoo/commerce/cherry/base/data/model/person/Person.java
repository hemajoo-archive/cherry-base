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

    @Override
    public final Set<IEmailAddress> getEmailAddresses()
    {
        return Collections.unmodifiableSet(emailAddresses);
    }

    @Override
    public final IEmailAddress getDefaultEmailAddress()
    {
        return emailAddresses.stream()
                .filter(IEmailAddress::getIsDefaultEmail).findFirst().orElse(null);
    }

    @Override
    public final boolean hasDefaultEmailAddress()
    {
        return emailAddresses.stream().anyMatch(IEmailAddress::getIsDefaultEmail);
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
    public final boolean existEmailAddressByValue(final @NonNull String email)
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.getEmail().equals(email));
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
    public final IEmailAddress getEmailAddressByValue(@NonNull String value)
    {
        return emailAddresses.stream()
                .filter(e -> e.getEmail().equals(value)).findFirst().orElse(null);
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
    public final void deleteEmailAddressByValue(@NonNull String value) throws DataModelEntityException
    {
        IEmailAddress email = getEmailAddressByValue(value);

        if (email != null)
        {
            email.setParent(null);
            emailAddresses.removeIf(e -> e.getEmail().equals(value));
        }
    }

    @Override
    public final void deleteAllEmailAddress()
    {
        emailAddresses.clear();
    }

    @Override
    public final int getEmailAddressCount()
    {
        return emailAddresses.size();
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
    public final Set<IPostalAddress> getPostalAddresses()
    {
        return Collections.unmodifiableSet(postalAddresses);
    }

    @Override
    public final IPostalAddress getDefaultPostalAddress()
    {
        return postalAddresses.stream()
                .filter(IPostalAddress::getIsDefault).findFirst().orElse(null);
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
    public final boolean existPhoneNumber(final @NonNull IPhoneNumber phoneNumber)
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
