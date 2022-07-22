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

import com.hemajoo.commerce.cherry.base.data.model.base.DataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.utilities.UuidGenerator;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.ressec.avocado.core.helper.FileHelper;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Represent a <b>document</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "DOCUMENT")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Document extends DataModelEntity implements IDocument
{
    /**
     * Document type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", length = 50)
    private DocumentType documentType = DocumentType.DOCUMENT_GENERIC;

    /**
     * Document file extension.
     */
    @Getter
    //@Setter
    private String extension;

    /**
     * Document file name.
     */
    @Getter
    //@Setter
    @Column(name = "FILENAME")
    private String filename;

    /**
     * Multipart file.
     */
    @DiffIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter
    @Transient
    private transient MultipartFile multiPartFile; // Only stored until the content of the file is loaded into the content store.

    /**
     * Base file name.
     */
    @DiffIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter
    @Transient
    private transient String baseFilename; // Only stored until the content of the file is loaded into the content store.

    /**
     * File content identifier.
     * <br>
     * UUID of the file in the content store.
     */
    @Getter
    @Setter
    @ContentId
    private String contentId;

    /**
     * File content length.
     */
    @Getter
    //@Setter
    @ContentLength
    private long contentLength;

    /**
     * File <b>MIME</b> type.
     */
    @Getter
    //@Setter
    @MimeType
    private String mimeType = "text/plain";

    /**
     * File path of the document in the <b>content store</b>.
     */
    @Getter
    //@Setter
    private String contentPath;

    /**
     * Document content.
     */
    @DiffIgnore
    @Getter
    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private transient InputStream content;

    /**
     * Create a new document.
     */
    public Document()
    {
        super(EntityType.DOCUMENT);

        setActive();
    }

    /**
     * Create a new document.
     * @param name Document name.
     * @param description Document description.
     * @param documentType Document type.
     * @param statusType Status type.
     * @param parent Parent.
     * @param reference Document reference.
     * @param filename Document file name representing the document content. If <b>withFilename()</b> is used invoking the builder, the <b>withFile()</b> is not supposed to be invoked!
     * @param file Document file representing the document content. If <b>withFile()</b> is used invoking the builder, the <b>withFilename()</b> is not supposed to be invoked!
     * @param tags List of tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to create a document.
     */
    @SuppressWarnings("java:S107")
    @Builder(setterPrefix = "with")
    public Document(final String name, final String description, final DocumentType documentType, final EntityStatusType statusType, final IDataModelEntity parent, final String reference, final File file, final String filename, final List<String> tags) throws DataModelEntityException
    {
        super(EntityType.DOCUMENT, name, description, reference, statusType, parent, null /* a document cannot contain other documents */, tags);

        setId(UuidGenerator.getUuid());

        if (documentType != null)
        {
            this.documentType = documentType;
        }

        try
        {
            setParent(parent);
            if (parent != null)
            {
                parent.addDocument(this);
            }
        }
        catch (DataModelEntityException e)
        {
            throw new DocumentException(e);
        }

        if (file != null)
        {
            setContent(file);
        }
        else
        {
            if (filename != null)
            {
                setContent(filename);
            }
        }

        super.validate(); // Validate the document data
    }

//    /**
//     * Create a new document.
//     * @param documentType Document type.
//     * @param owner Document owner.
//     * @throws DocumentException Thrown to indicate an error occurred when trying to create a document.
//     */
//    protected Document(final DocumentType documentType, final IDataModelEntity owner) throws DocumentException
//    {
//        this();
//
//        setId(UuidGenerator.getUuid());
//
//        if (documentType != null)
//        {
//            this.documentType = documentType;
//        }
//
//        try
//        {
//            setParent(owner);
//            if (owner != null)
//            {
//                owner.addDocument(this);
//            }
//        }
//        catch (DataModelEntityException e)
//        {
//            throw new DocumentException(e);
//        }
//    }

    @SuppressWarnings("java:S1163")
    @Override
    public final void setContent(final @NonNull String filename) throws DocumentException
    {
        try
        {
            File file = FileHelper.getFile(filename);
            if (file == null)
            {
                throw new DocumentException(String.format("Cannot find file: '%s'", filename));
            }

            try (FileInputStream stream = new FileInputStream(file))
            {
                setContent(stream, file.getAbsolutePath());
            }
        }
        catch (Exception e)
        {
            throw new DocumentException(e);
        }
    }

    @Override
    public final void setContent(final @NonNull File file) throws DocumentException
    {
        setContent(file.getAbsolutePath());
    }

    /**
     * Set the document content.
     * @param inputStream Input stream.
     * @param absolutePath Absolute path of the file.
     * @throws DocumentException Thrown in case an error occurred while setting the document content.
     */
    private void setContent(final @NonNull InputStream inputStream, final @NonNull String absolutePath) throws DocumentException
    {
        try
        {
            detectMimeType(absolutePath);
            this.baseFilename = absolutePath;
            this.filename = FilenameUtils.getName(baseFilename);
            this.extension = FilenameUtils.getExtension(filename);
            this.content = inputStream;
            this.contentLength = inputStream.available();
        }
        catch (IOException e)
        {
            throw new DocumentException(e);
        }
    }

    /**
     * Detect the <b>mime</b> type of the given file name.
     * @param filename File name.
     * @throws DocumentContentException Thrown in case an error occurred while processing the file.
     */
    private void detectMimeType(final @NonNull String filename) throws DocumentContentException
    {
        try
        {
            detectMimeType(FileHelper.getFile(filename));
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detect the <b>mime</b> type of the given file.
     * @param file File.
     * @throws DocumentContentException Thrown in case an error occurred while processing the file.
     */
    private void detectMimeType(final @NonNull File file) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(file);
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detect the <b>mime</b> type of the file contained in the given input stream.
     * @param inputStream Input stream.
     * @throws DocumentContentException Thrown in case an error occurred while processing the input stream.
     */
    private void detectMimeType(final @NonNull InputStream inputStream) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(inputStream);
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detect the <b>mime</b> type of the media file.
     * @param multiPartFile Multipart file.
     * @throws DocumentContentException Thrown in case an error occurred while processing the multipart file.
     */
    private void detectMimeType(final @NonNull MultipartFile multiPartFile) throws DocumentContentException
    {
        try
        {
            detectMimeType(multiPartFile.getInputStream());
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Return the document output full path name (path + name).
     * @param outputPath Output path and file name.
     * @return Document full path and file name.
     */
    @DiffIgnore
    public final String getOutputFilename(final @NonNull String outputPath)
    {
        String end = getName() + "." + extension;

        return outputPath.endsWith(File.separator)
                ? (outputPath + end)
                : (outputPath + File.separator + end);
    }
}
