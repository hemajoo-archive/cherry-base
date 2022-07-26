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

import com.hemajoo.commerce.cherry.base.data.model.base.DataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityValidationException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.IPerson;
import com.hemajoo.commerce.cherry.base.data.model.person.Person;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commons.annotation.EnumNotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Represents an <b>email address</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "EMAIL_ADDRESS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmailAddress extends DataModelEntity implements IEmailAddress
{
    /**
     * Email address.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Email
    @Column(name = "EMAIL")
    private String email;

    /**
     * Is it the default email address?
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDefault;

    /**
     * Email type.
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @EnumNotNull(enumClass = AddressType.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    /**
     * The person identifier this email address belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private IPerson person;

    /**
     * Creates a new persistent email address.
     */
    public EmailAddress()
    {
        super(EntityType.EMAIL_ADDRESS);

        this.addressType = AddressType.UNKNOWN;
        this.isDefault = false;
    }

    /**
     * Create a new email address.
     * @param email Email.
     * @param addressType Address type.
     * @param isDefault Is default email address?
     * @param name Name.
     * @param description Description.
     * @param reference Reference.
     * @param statusType Status type.
     * @param parent Parent.
     * @param document Associated document.
     * @param tags Tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create an email address.
     */
    @SuppressWarnings("java:S107")
    @Builder(setterPrefix = "with")
    public EmailAddress(final String email, final AddressType addressType, final boolean isDefault, final String name, final String description, final String reference, final EntityStatusType statusType, final IDataModelEntity parent, final IDocument document, final Set<String> tags) throws DataModelEntityException
    {
        super(EntityType.EMAIL_ADDRESS, name, description, reference, statusType, parent, document, tags);

        this.email = email;
        this.isDefault = isDefault;
        setAddressType(addressType == null ? AddressType.UNKNOWN : addressType);

        if (getName() == null)
        {
            setName(email);
        }

        try
        {
            super.validate(); // Validate the entity data
        }
        catch (Exception e)
        {
            throw new EmailAddressException(e.getMessage());
        }
    }

    @Override
    protected final void postValidate() throws DataModelEntityValidationException
    {
        super.postValidate();
    }
}
