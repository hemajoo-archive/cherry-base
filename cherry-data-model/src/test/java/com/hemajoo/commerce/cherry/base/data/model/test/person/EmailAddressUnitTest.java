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
package com.hemajoo.commerce.cherry.base.data.model.test.person;

import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests the <b>email address</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest
class EmailAddressUnitTest extends AbstractPersonUnitTest
{
    public EmailAddressUnitTest()
    {
        super();
    }

    @Test
    @DisplayName("Create an empty email address")
    final void testCreateEmptyEmailAddress()
    {
        IEmailAddress email = new EmailAddress(); // Using this constructor, no validation is done!

        assertThat(email).isNotNull();
        assertThat(email.getEntityType()).isEqualTo(EntityType.EMAIL_ADDRESS);
    }

    @Test
    @DisplayName("Create an email address with a minimal set of attributes")
    final void testCreateEmailAddressWithMinimalAttributeSet() throws DataModelEntityException
    {
        final String EMAIL_ADDRESS = FAKER.internet().emailAddress();

        IEmailAddress email = EmailAddress.builder()
                .withEmail(EMAIL_ADDRESS)
                .withAddressType(AddressType.PRIVATE)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getEmail()).isEqualTo(EMAIL_ADDRESS);
        assertThat(email.getIsDefaultEmail()).isFalse();
        assertThat(email.getAddressType()).isEqualTo(AddressType.PRIVATE);
        assertThat(email.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create an email address without an email")
    final void testCannotCreateEmailAddressWithoutEmail()
    {
        assertThrows(EmailAddressException.class, () ->
                EmailAddress.builder()
                        .withAddressType(AddressType.PROFESSIONAL)
                        .build());
    }

    @Test
    @DisplayName("Create an email address with a maximum of attributes")
    final void testCreateEmailAddressWithMaximalAttributes() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, true);
        IEmailAddress parent = EmailAddressRandomizer.generate(true, true, true, 1);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withName(name)
                .withDescription(description)
                .withReference(reference)
                .withTags(tags)
                .withIsDefault(emailIsDefault)
                .withStatusType(statusType)
                .withParent(parent)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getEmail()).isEqualTo(emailAddress);
        assertThat(email.getAddressType()).isEqualTo(emailAddressType);
        assertThat(email.getStatusType()).isEqualTo(statusType);
        assertThat(email.getIsDefaultEmail()).isEqualTo(emailIsDefault);
        assertThat(email.getName()).isEqualTo(name);
        assertThat(email.getDescription()).isEqualTo(description);
        assertThat(email.getReference()).isEqualTo(reference);
        assertThat(email.getTags()).containsAll(tags);
        assertThat(email.getTagCount()).isEqualTo(tags.size());
        assertThat((IDataModelEntity) email.getParent()).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException
    {
        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(DocumentRandomizer.generate(true, false))
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);

        email.addDocument(DocumentRandomizer.generate(true, true));
        assertThat(email.getDocumentCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Cannot add a duplicate document")
    final void testCannotAddDuplicateDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);
        IDocument other = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on instance
        email.addDocument(document);
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on UUID
        other.setId(document.getId());
        email.addDocument(other);
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on name
        other.setName(document.getName());
        email.addDocument(other);
        assertThat(email.getDocumentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Delete a document")
    final void testDeleteDocument() throws DataModelEntityException
    {
        IDocument document1 = DocumentRandomizer.generate(true, false);
        IDocument document2 = DocumentRandomizer.generate(true, false);
        IDocument document3 = DocumentRandomizer.generate(true, false);
        IDocument document4 = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .build();

        email.addDocument(document1);
        email.addDocument(document2);
        email.addDocument(document3);
        email.addDocument(document4);

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(4);

        // Delete document by instance
        email.deleteDocument(document1);
        assertThat(email.getDocumentCount()).isEqualTo(3);

        // Delete document by UUID
        email.deleteDocumentById(document2.getId());
        assertThat(email.getDocumentCount()).isEqualTo(2);

        // Delete document by id
        email.deleteDocumentById(document3.getId().toString());
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Delete document by name
        email.deleteDocumentByName(document4.getName());
        assertThat(email.getDocumentCount()).isZero();
    }

    @Test
    @DisplayName("Retrieve a document")
    final void testRetrieveDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat((IDocument) email.getDocument(document)).isEqualTo(document);

        // Get a document by UUID
        assertThat((IDocument) email.getDocumentById(document.getId())).isEqualTo(document);

        // Get a document by identifier
        assertThat((IDocument) email.getDocumentById(document.getId().toString())).isEqualTo(document);

        // Get a document by name
        assertThat((IDocument) email.getDocumentByName(document.getName())).isEqualTo(document);
    }

    @Test
    @DisplayName("Check if a document exist")
    final void testExistDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat(email.existDocument(document)).isTrue();

        // Get a document by identifier
        assertThat(email.existDocumentById(document.getId().toString())).isTrue();

        // Get a document by UUID
        assertThat(email.existDocumentById(document.getId())).isTrue();

        // Get a document by name
        assertThat(email.existDocumentByName(document.getName())).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException
    {
        int count = DocumentRandomizer.getRandomInt(1, 20);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isZero();

        for (int i = 0; i < count; i++)
        {
            email.addDocument(DocumentRandomizer.generate(true));
        }

        assertThat(email.getDocuments()).hasSize(count);
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 email addresses with documents and no content")
    final void testPerformanceCreateMultipleEmailAddressesWithoutDocumentContent() throws DataModelEntityException
    {
        final int count = 1000;
        List<IEmailAddress> list = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            // For each email address entity created, we also create:
            // - from 1 to 3 documents
            list.add(EmailAddressRandomizer.generate(true, true, false, EmailAddressRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(count);
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 email addresses with documents and content")
    final void testPerformanceCreateMultipleEmailAddressesWithDocumentAndContent() throws DataModelEntityException
    {
        final int count = 1000;
        List<IEmailAddress> list = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            // For each email address entity created, we also create:
            // - from 1 to 3 documents
            list.add(EmailAddressRandomizer.generate(true, true, true, EmailAddressRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(count);
    }
}
