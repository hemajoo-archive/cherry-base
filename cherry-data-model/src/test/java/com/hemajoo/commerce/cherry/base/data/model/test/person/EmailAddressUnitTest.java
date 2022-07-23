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
    @DisplayName("Create an email address with a maximal set of attributes")
    final void testCreateEmailAddressWithMaximalAttributeSet() throws DataModelEntityException
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
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .build();

        email.addDocument(document);

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);
        assertThat(email.getDocuments().get(0).getName()).isEqualTo(document.getName());
        assertThat(email.getDocuments().get(0).getId()).isEqualTo(document.getId());
    }

    @Test
    @DisplayName("Remove a document")
    final void testRemoveDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);
        assertThat(email.getDocuments().get(0).getName()).isEqualTo(document.getName());
        assertThat(email.getDocuments().get(0).getId()).isEqualTo(document.getId());

        email.removeDocument(document);

        assertThat(email.getDocumentCount()).isZero();
    }

    @Test
    @DisplayName("Get a document by its name")
    final void testGetDocumentByName() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);
        assertThat(email.getDocuments().get(0).getName()).isEqualTo(document.getName());
        assertThat(email.getDocuments().get(0).getId()).isEqualTo(document.getId());

        IDocument other = email.getDocumentByName(document.getName());
        assertThat(other).isNotNull();
        assertThat(other.getName()).isEqualTo(document.getName());
    }

    @Test
    @DisplayName("Get a document by its identifier")
    final void testGetDocumentById() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .withDocument(document)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isEqualTo(1);
        assertThat(email.getDocuments().get(0).getName()).isEqualTo(document.getName());
        assertThat(email.getDocuments().get(0).getId()).isEqualTo(document.getId());

        IDocument other = email.getDocumentById(document.getId());
        assertThat(other).isNotNull();
        assertThat(other.getId().toString()).isEqualTo(document.getId().toString());
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
        assertThat(email.getDocuments().get(0).getName()).isEqualTo(document.getName());
        assertThat(email.getDocuments().get(0).getId()).isEqualTo(document.getId());

        assertThat(email.existDocument(document)).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException
    {
        IEmailAddress email = EmailAddress.builder()
                .withEmail(emailAddress)
                .withAddressType(emailAddressType)
                .build();

        assertThat(email).isNotNull();
        assertThat(email.getDocumentCount()).isZero();

        for (int i = 0; i < 10; i++)
        {
            email.addDocument(DocumentRandomizer.generate(true));
        }

        assertThat(email.getDocumentCount()).isEqualTo(10);
        assertThat(email.getDocuments()).hasSize(10);
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 email addresses with documents and no content")
    final void testPerformanceCreateMultipleEmailAddressesWithoutDocumentContent() throws DataModelEntityException
    {
        final int COUNT = 1000;
        List<IEmailAddress> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            // For each email address entity created, we also create:
            // - from 1 to 3 documents
            list.add(EmailAddressRandomizer.generate(true, true, false, EmailAddressRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 email addresses with documents and content")
    final void testPerformanceCreateMultipleEmailAddressesWithDocumentAndContent() throws DataModelEntityException
    {
        final int COUNT = 1000;
        List<IEmailAddress> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            // For each email address entity created, we also create:
            // - from 1 to 3 documents
            list.add(EmailAddressRandomizer.generate(true, true, true, EmailAddressRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(COUNT);
    }
}
