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
package com.hemajoo.commerce.cherry.base.data.model.person.address.postal;

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
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Represents a <b>postal address</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Table(name = "POSTAL_ADDRESS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PostalAddress extends DataModelEntity implements IPostalAddress
{
    /**
     * Postal address street name.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "STREET_NAME")
    private String streetName;

    /**
     * Postal address street number.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "STREET_NUMBER")
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "LOCALITY")
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Column(name = "ZIP_CODE", length = 10)
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @Getter
    @Setter
    @Column(name = "AREA")
    private String area;

    /**
     * Is it a default postal address?
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    /**
     * Postal address category type.
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private PostalAddressType postalAddressType;

    /**
     * The person identifier this postal address belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private IPerson person;

    /**
     * Creates a new postal address.
     */
    public PostalAddress()
    {
        super(EntityType.POSTAL_ADDRESS);
    }

    /**
     * Create a new postal address.
     * @param streetName Street name.
     * @param streetNumber Street number.
     * @param locality Locality.
     * @param countryCode Country code.
     * @param zipCode Zip code.
     * @param area Area.
     * @param isDefault Is default postal address?
     * @param addressType Address type.
     * @param postalAddressType Postal address type.
     * @param name Name.
     * @param description Description.
     * @param reference Reference.
     * @param statusType Status type.
     * @param parent Parent.
     * @param document Associated document.
     * @param tags Tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create a postal address.
     */
    @SuppressWarnings("java:S107")
    @Builder(setterPrefix = "with")
    public PostalAddress(final String streetName, final String streetNumber, final String locality, final String countryCode, final String zipCode, final String area, final AddressType addressType, final PostalAddressType postalAddressType, final boolean isDefault, final String name, final String description, final String reference, final EntityStatusType statusType, final IDataModelEntity parent, final IDocument document, final Set<String> tags) throws DataModelEntityException
    {
        super(EntityType.POSTAL_ADDRESS, name, description, reference, statusType, parent, document, tags);

        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.locality = locality;
        this.countryCode = countryCode;
        this.zipCode = zipCode;
        this.area = area;
        this.isDefault = isDefault;
        setAddressType(addressType == null ? AddressType.UNKNOWN : addressType);
        setPostalAddressType(postalAddressType == null ? PostalAddressType.OTHER : postalAddressType);

        if (name == null)
        {
            setName(streetNumber + ", " + streetName + " " + zipCode + " " + locality + " - " + countryCode);
        }

        try
        {
            super.validate(); // Validate the entity data
        }
        catch (Exception e)
        {
            throw new PostalAddressException(e.getMessage());
        }
    }

    @Override
    protected final void postValidate() throws DataModelEntityValidationException
    {
        super.postValidate();
    }
}
