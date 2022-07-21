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

import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.Document;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentType;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.ressec.avocado.core.exception.checked.FileException;
import org.ressec.avocado.core.helper.FileHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
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
    }

    @Test
    @DisplayName("Create a document with the minimal set of attributes")
    final void testCreateDocument() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getEntityType()).isEqualTo(EntityType.DOCUMENT);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.DOCUMENT_GENERIC);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a document without mandatory attributes")
    final void testCannotCreateDocumentWithoutName()
    {
        // Name is mandatory
        assertThrows(ConstraintViolationException.class, () ->
                Document.builder()
                    .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                    .build());
    }

    @Test
    @DisplayName("Create a document with all attributes")
    final void testCreateDocumentWithAllAttributes() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withDescription(DOCUMENT_DESCRIPTION)
                .withReference(DOCUMENT_REFERENCE)
                .withTags(new String[]{DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3})
                .withFilename(DOCUMENT_CONTENT_PDF)
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getDescription()).isEqualTo(DOCUMENT_DESCRIPTION);
        assertThat(document.getReference()).isEqualTo(DOCUMENT_REFERENCE);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG1);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG2);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG3);
        assertThat(document.getEntityType()).isEqualTo(EntityType.DOCUMENT);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.DOCUMENT_MEDIA);
    }

    @Test
    @DisplayName("Count the tags")
    final void testCountTags() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withTags(new String[]{DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3})
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG1);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG2);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG3);
        assertThat(document.getTags()).hasSize(3);
    }

    @Test
    @DisplayName("Cannot create duplicate tag")
    final void testCannotAddDuplicateTag() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withTags(new String[]{DOCUMENT_TAG1, DOCUMENT_TAG1, DOCUMENT_TAG3, DOCUMENT_TAG1})
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG1);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG3);
    }

    @Test
    @DisplayName("Delete a document tag")
    final void testDeleteTag() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withTags(new String[]{DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3})
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG1);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG2);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG3);
        assertThat(document.getTagCount()).isEqualTo(3);

        document.removeTag(DOCUMENT_TAG2);
        assertThat(document.getTagCount()).isEqualTo(2);
        assertThat(document.getTags()).doesNotContain(DOCUMENT_TAG2);
    }

    @Test
    @DisplayName("Create a document with a content file name")
    final void testCreateDocumentWithContentFilename() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .withFilename(DOCUMENT_CONTENT_PDF)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getContentLength()).isPositive();
    }

    @Test
    @DisplayName("Create a document with a content file")
    final void testCreateDocumentWithContentFile() throws DocumentException, FileException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .withFile(FileHelper.getFile(DOCUMENT_CONTENT_PDF))
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getContentPath()).isNull(); // As it has not been stored in the store yet!
        assertThat(document.getContentLength()).isPositive();
    }

    @Test
    @DisplayName("Set a document content after instance creation of the document")
    final void testSetDocumentContentAfterCreationWithFilename() throws DocumentException
    {
        final String documentName = "A Kind of Magic";
        final String documentDescription = "A Kind of Magic is the twelfth studio album by the British rock band Queen.";
        final String documentReference = "1986";

        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .build();

        assertThat(document).isNotNull();

        document.setContent(DOCUMENT_CONTENT_PDF);

        assertThat(document.getContent()).isNotNull();
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.DOCUMENT_MEDIA);
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getDescription()).isEqualTo(documentDescription);
        assertThat(document.getReference()).isEqualTo(documentReference);
        assertThat(document.getExtension()).isEqualTo("pdf");
        assertThat(document.getMimeType()).isEqualTo("application/pdf");
        assertThat(document.getFilename()).isEqualTo("java-8-streams-cheat-sheet.pdf");
    }

    @Test
    @DisplayName("Set a document content after instance creation given a file")
    final void testSetDocumentContentAfterCreationWithFile() throws DocumentException, FileException
    {
        final String documentName = "Java 8 Sheet";
        final String documentDescription = "A Java 8 reference sheet.";
        final String documentReference = "2021";

        IDocument document = Document.builder()
                .withName(documentName)
                .withDescription(documentDescription)
                .withReference(documentReference)
                .withDocumentType(DocumentType.DOCUMENT_INVOICE)
                .build();

        assertThat(document).isNotNull();

        document.setContent(FileHelper.getFile(DOCUMENT_CONTENT_PDF));

        assertThat(document.getContent()).isNotNull();
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.DOCUMENT_INVOICE);
        assertThat(document.getName()).isEqualTo(documentName);
        assertThat(document.getDescription()).isEqualTo(documentDescription);
        assertThat(document.getReference()).isEqualTo(documentReference);
        assertThat(document.getExtension()).isEqualTo("pdf");
        assertThat(document.getMimeType()).isEqualTo("application/pdf");
        assertThat(document.getFilename()).isEqualTo("java-8-streams-cheat-sheet.pdf");
    }

    @Test
    @DisplayName("Inactivate a document")
    final void testInactivateDocument() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withTags(new String[] { DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3 })
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .withStatusType(EntityStatusType.INACTIVE)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG1);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG2);
        assertThat(document.getTags()).containsOnlyOnce(DOCUMENT_TAG3);
        assertThat(document.getTags()).hasSize(3);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.INACTIVE);
        assertThat(document.getInactiveSince()).isNotNull();
    }

    @Test
    @DisplayName("Reactivate a document")
    final void testReactivateDocument() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName(DOCUMENT_NAME)
                .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                .withStatusType(EntityStatusType.INACTIVE)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(DOCUMENT_NAME);
        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.INACTIVE);
        assertThat(document.getInactiveSince()).isNotNull();

        document.setStatusType(EntityStatusType.ACTIVE);

        assertThat(document.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
        assertThat(document.getInactiveSince()).isNull();
    }

    @Test
    @Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 10'000 documents without content")
    final void testPerformanceCreateMultipleDocumentsWithoutContent() throws DocumentException
    {
        final int COUNT = 10000;
        List<IDocument> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            list.add(Document.builder()
                    .withName(DOCUMENT_NAME)
                    .withTags(new String[] { DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3 })
                    .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                    .build());
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @Timeout(value = 8000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 10'000 documents with content")
    final void testPerformanceCreateMultipleDocumentsWithContent() throws DocumentException
    {
        final int COUNT = 10000;
        List<IDocument> list = new ArrayList<>();
        IDocument document;

        for (int i = 0; i < COUNT; i++)
        {
            document = Document.builder()
                    .withName(DOCUMENT_NAME)
                    .withTags(new String[] { DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3 })
                    .withDocumentType(DocumentType.DOCUMENT_MEDIA)
                    .build();

            document.setContent(DOCUMENT_CONTENT_PDF);

            list.add(document);
        }

        assertThat(list).hasSize(COUNT);
    }
}
