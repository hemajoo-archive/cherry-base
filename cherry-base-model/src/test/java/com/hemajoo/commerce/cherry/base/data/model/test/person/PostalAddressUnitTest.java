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
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.address.AddressType;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.*;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
import com.hemajoo.commerce.cherry.base.utilities.generator.RandomNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests the <b>postal address</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest
class PostalAddressUnitTest extends AbstractPersonUnitTest
{
    public PostalAddressUnitTest()
    {
        super();
    }

    @Test
    @DisplayName("Create an empty postal address")
    final void testCreateEmptyPostalAddress()
    {
        IPostalAddress address = new PostalAddress(); // Using this constructor, no validation is done!

        assertThat(address).isNotNull();
        assertThat(address.getEntityType()).isEqualTo(EntityType.POSTAL_ADDRESS);
    }

    @Test
    @DisplayName("Create a postal address with a minimal set of attributes")
    final void testCreatePostalAddressWithMinimalAttributeSet() throws DataModelEntityException
    {
        IPostalAddress address = PostalAddress.builder()
                .withStreetName(PostalAddressRandomizer.getRandomStreetName())
                .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber())
                .withLocality(PostalAddressRandomizer.getRandomLocality())
                .withCountryCode(PostalAddressRandomizer.getRandomCountryCode())
                .withZipCode(PostalAddressRandomizer.getRandomZipCode())
                .build();

        assertThat(address).isNotNull();
        assertThat(address.getStreetName()).isNotNull();
        assertThat(address.getStreetNumber()).isNotNull();
        assertThat(address.getLocality()).isNotNull();
        assertThat(address.getCountryCode()).isNotNull();
        assertThat(address.getZipCode()).isNotNull();

        assertThat(address.getAddressType()).isEqualTo(AddressType.UNKNOWN);
        assertThat(address.getPostalAddressType()).isEqualTo(PostalAddressType.OTHER);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a postal address without")
    final void testCannotCreatePostalAddressWithout()
    {
        // Cannot create a postal address if the street name is not provided!
        assertThrows(PostalAddressException.class, () ->
                PostalAddress.builder()
                        .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber()) // Mandatory
                        .withLocality(PostalAddressRandomizer.getRandomLocality()) // Mandatory
                        .withCountryCode(PostalAddressRandomizer.getRandomCountryCode()) // Mandatory
                        .withZipCode(PostalAddressRandomizer.getRandomZipCode()) // Mandatory
                        .build());

        // Cannot create a postal address if the street number is not provided!
        assertThrows(PostalAddressException.class, () ->
                PostalAddress.builder()
                        .withStreetName(PostalAddressRandomizer.getRandomStreetName()) // Mandatory
                        .withLocality(PostalAddressRandomizer.getRandomLocality()) // Mandatory
                        .withCountryCode(PostalAddressRandomizer.getRandomCountryCode()) // Mandatory
                        .withZipCode(PostalAddressRandomizer.getRandomZipCode()) // Mandatory
                        .build());

