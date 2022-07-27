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
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests the <b>phone number</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest
class PhoneNumberUnitTest extends AbstractPersonUnitTest
{
    public PhoneNumberUnitTest()
    {
        super();
    }

    @Test
    @DisplayName("Create an empty phone number")
    final void testCreateEmptyPhoneNumber()
    {
        IPhoneNumber phone = new PhoneNumber(); // Using this constructor, no validation is done!

        assertThat(phone).isNotNull();
        assertThat(phone.getEntityType()).isEqualTo(EntityType.PHONE_NUMBER);
    }

    @Test
    @DisplayName("Create a phone number with a minimal set of attributes")
    final void testCreatePhoneNumberWithMinimalAttributeSet() throws DataModelEntityException
    {
        IPhoneNumber phone = PhoneNumber.builder()
                .withNumber(PhoneNumberRandomizer.getRandomNumber())
                .withCountryCode(PhoneNumberRandomizer.getRandomCountryCode())
                .build();

        assertThat(phone).isNotNull();
        assertThat(phone.getNumber()).isNotNull();
        assertThat(phone.getCountryCode()).isNotNull();
        assertThat(phone.getPhoneType()).isEqualTo(PhoneNumberType.OTHER);
        assertThat(phone.getCategoryType()).isEqualTo(PhoneNumberCategoryType.OTHER);

        assertThat(phone.getStatusType()).isEqualTo(EntityStatusType.ACTIVE);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a phone number without")
    final void testCannotCreatePhoneNumberWithout()
    {
        // Cannot create a phone number if the number is not provided!
        assertThrows(PhoneNumberException.class, () ->
                PhoneNumber.builder()
                        .withNumber(PhoneNumberRandomizer.getRandomNumber()) // Mandatory
                        .build());

        // Cannot create a phone number if the country code is not provided!
        assertThrows(PhoneNumberException.class, () ->
                PhoneNumber.builder()
                        .withCountryCode(PhoneNumberRandomizer.getRandomCountryCode()) // Mandatory
                        .build());
    }

    @Test
    @DisplayName("Create a phone number with a maximal set of attributes")
    final void testCreatePhoneNumberWithMaximalAttributes() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, true);
        IPhoneNumber parent = PhoneNumberRandomizer.generate(true, true, true, 1);

        IPhoneNumber phone = PhoneNumber.builder()
                .withNumber(phoneNumber)
                .withPhoneType(phoneNumberType)
                .withCategoryType(phoneNumberCategoryType)
                .withCountryCode(countryCode)
                .withIsDefault(phoneIsDefault)
                .withStatusType(statusType)
                .withName(name)
                .withDescription(description)
                .withReference(reference)
                .withTags(tags)
                .withDocument(document)
                .withParent(parent)
                .build();

        assertThat(phone).isNotNull();

        assertThat(phone.getNumber()).isEqualTo(phoneNumber);
        assertThat(phone.getPhoneType()).isEqualTo(phoneNumberType);
        assertThat(phone.getCategoryType()).isEqualTo(phoneNumberCategoryType);
        assertThat(phone.getCountryCode()).isEqualTo(countryCode);
        assertThat(phone.getIsDefault()).isEqualTo(phoneIsDefault);

