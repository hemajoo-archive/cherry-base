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
package com.hemajoo.commerce.cherry.base.data.model.document;

import lombok.NonNull;

import java.util.Arrays;

/**
 * Enumeration representing the several possible data model document types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DocumentType
{
    /**
     * <b>Unknown</b> document type.
     */
    UNKNOWN,

    /**
     * <b>Generic</b> document type.
     */
    DOCUMENT_GENERIC,

    /**
     * Document is a <b>photo</b>.
     */
    MEDIA_PHOTO,

    /**
     * Document is a <b>video</b>.
     */
    MEDIA_VIDEO,

    /**
     * Document is an <b>audio</b>.
     */
    MEDIA_AUDIO,

    /**
     * <b>Invoice</b> document type.
     */
    DOCUMENT_INVOICE,

    /**
     * <b>Icon</b> document type.
     */
    DOCUMENT_ICON;

    /**
     * Creates a new document type given its type as a string representation.
     * @param value Document type as a string.
     * @return Document type.
     * @throws IllegalArgumentException Thrown in case an error occurred while trying to create a document type enumerated value.
     */
    public static DocumentType from(final @NonNull String value)
    {
        for (DocumentType element : DocumentType.values())
        {
            if (element.name().equalsIgnoreCase(value))
            {
                return element;
            }
        }

        throw new IllegalArgumentException(String.format(
                "Cannot created a document type with value: '%s'. Possible values are: '%s'",
                value,
                Arrays.toString(DocumentType.values())));
    }
}