        // Cannot create a postal address if the locality is not provided!
        assertThrows(PostalAddressException.class, () ->
                PostalAddress.builder()
                        .withStreetName(PostalAddressRandomizer.getRandomStreetName()) // Mandatory
                        .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber()) // Mandatory
                        .withCountryCode(PostalAddressRandomizer.getRandomCountryCode()) // Mandatory
                        .withZipCode(PostalAddressRandomizer.getRandomZipCode()) // Mandatory
                        .build());

        // Cannot create a postal address if the country code is not provided!
        assertThrows(PostalAddressException.class, () ->
                PostalAddress.builder()
                        .withStreetName(PostalAddressRandomizer.getRandomStreetName()) // Mandatory
                        .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber()) // Mandatory
                        .withLocality(PostalAddressRandomizer.getRandomLocality()) // Mandatory
                        .withZipCode(PostalAddressRandomizer.getRandomZipCode()) // Mandatory
                        .build());

        // Cannot create a postal address if the zip code is not provided!
        assertThrows(PostalAddressException.class, () ->
                PostalAddress.builder()
                        .withStreetName(PostalAddressRandomizer.getRandomStreetName()) // Mandatory
                        .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber()) // Mandatory
                        .withLocality(PostalAddressRandomizer.getRandomLocality()) // Mandatory
                        .withCountryCode(PostalAddressRandomizer.getRandomCountryCode()) // Mandatory
                        .build());
    }

    @Test
    @DisplayName("Create a postal address with a maximal set of attributes")
    final void testCreatePostalAddressWithMaximalAttributes() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, true);
        IEmailAddress parent = EmailAddressRandomizer.generate(true, true, true, 1);

        IPostalAddress address = PostalAddress.builder()
                .withStreetName(streetName)
                .withStreetNumber(streetNumber)
                .withLocality(locality)
                .withArea(area)
                .withCountryCode(countryCode)
                .withZipCode(zipCode)
                .withIsDefault(postalIsDefault)
                .withAddressType(postalAddressType)
                .withPostalAddressType(postalCategoryAddressType)
                .withStatusType(statusType)
                .withName(name)
                .withDescription(description)
                .withReference(reference)
                .withTags(tags)
                .withDocument(document)
                .withParent(parent)
                .build();

        assertThat(address).isNotNull();

        assertThat(address.getStreetName()).isEqualTo(streetName);
        assertThat(address.getStreetNumber()).isEqualTo(streetNumber);
        assertThat(address.getLocality()).isEqualTo(locality);
        assertThat(address.getArea()).isEqualTo(area);
        assertThat(address.getCountryCode()).isEqualTo(countryCode);
        assertThat(address.getZipCode()).isEqualTo(zipCode);
        assertThat(address.getIsDefault()).isEqualTo(postalIsDefault);
        assertThat(address.getAddressType()).isEqualTo(postalAddressType);
        assertThat(address.getPostalAddressType()).isEqualTo(postalCategoryAddressType);

        assertThat(address.getName()).isEqualTo(name);
        assertThat(address.getDescription()).isEqualTo(description);
        assertThat(address.getReference()).isEqualTo(reference);
        assertThat(address.getTags()).hasSize(tags.size());
        assertThat(address.getStatusType()).isEqualTo(statusType);
        assertThat((IDocument) address.getDocumentByName(document.getName())).isEqualTo(document);
        assertThat((IDataModelEntity) address.getParent()).isEqualTo(parent);
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress address = PostalAddress.builder()
                .withStreetName(PostalAddressRandomizer.getRandomStreetName())
                .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber())
                .withLocality(PostalAddressRandomizer.getRandomLocality())
                .withCountryCode(PostalAddressRandomizer.getRandomCountryCode())
                .withZipCode(PostalAddressRandomizer.getRandomZipCode())
                .withDocument(DocumentRandomizer.generate(true)) // Add a document using the builder
                .build();

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isEqualTo(1);

        address.addDocument(DocumentRandomizer.generate(true, true)); // Add a document using the service
        assertThat(address.getDocumentCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Cannot add a duplicate document")
    final void testCannotAddDuplicateDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, false);
        IDocument other = DocumentRandomizer.generate(true, false);

        IPostalAddress address = PostalAddress.builder()
                .withStreetName(PostalAddressRandomizer.getRandomStreetName())
                .withStreetNumber(PostalAddressRandomizer.getRandomStreetNumber())
                .withLocality(PostalAddressRandomizer.getRandomLocality())
                .withCountryCode(PostalAddressRandomizer.getRandomCountryCode())
                .withZipCode(PostalAddressRandomizer.getRandomZipCode())
                .withDocument(document)
                .build();

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on instance
        address.addDocument(document);
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on UUID
        other.setId(document.getId());
        address.addDocument(other);
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Cannot add a duplicate document based on name
        other.setName(document.getName());
        address.addDocument(other);
        assertThat(address.getDocumentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Delete a document")
    final void testDeleteDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document1 = DocumentRandomizer.generate(true, false);
        IDocument document2 = DocumentRandomizer.generate(true, false);
        IDocument document3 = DocumentRandomizer.generate(true, false);
        IDocument document4 = DocumentRandomizer.generate(true, false);

        IPostalAddress address = generatePostalAddress();

        address.addDocument(document1);
        address.addDocument(document2);
        address.addDocument(document3);
        address.addDocument(document4);

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isEqualTo(4);

        // Delete document by instance
        address.deleteDocument(document1);
        assertThat(address.getDocumentCount()).isEqualTo(3);

        // Delete document by UUID
        address.deleteDocumentById(document2.getId());
        assertThat(address.getDocumentCount()).isEqualTo(2);

        // Delete document by id
        address.deleteDocumentById(document3.getId().toString());
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Delete document by name
        address.deleteDocumentByName(document4.getName());
        assertThat(address.getDocumentCount()).isZero();
    }

    @Test
    @DisplayName("Retrieve a document")
    final void testRetrieveDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IPostalAddress address = generatePostalAddress();
        address.addDocument(document);

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat((IDocument) address.getDocument(document)).isEqualTo(document);

        // Get a document by UUID
        assertThat((IDocument) address.getDocumentById(document.getId())).isEqualTo(document);

        // Get a document by identifier
        assertThat((IDocument) address.getDocumentById(document.getId().toString())).isEqualTo(document);

        // Get a document by name
        assertThat((IDocument) address.getDocumentByName(document.getName())).isEqualTo(document);
    }

    @Test
    @DisplayName("Check if a document exist")
    final void testExistDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, false);

        IPostalAddress address = generatePostalAddress();
        address.addDocument(document);

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isEqualTo(1);

        // Get a document by instance
        assertThat(address.existDocument(document)).isTrue();

        // Get a document by identifier
        assertThat(address.existDocumentById(document.getId().toString())).isTrue();

        // Get a document by UUID
        assertThat(address.existDocumentById(document.getId())).isTrue();

        // Get a document by name
        assertThat(address.existDocumentByName(document.getName())).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException, GeneratorException
    {
        int count = RandomNumberGenerator.nextInt(1, 20);

        IPostalAddress address = generatePostalAddress();

        assertThat(address).isNotNull();
        assertThat(address.getDocumentCount()).isZero();

        for (int i = 0; i < count; i++)
        {
            address.addDocument(DocumentRandomizer.generate(true));
        }

        assertThat(address.getDocuments()).hasSize(count);
    }

    @Test
    //@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 postal addresses with documents and no content")
    final void testPerformanceCreateMultiplePostalAddressesWithoutDocumentContent() throws DataModelEntityException, GeneratorException
    {
        final int count = 1000;
        Set<IPostalAddress> addresses = new HashSet<>();

        for (int i = 0; i < count; i++)
        {
            // For each postal address entity created, we also create:
            // - from 1 to 3 documents
            addresses.add(PostalAddressRandomizer.generate(true, true, false, RandomNumberGenerator.nextInt(1, 3)));
        }

        assertThat(addresses).hasSize(count);
    }

    @Test
    //@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 email addresses with documents and content")
    final void testPerformanceCreateMultiplePostalAddressesWithDocumentAndContent() throws DataModelEntityException, GeneratorException
    {
        final int count = 1000;
        Set<IPostalAddress> addresses = new HashSet<>();

        for (int i = 0; i < count; i++)
        {
            // For each email address entity created, we also create:
            // - from 1 to 3 documents
            addresses.add(PostalAddressRandomizer.generate(true, true, true, RandomNumberGenerator.nextInt(1, 3)));
        }

        assertThat(addresses).hasSize(count);
    }
}
