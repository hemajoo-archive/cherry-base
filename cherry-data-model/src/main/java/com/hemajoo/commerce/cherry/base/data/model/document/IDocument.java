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
 * Entities implementing this interface are tagged as a <b>document</b> data model entity.
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
     * Return the document content.
     * @return Document content.
     */
    InputStream getContent();

    /**
     * Set the document content.
     * @param file File.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull File file) throws DocumentException;

    /**
     * Set the document content.
     * @param filename File name.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull String filename) throws DocumentException;

    /**
     * Set the document content (based on the information provided at document creation time).
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent() throws DocumentException;

    /**
     * Returns the document extension.
     * @return Document extension.
     */
    String getExtension();

    /**
     * Sets the document extension.
     * @param extension Document extension.
     */
    void setExtension(final String extension);

    /**
     * Returns the document filename.
     * @return Document filename.
     */
    String getFilename();

    /**
     * Sets the document filename.
     * @param filename Document filename.
     */
    void setFilename(final String filename);

    /**
     * Returns the document content identifier.
     * @return Document content identifier.
     */
    String getContentId();

    /**
     * Sets the document content identifier.
     * @param contentId Document content identifier.
     */
    void setContentId(final String contentId);

    /**
     * Returns the document content length.
     * @return Document content length.
     */
    long getContentLength();

    /**
     * Sets the document content length.
     * @param contentLength Document content length.
     */
    void setContentLength(final long contentLength);

    /**
     * Returns the document mime type.
     * @return Document mime type.
     */
    String getMimeType();

    /**
     * Sets the document mime type.
     * @param mimeType Document mime type.
     */
    void setMimeType(final String mimeType);

    /**
     * Returns the document content path.
     * @return Document content path.
     */
    String getContentPath();

    /**
     * Sets the document content path.
     * @param contentPath Document content path.
     */
    void setContentPath(final String contentPath);
}
