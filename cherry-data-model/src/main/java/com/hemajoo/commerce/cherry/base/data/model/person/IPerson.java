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
import me.xdrop.jrand.model.person.PersonType;

import java.util.Date;

/**
 * Entities implementing this interface are tagged as a server <b>person</b> data model entity.
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
}