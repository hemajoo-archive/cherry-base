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
package com.hemajoo.commerce.cherry.base.data.model.test.document;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.ressec.avocado.core.exception.checked.FileException;
import org.ressec.avocado.core.helper.FileHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests the <b>document</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@SpringBootTest
class DocumentUnitTest extends AbstractDocumentUnitTest
{
    @Test
    @DisplayName("Create an empty document")
    final void testCreateEmptyDocument()
    {
        IDocument document = new Document();

        assertThat(document).isNotNull();
        assertThat(document.getEntityType()).isEqualTo(EntityType.DOCUMENT);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
    }

    @Test
    @DisplayName("Create a document with the minimal set of attributes")
    final void testCreateDocument() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getEntityType()).isEqualTo(EntityType.DOCUMENT);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.GENERIC);
    }

    @Test
    @DisplayName("Create a document with all attributes")
    final void testCreateDocumentWithAllAttributes() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .withStatusType(EntityStatusType.INACTIVE)
                .withFilename(documentFilename)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getDescription()).isEqualTo(documentDescription);
        assertThat(document.getReference()).isEqualTo(documentReference);
        assertThat(document.getEntityType()).isEqualTo(EntityType.DOCUMENT);
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.MEDIA);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.INACTIVE);
        assertThat(document.getInactiveSince()).isNotNull();
        assertThat(document.getTags()).hasSameSizeAs(documentTags);
        assertThat(document.getTags()).containsAll(documentTags);
        assertThat(document.getExtension()).isNotNull();
        assertThat(document.getFilename()).isNotNull();
        assertThat(document.getOriginalFilename()).isNotNull();
        assertThat(document.getContentLength()).isPositive();
        assertThat(document.getContent()).isNotNull();
        assertThat(document.getMimeType()).isNotNull();
        assertThat(document.getContentId()).isNull();
        assertThat(document.getContentPath()).isNull();
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a document without mandatory attributes")
    final void testCannotCreateDocumentWithoutName()
    {
        // Name is mandatory
        assertThrows(DocumentException.class, () ->
                Document.builder()
                        .withDocumentType(documentType)
                        .build());
    }

    @Test
    @DisplayName("Add a tag")
    final void testAddTag() throws DataModelEntityException
    {
        final String tag = FAKER.country().countryCode2(); // We know this one cannot be part of the initial tag list!

        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getTags()).hasSameSizeAs(documentTags);

        assertThat(document.getTags()).containsAll(documentTags);

        document.addTag(tag);

        assertThat(document.getTags()).contains(tag);
        assertThat(document.getTags()).hasSize(documentTags.size() + 1);
    }

    @Test
    @DisplayName("Cannot create duplicate tag")
    final void testCannotAddDuplicateTag() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);

        assertThat(document.getTags()).hasSameSizeAs(documentTags);
        assertThat(document.getTags()).containsAll(documentTags);

        document.addTag(List.copyOf(documentTags).get(0));
        document.addTag(List.copyOf(documentTags).get(0));

        assertThat(document.getTags()).containsOnlyOnce(List.copyOf(documentTags).get(0));
    }

    @Test
    @DisplayName("Count the number of tags")
    final void testCountTags() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);

        assertThat(document.getTagCount()).isEqualTo(documentTags.size());
        assertThat(document.getTags()).containsAll(documentTags);
    }

    @Test
    @DisplayName("Delete a document tag")
    final void testDeleteTag() throws DataModelEntityException
    {
        int size = documentTags.size();

        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getTagCount()).isEqualTo(documentTags.size());
        assertThat(document.getTags()).containsAll(documentTags);

        document.deleteTag(List.copyOf(documentTags).get(0));

        assertThat(document.getTags()).doesNotContain(List.copyOf(documentTags).get(0));
        assertThat(document.getTagCount()).isEqualTo(size - 1);
    }

    @Test
    @DisplayName("Delete all document tags")
    final void testDeleteAllTags() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getTagCount()).isEqualTo(documentTags.size());
        assertThat(document.getTags()).containsAll(documentTags);

        document.deleteAllTags();

        assertThat(document.getTagCount()).isZero();
    }

    @Test
    @DisplayName("Check if a tag exist")
    final void testExistTag() throws DataModelEntityException
    {
        final String tag = FAKER.beer().name(); // We know this one cannot be part of initial tag list!

        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document.existTag(List.copyOf(documentTags).get(0))).isTrue();
        assertThat(document.existTag(tag)).isFalse();
        assertThat(document.getTagCount()).isEqualTo(documentTags.size());
        assertThat(document.getTags()).containsAll(documentTags);
    }

    @Test
    @DisplayName("Create a document with a content file name")
    final void testCreateDocumentWithContentFilename() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.GENERIC)
                .withFilename(documentFilename)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getContentLength()).isPositive();
    }

    @Test
    @DisplayName("Create a document with a content file")
    final void testCreateDocumentWithContentFile() throws DataModelEntityException, FileException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.GENERIC)
                .withFile(FileHelper.getFile(documentFilename))
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getContentPath()).isNull(); // As it has not been stored in the store yet!
        assertThat(document.getContentLength()).isPositive();
    }

    @Test
    @DisplayName("Set a document content after instance creation of the document")
    final void testSetDocumentContentAfterCreationWithFilename() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.MEDIA)
                .build();

        assertThat(document).isNotNull();

        document.setContent(documentFilename);

        assertThat(document.getContent()).isNotNull();
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.MEDIA);
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getDescription()).isEqualTo(documentDescription);
        assertThat(document.getReference()).isEqualTo(documentReference);
        assertThat(document.getExtension()).isNotNull();
        assertThat(document.getMimeType()).isNotNull();
        assertThat(document.getFilename()).isNotNull();
    }

    @Test
    @DisplayName("Set a document content after instance creation given a file")
    final void testSetDocumentContentAfterCreationWithFile() throws DataModelEntityException, FileException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.INVOICE)
                .build();

        assertThat(document).isNotNull();

        document.setContent(FileHelper.getFile(documentFilename));

        assertThat(document.getContent()).isNotNull();
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.INVOICE);
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getDescription()).isEqualTo(documentDescription);
        assertThat(document.getReference()).isEqualTo(documentReference);
        assertThat(document.getExtension()).isNotNull();
        assertThat(document.getMimeType()).isNotNull();
        assertThat(document.getFilename()).isNotNull();
    }

    @Test
    @DisplayName("Inactivate a document")
    final void testInactivateDocument() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withTags(documentTags)
                .withDocumentType(DocumentType.MEDIA)
                .withStatusType(EntityStatusType.INACTIVE)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.INACTIVE);
        assertThat(document.getInactiveSince()).isNotNull();
        assertThat(document.getTagCount()).isEqualTo(documentTags.size());
        assertThat(document.getTags()).containsAll(documentTags);
    }

    @Test
    @DisplayName("Reactivate a document")
    final void testReactivateDocument() throws DataModelEntityException
    {
        IDocument document = Document.builder()
                .withName(documentName)
                .withDocumentType(DocumentType.MEDIA)
                .withStatusType(EntityStatusType.INACTIVE)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.INACTIVE);
        assertThat(document.getInactiveSince()).isNotNull();

        document.setStatusType(EntityStatusType.ACTIVE);

        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
        assertThat(document.getInactiveSince()).isNull();
    }

    @Test
    @Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 10'000 documents without content")
    final void testPerformanceCreateMultipleDocumentsWithoutContent() throws DataModelEntityException
    {
        final int COUNT = 10000;
        List<IDocument> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            list.add(Document.builder()
                    .withName(DocumentRandomizer.getRandomName())
                    .withTags(DocumentRandomizer.getRandomTagList())
                    .withDocumentType(DocumentType.MEDIA)
                    .build());
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @Timeout(value = 8000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 10'000 documents with content")
    final void testPerformanceCreateMultipleDocumentsWithContent() throws DataModelEntityException
    {
        final int COUNT = 10000;
        List<IDocument> list = new ArrayList<>();
        IDocument document;

        for (int i = 0; i < COUNT; i++)
        {
            document = Document.builder()
                    .withName(DocumentRandomizer.getRandomName())
                    .withTags(DocumentRandomizer.getRandomTagList())
                    .withDocumentType(DocumentType.MEDIA)
                    .build();

            document.setContent(DocumentRandomizer.getRandomFilename());

            list.add(document);
        }

        assertThat(list).hasSize(COUNT);
    }
}
