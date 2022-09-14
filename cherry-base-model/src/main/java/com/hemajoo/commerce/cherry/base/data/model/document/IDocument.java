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
import org.springframework.web.multipart.MultipartFile;

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
     * Return the content of the document.
     * @return Document content as an input stream.
     */
    InputStream getContent();

    /**
     * Set the content of a document given a file.
     * @param file File representing the content (file) to attach to the document.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull File file) throws DocumentException;

    /**
     * Set the content of a document given a filename.
     * @param filename File name representing the content (file) to attach to the document.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull String filename) throws DocumentException;

    /**
     * Set the content of a document given a multipart file.
     * @param multipartFile Multipart file.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull MultipartFile multipartFile) throws DocumentException;

    /**
     * Set the content of a document given an input stream.
     * @param stream Input stream representing the content of the document.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    void setContent(final @NonNull InputStream stream) throws DocumentException;

    /**
     * Return the document extension.
     * @return Document extension.
     */
    String getExtension();

    /**
     * Set the extension.
     * @param extension Extension.
     */
    void setExtension(final @NonNull String extension);

    /**
     * Return the document filename.
     * @return Document filename.
     */
    String getFilename();

    /**
     * Set the filename.
     * @param filename Filename.
     */
    void setFilename(final @NonNull String filename);

    /**
     * Return the document original filename.
     * @return Document original filename.
     */
    String getOriginalFilename();

    /**
     * Set the original filename.
     * @param filename Original filename.
     */
    void setOriginalFilename(final @NonNull String filename);

    /**
     * Return the document content identifier.
     * @return Document content identifier.
     */
    String getContentId();

    /**
     * Set the content identifier.
     * @param contentId Content identifier.
     */
    void setContentId(final @NonNull String contentId);

    /**
     * Return the document content length.
     * @return Document content length.
     */
    long getContentLength();

    /**
     * Set the content length (in bytes).
     * @param length Content length.
     */
    void setContentLength(final long length);

    /**
     * Return the document mime type.
     * @return Document mime type.
     */
    String getMimeType();

    /**
     * Set the mime type.
     * @param mimeType Mime type.
     */
    void setMimeType(final @NonNull String mimeType);

    /**
     * Return the document content path.
     * @return Document content path.
     */
    String getContentPath();

    /**
     * Set the content path.
     * @param contentPath Content path.
     */
    void setContentPath(final @NonNull String contentPath);
}
