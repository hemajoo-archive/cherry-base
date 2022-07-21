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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import lombok.NonNull;

import java.io.File;
import java.io.InputStream;

/**
 * Provide services to manipulate the data composing a <b>document</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IDocument extends IDataModelEntity
{
    /**
     * Document attribute: <b>extension</b>.
     */
    @JsonIgnore
    String DOCUMENT_EXTENSION = "extension";

    /**
     * Document attribute: <b>filename</b>.
     */
    @JsonIgnore
    String DOCUMENT_FILENAME = "filename";

    /**
     * Document attribute: <b>content path</b>.
     */
    @JsonIgnore
    String DOCUMENT_CONTENT_PATH = "contentPath";

    /**
     * Document attribute: <b>content length</b>.
     */
    @JsonIgnore
    String DOCUMENT_CONTENT_LENGTH = "contentLength";

    /**
     * Document attribute: <b>document type</b>.
     */
    @JsonIgnore
    String DOCUMENT_TYPE = "documentType";

    /**
     * Document attribute: <b>document mime type</b>.
     */
    @JsonIgnore
    String DOCUMENT_MIMETYPE = "mimeType";

    /**
     * Document attribute: <b>document tags</b>.
     */
    @JsonIgnore
    String DOCUMENT_TAGS = "tags";

    /**
     * Return the document type.
     * @return Document type.
     */
    DocumentType getDocumentType();

    /**
     * Set the document type.
     * @param type Document type.
     */
    void setDocumentType(final DocumentType type);

    /**
     * Return the content (file) of this document.
     * @return Document content as an input stream.
     */
    InputStream getContent();

    /**
     * Set the content of the document.
     * @param file File representing the content (file) to attach to the document.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull File file) throws DocumentException;

    /**
     * Set the content of the document.
     * @param filename File name representing the content (file) to attach to the document.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull String filename) throws DocumentException;

    /**
     * Return the document extension.
     * @return Document extension.
     */
    String getExtension();

    /**
     * Return the document filename.
     * @return Document filename.
     */
    String getFilename();

    /**
     * Return the document content identifier.
     * @return Document content identifier.
     */
    String getContentId();

    /**
     * Return the document content length.
     * @return Document content length.
     */
    long getContentLength();

    /**
     * Return the document mime type.
     * @return Document mime type.
     */
    String getMimeType();

    /**
     * Return the document content path.
     * @return Document content path.
     */
    String getContentPath();
}
