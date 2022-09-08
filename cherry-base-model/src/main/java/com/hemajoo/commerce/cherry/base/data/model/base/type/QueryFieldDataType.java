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
 * Enumeration containing a definition for the several possible <b>query field data types</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum QueryFieldDataType
{
    /**
     * Data type is a <b>string</b>.
     */
    STRING,

    /**
     * Data type is an <b>integer</b>.
     */
    INTEGER,

    /**
     * Data type is a <b>long</b>.
     */
    LONG,

    /**
     * Data type is a <b>double</b>.
     */
    DOUBLE,

    /**
     * Data type is a <b>float</b>.
     */
    FLOAT,

    /**
     * Data type is a <b>date</b>.
     */
    DATE,

    /**
     * Data type is an <b>enumeration</b>.
     */
    ENUM,

    /**
     * Data type is a <b>UUID</b>.
     */
    UUID,

    /**
     * Data type is a <b>boolean</b>.
     */
    BOOLEAN,
}
