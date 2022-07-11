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
class DocumentUnitTest
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
    @DisplayName("Create a document with a name")
    final void testCreateDocumentWithName() throws DocumentException
    {
        final String NAME = "Into the wild";

        IDocument document = Document.builder()
                .withName(NAME)
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Create a document without a name should raise a ConstraintViolationException")
    final void testCannotCreateDocumentWithoutName()
    {
        assertThrows(ConstraintViolationException.class, () ->
                Document.builder()
                    .withReference("2021")
                    .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                    .withFilename("./media/java-8-streams-cheat-sheet.pdf")
                    .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Create a document without a document type should set the document type to: DOCUMENT_GENERIC")
    final void testCreateDocumentWithoutType() throws DocumentException
    {
        final String NAME = "Into the wild";

            IDocument document = Document.builder()
                    .withName(NAME)
                    .build();

        assertThat(document).isNotNull();
        assertThat(document.getDocumentType()).isEqualTo(DocumentType.DOCUMENT_GENERIC);
    }

    @Test
    @DisplayName("Create a document with a description")
    final void testCreateDocumentWithDescription() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String DESCRIPTION = "A great movie distributed from Paramount Vantage";

        IDocument document = Document.builder()
                .withName(NAME)
                .withDescription(DESCRIPTION)
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    @DisplayName("Create a document with a reference")
    final void testCreateDocumentWithReference() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String REFERENCE = "Release date: September 21, 2007";

        IDocument document = Document.builder()
                .withName(NAME)
                .withReference(REFERENCE)
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getReference()).isEqualTo(REFERENCE);
    }

    @Test
    @DisplayName("Create a document with several tags")
    final void testCreateDocumentWithTags() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String TAG1 = "Sean Penn";
        final String TAG2 = "Mickael Brook";
        final String TAG3 = "Emile Hirsch";

        IDocument document = Document.builder()
                .withName(NAME)
                .withTags(new String[]{ TAG1, TAG2, TAG3 })
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getTags()).containsOnlyOnce(TAG1);
        assertThat(document.getTags()).containsOnlyOnce(TAG2);
        assertThat(document.getTags()).containsOnlyOnce(TAG3);
    }

    @Test
    @DisplayName("Count the document tags")
    final void testCountDocumentTags() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String TAG1 = "Sean Penn";
        final String TAG2 = "Mickael Brook";
        final String TAG3 = "Emile Hirsch";

        IDocument document = Document.builder()
                .withName(NAME)
                .withTags(new String[]{ TAG1, TAG2, TAG3 })
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getTags()).containsOnlyOnce(TAG1);
        assertThat(document.getTags()).containsOnlyOnce(TAG2);
        assertThat(document.getTags()).containsOnlyOnce(TAG3);
        assertThat(document.getTagCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("Cannot create duplicate tag!")
    final void testCannotAddDuplicateTag() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String TAG1 = "Sean Penn";
        final String TAG3 = "Emile Hirsch";

        IDocument document = Document.builder()
                .withName(NAME)
                .withTags(new String[]{ TAG1, TAG1, TAG3, TAG1 })
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getTags()).containsOnlyOnce(TAG1);
        assertThat(document.getTags()).containsOnlyOnce(TAG3);
    }

    @Test
    @DisplayName("Delete a document tag")
    final void testDeleteDocumentTag() throws DocumentException
    {
        final String NAME = "Into the wild";
        final String TAG1 = "Sean Penn";
        final String TAG2 = "Mickael Brook";
        final String TAG3 = "Emile Hirsch";

        IDocument document = Document.builder()
                .withName(NAME)
                .withTags(new String[]{ TAG1, TAG2, TAG3 })
                .withDocumentType(DocumentType.MEDIA_VIDEO)
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getName()).isEqualTo(NAME);
        assertThat(document.getTags()).containsOnlyOnce(TAG1);
        assertThat(document.getTags()).containsOnlyOnce(TAG2);
        assertThat(document.getTags()).containsOnlyOnce(TAG3);
        assertThat(document.getTagCount()).isEqualTo(3);

        document.removeTag(TAG2);
        assertThat(document.getTagCount()).isEqualTo(2);
        assertThat(document.getTags()).doesNotContain(TAG2);
    }

    @Test
    @DisplayName("Create a document with a file")
    final void testDocumentCreateWithFile() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .withFilename("./media/java-8-streams-cheat-sheet.pdf")
                .build();

        assertThat(document).isNotNull();
        assertThat(document.getContentLength()).isPositive();
    }

    @Test
    @DisplayName("Cannot set document content without a file!")
    final void testCannotSetDocumentContentWithoutFile() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .build();

        assertThat(document).isNotNull();
        assertThrows(DocumentException.class, document::setContent);
    }

    @Test
    @DisplayName("Set a document content given a file name")
    final void testSetDocumentContentWithFilename() throws DocumentException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .build();

        assertThat(document).isNotNull();

        document.setContent("./media/java-8-streams-cheat-sheet.pdf");

        assertThat(document.getContent()).isNotNull();
    }

    @Test
    @DisplayName("Set a document content given a file")
    final void testSetDocumentContentWithFile() throws DocumentException, FileException
    {
        IDocument document = Document.builder()
                .withName("Java 8 Sheet")
                .withDescription("A Java 8 reference sheet.")
                .withReference("2021")
                .withDocumentType(DocumentType.DOCUMENT_GENERIC)
                .build();

        assertThat(document).isNotNull();

        document.setContent(FileHelper.getFile("./media/java-8-streams-cheat-sheet.pdf"));

        assertThat(document.getContent()).isNotNull();
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS) // Creating 10'000 instances must not exceed 500 ms!
    @DisplayName("Create 10'000 documents with tags and validation of data")
    final void testCreateMultipleDocumentWithTags() throws DocumentException
    {
        final int COUNT = 10000;
        List<IDocument> list = new ArrayList<>();

        final String NAME = "Into the wild";
        final String TAG1 = "Sean Penn";
        final String TAG2 = "Mickael Brook";
        final String TAG3 = "Emile Hirsch";

        for (int i = 0; i < COUNT; i++)
        {
            list.add(Document.builder()
                    .withName(NAME)
                    .withTags(new String[]{ TAG1, TAG2, TAG3 })
                    .withDocumentType(DocumentType.MEDIA_VIDEO)
                    .build());
        }

        assertThat(list).hasSize(COUNT);
    }
}
