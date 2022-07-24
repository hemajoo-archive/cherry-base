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
package com.hemajoo.commerce.cherry.base.data.model.person.phone;

import com.hemajoo.commerce.cherry.base.data.model.base.DataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityValidationException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.Person;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Represents a server data model <b>phone number</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Table(name = "PHONE_NUMBER")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PhoneNumber extends DataModelEntity implements IPhoneNumber
{
    /**
     * Phone number.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "PHONE_NUMBER", length = 30)
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    /**
     * Phone number type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PHONE_TYPE")
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;

    /**
     * The person identifier this phone number belongs to.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter
    @Setter
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private Person person;

    /**
     * Creates a new phone number.
     */
    public PhoneNumber()
    {
        super(EntityType.PHONE_NUMBER);
    }

    /**
     * Create a new phone number.
     * @param number Phone number.
     * @param countryCode Country code.
     * @param phoneType Phone type.
     * @param categoryType Phone category type.
     * @param isDefault Is default phone number?
     * @param name Name.
     * @param description Description.
     * @param reference Reference.
     * @param statusType Status type.
     * @param parent Parent.
     * @param document Associated document.
     * @param tags Tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create a phone number.
     */
    @SuppressWarnings("java:S107")
    @Builder(setterPrefix = "with")
    public PhoneNumber(final String number, final String countryCode, final PhoneNumberType phoneType, final PhoneNumberCategoryType categoryType, final boolean isDefault, final String name, final String description, final String reference, final EntityStatusType statusType, final IDataModelEntity parent, final IDocument document, final Set<String> tags) throws DataModelEntityException
    {
        super(EntityType.PHONE_NUMBER, name, description, reference, statusType, parent, document, tags);

        this.number = number;
        this.countryCode = countryCode;
        this.isDefault = isDefault;
        setPhoneType(phoneType == null ? PhoneNumberType.OTHER : phoneType);
        setCategoryType(categoryType == null ? PhoneNumberCategoryType.OTHER : categoryType);

        if (name == null)
        {
            setName("+" + countryCode + number);
        }

        try
        {
            super.validate(); // Validate the entity data
        }
        catch (Exception e)
        {
            throw new PhoneNumberException(e.getMessage());
        }
    }

    @Override
    protected final void postValidate() throws DataModelEntityValidationException
    {
        super.postValidate();
    }
}
