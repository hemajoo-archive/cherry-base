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

import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;

/**
 * Provide services to manipulate the data composing a <b>phone number</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IPhoneNumber extends IDataModelEntity
{
    /**
     * Phone number attribute: <b>is default</b>.
     */
    public static final String PHONE_NUMBER_IS_DEFAULT = "isDefault";

    /**
     * Phone number attribute: <b>phone number</b>.
     */
    public static final String PHONE_NUMBER_NUMBER = "number";

    /**
     * Phone number attribute: <b>country code</b>.
     */
    public static final String PHONE_NUMBER_COUNTRY_CODE = "countryCode";

    /**
     * Phone number attribute: <b>phone type</b>.
     */
    public static final String PHONE_NUMBER_PHONE_TYPE = "phoneType";

    /**
     * Phone number attribute: <b>category type</b>.
     */
    public static final String PHONE_NUMBER_CATEGORY_TYPE = "categoryType";

    /**
     * Returns the phone number.
     * @return Phone number.
     */
    String getNumber();

    /**
     * Sets the phone number.
     * @param number Phone number.
     */
    void setNumber(final String number);

    /**
     * Returns the phone number country code.
     * @return Country code.
     */
    String getCountryCode();

    /**
     * Sets the phone number country code.
     * @param countryCode Country code.
     */
    void setCountryCode(final String countryCode);

    /**
     * Returns the phone number type.
     * @return Phone type.
     */
    PhoneNumberType getPhoneType();

    /**
     * Sets the phone number type.
     * @param type Phone number type.
     */
    void setPhoneType(final PhoneNumberType type);

    /**
     * Returns the phone number category type.
     * @return Category type.
     */
    PhoneNumberCategoryType getCategoryType();

    /**
     * Sets the phone number category type.
     * @param type Category type.
     */
    void setCategoryType(final PhoneNumberCategoryType type);

    /**
     * Returns if the phone number is the default one.
     * @return <b>True</b> if is the default phone number, <b>false</b> otherwise.
     */
    Boolean getIsDefault();

    /**
     * Sets if the phone number is the default one.
     * @param isDefault <b>True</b> to set the phone number as the default one, <b>false</b> otherwise.
     */
    void setIsDefault(final Boolean isDefault);
}
