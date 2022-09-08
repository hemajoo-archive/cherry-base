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
 * Enumeration containing a definition for the several possible <b>entity types</b>.
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
     * Entity is a <b>media</b> entity.
     */
    MEDIA,

    /**
     * Entity is a <b>document</b> entity.
     */
    DOCUMENT,

    /**
     * Entity is a <b>document content</b> entity.
     */
    DOCUMENT_CONTENT,

    /**
     * Entity is a <b>person</b> entity.
     */
    PERSON,

    /**
     * Entity is an <b>email address</b> entity.
     */
    EMAIL_ADDRESS,

    /**
     * Entity is a <b>postal address</b> entity.
     */
    POSTAL_ADDRESS,

    /**
     * Entity is a <b>phone number</b> entity.
     */
    PHONE_NUMBER,

    /**
     * Entity is an <b>account</b> entity.
     */
    ACCOUNT,

    /**
     * Entity is a <b>customer</b> entity.
     */
    CUSTOMER,

    /**
     * Entity is an <b>organization</b> entity.
     */
    ORGANIZATION,

    /**
     * Entity is a <b>company</b> entity.
     */
    COMPANY,

    /**
     * Entity is a <b>department</b> entity.
     */
    DEPARTMENT,

    /**
     * Entity is a <b>shop</b> entity.
     */
    SHOP,

    /**
     * Entity is an <b>employee</b> entity.
     */
    EMPLOYEE,
}