        assertThat(phone.getName()).isEqualTo(name);
        assertThat(phone.getDescription()).isEqualTo(description);
        assertThat(phone.getReference()).isEqualTo(reference);
        assertThat(phone.getTags()).hasSize(tags.size());
        assertThat(phone.getStatusType()).isEqualTo(statusType);
        assertThat((IDocument) phone.getDocumentByName(document.getName())).isEqualTo(document);
        assertThat((IDataModelEntity) phone.getParent()).isEqualTo(parent);
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException
    {
        IPhoneNumber phone = PhoneNumber.builder()
                .withNumber(PhoneNumberRandomizer.getRandomNumber())
                .withCountryCode(PhoneNumberRandomizer.getRandomCountryCode())
                .withDocument(DocumentRandomizer.generate(true)) // Add a document using the builder
                .build();

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        phone.addDocument(DocumentRandomizer.generate(true, true)); // Add a document using the service
        assertThat(phone.getDocumentCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Cannot add a duplicate document")
    final void testCannotAddDuplicateDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);
        IDocument other = DocumentRandomizer.generate(true, false);

        IPhoneNumber phone = PhoneNumberRandomizer.generate(true, false);

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isZero();

        phone.addDocument(document);
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on instance
        phone.addDocument(document);
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on UUID
        other.setId(document.getId());
        phone.addDocument(other);
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on name
        other.setName(document.getName());
        phone.addDocument(other);
        assertThat(phone.getDocumentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Delete a document")
    final void testDeleteDocument() throws DataModelEntityException
    {
        IDocument document1 = DocumentRandomizer.generate(true, false);
        IDocument document2 = DocumentRandomizer.generate(true, false);
        IDocument document3 = DocumentRandomizer.generate(true, false);
        IDocument document4 = DocumentRandomizer.generate(true, false);

        IPhoneNumber phone = generatePhoneNumber();

        phone.addDocument(document1);
        phone.addDocument(document2);
        phone.addDocument(document3);
        phone.addDocument(document4);

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isEqualTo(4);

        // Delete document by instance
        phone.deleteDocument(document1);
        assertThat(phone.getDocumentCount()).isEqualTo(3);

        // Delete document by UUID
        phone.deleteDocumentById(document2.getId());
        assertThat(phone.getDocumentCount()).isEqualTo(2);

        // Delete document by id
        phone.deleteDocumentById(document3.getId().toString());
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Delete document by name
        phone.deleteDocumentByName(document4.getName());
        assertThat(phone.getDocumentCount()).isZero();
    }

    @Test
    @DisplayName("Retrieve a document")
    final void testRetrieveDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IPhoneNumber phone = generatePhoneNumber();
        phone.addDocument(document);

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat((IDocument) phone.getDocument(document)).isEqualTo(document);

        // Get a document by UUID
        assertThat((IDocument) phone.getDocumentById(document.getId())).isEqualTo(document);

        // Get a document by identifier
        assertThat((IDocument) phone.getDocumentById(document.getId().toString())).isEqualTo(document);

        // Get a document by name
        assertThat((IDocument) phone.getDocumentByName(document.getName())).isEqualTo(document);
    }

    @Test
    @DisplayName("Check if a document exist")
    final void testExistDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IPhoneNumber phone = generatePhoneNumber();
        phone.addDocument(document);

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat(phone.existDocument(document)).isTrue();

        // Get a document by identifier
        assertThat(phone.existDocumentById(document.getId().toString())).isTrue();

        // Get a document by UUID
        assertThat(phone.existDocumentById(document.getId())).isTrue();

        // Get a document by name
        assertThat(phone.existDocumentByName(document.getName())).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException
    {
        int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 20);

        IPhoneNumber phone = generatePhoneNumber();

        assertThat(phone).isNotNull();
        assertThat(phone.getDocumentCount()).isZero();

        for (int i = 0; i < count; i++)
        {
            phone.addDocument(DocumentRandomizer.generate(true));
        }

        assertThat(phone.getDocuments()).hasSize(count);
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 phone numbers with documents and no content")
    final void testPerformanceCreateMultiplePhoneNumbersWithoutDocumentContent() throws DataModelEntityException
    {
        final int count = 1000;
        Set<IPhoneNumber> phones = new HashSet<>();

        for (int i = 0; i < count; i++)
        {
            // For each phone number entity created, we also create:
            // - from 1 to 3 documents
            phones.add(PhoneNumberRandomizer.generate(true, true, false, AbstractDataModelEntityRandomizer.getRandomInt(1, 3)));
        }

        assertThat(phones).hasSize(count);
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 phone numbers with documents and content")
    final void testPerformanceCreateMultiplePhoneNumbersWithDocumentAndContent() throws DataModelEntityException
    {
        final int count = 1000;
        Set<IPhoneNumber> phones = new HashSet<>();

        for (int i = 0; i < count; i++)
        {
            // For each phone number entity created, we also create:
            // - from 1 to 3 documents
            phones.add(PhoneNumberRandomizer.generate(true, true, true, AbstractDataModelEntityRandomizer.getRandomInt(1, 3)));
        }

        assertThat(phones).hasSize(count);
    }
}
