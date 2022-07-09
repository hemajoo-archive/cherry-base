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
package com.hemajoo.commerce.cherry.base.data.model.base.type;

/**
 * Enumeration representing the several possible entity types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum EntityType
{
    /**
     * <b>Unknown</b> entity type.
     */
    UNKNOWN,

    /**
     * <b>Media</b> entity type.
     */
    MEDIA,

    /**
     * <b>Document</b> entity type.
     */
    DOCUMENT,

    /**
     * <b>Document Content</b> entity type.
     */
    DOCUMENT_CONTENT,

    /**
     * <b>Person</b> entity type.
     */
    PERSON,

    /**
     * <b>Email Address</b> entity type.
     */
    EMAIL_ADDRESS,

    /**
     * <b>Postal Address</b> entity type.
     */
    POSTAL_ADDRESS,

    /**
     * <b>Phone Number</b> entity type.
     */
    PHONE_NUMBER,

    /**
     * <b>Account</b> entity type.
     */
    ACCOUNT,

    /**
     * <b>Customer</b> entity type.
     */
    CUSTOMER,

    /**
     * <b>Organization</b> entity type.
     */
    ORGANIZATION,

    /**
     * <b>Company</b> entity type.
     */
    COMPANY,

    /**
     * <b>Department</b> entity type.
     */
    DEPARTMENT,

    /**
     * <b>Shop</b> entity type.
     */
    SHOP,

    /**
     * <b>Employee</b> entity type.
     */
    EMPLOYEE,
}
