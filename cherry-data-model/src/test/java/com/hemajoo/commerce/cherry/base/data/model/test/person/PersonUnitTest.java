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
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.data.model.person.*;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.address.email.IEmailAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.IPostalAddress;
import com.hemajoo.commerce.cherry.base.data.model.person.address.postal.PostalAddressRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.IPhoneNumber;
import com.hemajoo.commerce.cherry.base.data.model.person.phone.PhoneNumberRandomizer;
import com.hemajoo.commerce.cherry.base.utilities.generator.GeneratorException;
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
 * Unit tests the <b>person</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest
class PersonUnitTest extends AbstractPersonUnitTest
{
    @Test
    @DisplayName("Create an empty person")
    final void testCreateEmptyPerson()
    {
        IPerson person = new Person(); // Using this constructor, no validation is done!

        assertThat(person).isNotNull();
        assertThat(person.getEntityType()).isEqualTo(EntityType.PERSON);
    }

    @Test
    @DisplayName("Create a person with minimal attribute set")
    final void testCreatePersonWithMinimalAttributeSet() throws DataModelEntityException
    {
        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getGenderType()).isEqualTo(genderType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);

        person = Person.builder()
                .withPersonType(PersonType.VIRTUAL)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.VIRTUAL);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a person without")
    final void testCannotCreatePersonWithout()
    {
        // Without last name
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withFirstName(personFirstName)
                        .withPersonType(personType)
                        .withGenderType(genderType)
                        .build());

        // Without first name
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withPersonType(personType)
                        .withLastName(personLastName)
                        .withGenderType(genderType)
                        .build());

        // Without person type
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withLastName(personLastName)
                        .withFirstName(personFirstName)
                        .withGenderType(genderType)
                        .build());

        // Without gender type if person type is PHYSICAL
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withLastName(personLastName)
                        .withFirstName(personFirstName)
                        .withPersonType(PersonType.PHYSICAL)
                        .build());
    }

    @Test
    @DisplayName("Create a person with a maximal set of attributes")
    final void testCreatePersonWithMaximalAttributeSet() throws DataModelEntityException, GeneratorException
    {
        IPerson parent = PersonRandomizer.generate(true, false, false, false, false, false, 0);

        IPerson person = Person.builder()
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withBirthDate(personBirthDate)
                .withPersonType(personType)
                .withGenderType(genderType)
                .withTags(tags)
                .withName(name)
                .withStatusType(statusType)
                .withDescription(description)
                .withReference(reference)
                .withDocument(DocumentRandomizer.generate(true))
                .withEmailAddress(EmailAddressRandomizer.generate(true, false))
                .withPostalAddress(PostalAddressRandomizer.generate(true, false))
                .withPhoneNumber(PhoneNumberRandomizer.generate(true, false))
                .withParent(parent)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getGenderType()).isEqualTo(genderType);
        assertThat(person.getBirthDate()).isEqualTo(personBirthDate);
        assertThat(person.getDescription()).isEqualTo(description);
        assertThat(person.getReference()).isEqualTo(reference);
        assertThat(person.getTagCount()).isEqualTo(tags.size());
        assertThat(person.getTags()).containsAll(tags);

        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat(person.getEmailAddressCount()).isEqualTo(1);
        assertThat(person.getPostalAddressCount()).isEqualTo(1);
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);

        assertThat((IDataModelEntity) person.getParent()).isNotNull();
        assertThat(((IPerson) person.getParent()).getLastName()).isEqualTo(parent.getLastName());
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withDocument(document) // Add a document using the builder service
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNotNull();

        person.deleteAllDocuments();
        person.addDocument(document); // Add a document using the standard service

        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNotNull();
    }

    @Test
    @DisplayName("Delete a document")
    final void testDeleteDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document1 = DocumentRandomizer.generate(true, true);
        IDocument document2 = DocumentRandomizer.generate(true, true);
        IDocument document3 = DocumentRandomizer.generate(true, true);
        IDocument document4 = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addDocument(document1);
        person.addDocument(document2);
        person.addDocument(document3);
        person.addDocument(document4);

        assertThat(person.getDocumentCount()).isEqualTo(4);

        // Delete document by instance
        person.deleteDocument(document1);
        assertThat(person.getDocumentCount()).isEqualTo(3);
        assertThat((IDocument) person.getDocumentByName(document1.getName())).isNull();

        // Delete document by UUID
        person.deleteDocumentById(document2.getId());
        assertThat(person.getDocumentCount()).isEqualTo(2);
        assertThat((IDocument) person.getDocumentByName(document2.getName())).isNull();

        // Delete document by id
        person.deleteDocumentById(document3.getId().toString());
        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat((IDocument) person.getDocumentByName(document3.getName())).isNull();

        // Delete document by name
        person.deleteDocumentByName(document4.getName());
        assertThat(person.getDocumentCount()).isZero();
        assertThat((IDocument) person.getDocumentByName(document4.getName())).isNull();
    }

    @Test
    @DisplayName("Retrieve a document")
    final void testRetrieveDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withDocument(document)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getDocumentCount()).isEqualTo(1);

        // Retrieve document by instance
        assertThat((IDocument) person.getDocument(document)).isNotNull();

        // Retrieve document by UUID
        assertThat((IDocument) person.getDocumentById(document.getId())).isNotNull();

        // Retrieve document by id
        assertThat((IDocument) person.getDocumentById(document.getId().toString())).isNotNull();

        // Retrieve document by name
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNotNull();
    }

    @Test
    @DisplayName("Check if a document exist")
    final void testExistDocument() throws DataModelEntityException, GeneratorException
    {
        IDocument document = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withDocument(document)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getDocumentCount()).isEqualTo(1);

        // Check by instance
        assertThat(person.existDocument(document)).isTrue();

        // Check by UUID
        assertThat(person.existDocumentById(document.getId())).isTrue();

        // Check by id
        assertThat(person.existDocumentById(document.getId().toString())).isTrue();

        // Check by name
        assertThat(person.existDocumentByName(document.getName())).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        for (int i = 0; i < count; i++)
        {
            person.addDocument(DocumentRandomizer.generate(true));
        }

        assertThat(person).isNotNull();
        assertThat(person.getDocumentCount()).isEqualTo(count);
        assertThat(person.getDocuments()).hasSize(count);
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 persons with with documents and no content in less than 3 seconds")
    final void testPerformanceCreateMultiplePersonsWithDocumentAndNoContent() throws DataModelEntityException, GeneratorException
    {
        final int COUNT = 1000;
        List<IPerson> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            list.add(PersonRandomizer.generate(true,false, false, false, true, false, AbstractDataModelEntityRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 1'000 persons with documents and content in less than 5 seconds")
    final void testPerformanceCreateMultiplePersonsWithDocumentContent() throws DataModelEntityException, GeneratorException
    {
        final int COUNT = 1000;
        List<IPerson> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            list.add(PersonRandomizer.generate(true,false, false, false, true, true, AbstractDataModelEntityRandomizer.getRandomInt(1, 3)));
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @DisplayName("Add an email address")
    final void testAddEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withEmailAddress(emailAddress)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(1);

        person.addEmailAddress(EmailAddressRandomizer.generate(true, false));
        assertThat(person.getEmailAddresses()).hasSize(2);
    }

    @Test
    @DisplayName("Delete an email address")
    final void testDeleteEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress3 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress4 = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addEmailAddress(emailAddress1);
        person.addEmailAddress(emailAddress2);
        person.addEmailAddress(emailAddress3);
        person.addEmailAddress(emailAddress4);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(4);

        // Delete email by UUID
        person.deleteEmailAddress(emailAddress1);
        assertThat(person.getEmailAddresses()).hasSize(3);
        assertThat(person.existEmailAddressById(emailAddress1.getId())).isFalse();

        // Delete email by id
        person.deleteEmailAddress(emailAddress2);
        assertThat(person.getEmailAddresses()).hasSize(2);
        assertThat(person.existEmailAddressById(emailAddress2.getId().toString())).isFalse();

        // Delete email by value
        person.deleteEmailAddress(emailAddress3);
        assertThat(person.getEmailAddresses()).hasSize(1);
        assertThat(person.existEmailAddressByName(emailAddress3.getName())).isFalse();

        // Delete email by instance
        person.deleteEmailAddress(emailAddress4);
        assertThat(person.getEmailAddresses()).isEmpty();
        assertThat(person.existEmailAddress(emailAddress4)).isFalse();
    }

    @Test
    @DisplayName("Delete all email addresses")
    final void testDeleteAllEmailAddress() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, true, false, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(count);

        person.deleteAllEmailAddress();
        assertThat(person.getEmailAddresses()).isEmpty();
    }

    @Test
    @DisplayName("Retrieve an email address")
    final void testRetrieveEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withEmailAddress(emailAddress)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddressCount()).isEqualTo(1);

        // Retrieve an email address by UUID
        assertThat(person.getEmailAddressById(emailAddress.getId())).isEqualTo(emailAddress);

        // Retrieve an email address by id
        assertThat(person.getEmailAddressById(emailAddress.getId().toString())).isEqualTo(emailAddress);

        // Retrieve an email address by email address
        assertThat(person.getEmailAddressByName(emailAddress.getName())).isEqualTo(emailAddress);

        // Retrieve an email address by instance
        assertThat(person.getEmailAddress(emailAddress)).isEqualTo(emailAddress);
    }

    @Test
    @DisplayName("Retrieve all email addresses")
    final void testRetrieveAllEmailAddress() throws DataModelEntityException, GeneratorException
    {
        int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, true, false, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(count);
    }

    @Test
    @DisplayName("Count the email addresses")
    final void testCountEmailAddress() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, true, false, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddressCount()).isEqualTo(count);
    }

    @Test
    @DisplayName("Check if an email address exist")
    final void testExistEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withEmailAddress(emailAddress1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(1);

        // Check by UUID
        assertThat(person.existEmailAddressById(emailAddress1.getId())).isTrue();
        assertThat(person.existEmailAddressById(emailAddress2.getId())).isFalse();

        // Check by id
        assertThat(person.existEmailAddressById(emailAddress1.getId().toString())).isTrue();
        assertThat(person.existEmailAddressById(emailAddress2.getId().toString())).isFalse();

        // Check by value
        assertThat(person.existEmailAddressByName(emailAddress1.getName())).isTrue();
        assertThat(person.existEmailAddressByName(emailAddress2.getName())).isFalse();

        // Check by instance
        assertThat(person.existEmailAddress(emailAddress1)).isTrue();
        assertThat(person.existEmailAddress(emailAddress2)).isFalse();
    }

    @Test
    @DisplayName("Cannot add duplicate email address")
    final void testCannotAddDuplicateEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress3 = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withEmailAddress(emailAddress1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(1);

        // This one is a duplicate by id!
        emailAddress2.setId(emailAddress1.getId());
        assertThat(person.addEmailAddress(emailAddress2)).isFalse();
        assertThat(person.getEmailAddresses()).hasSize(1);

        // This one is a duplicate by name!
        emailAddress3.setEmail(emailAddress1.getEmail());
        assertThat(person.addEmailAddress(emailAddress3)).isFalse();
        assertThat(person.getEmailAddresses()).hasSize(1);
    }

    @Test
    @DisplayName("Cannot add multiple default email address")
    final void testCannotAddMultipleDefaultEmailAddress() throws DataModelEntityException, GeneratorException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true, false);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();

        emailAddress1.setIsDefault(true);
        person.addEmailAddress(emailAddress1);
        assertThat(person.getEmailAddresses()).hasSize(1);

        emailAddress2.setIsDefault(true);
        assertThat(person.addEmailAddress(emailAddress2)).isFalse();
        assertThat(person.getEmailAddresses()).hasSize(1);
    }

    @Test
    @DisplayName("Count the postal addresses")
    final void testCountPostalAddress() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, true, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddressCount()).isEqualTo(count);
    }

    @Test
    @DisplayName("Add a postal address")
    final void testAddPostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress = PostalAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPostalAddress(postalAddress)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddresses()).hasSize(1);

        person.addPostalAddress(PostalAddressRandomizer.generate(true, false));
        assertThat(person.getPostalAddresses()).hasSize(2);
    }

    @Test
    @DisplayName("Cannot add duplicate postal address")
    final void testCannotAddDuplicatePostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress1 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress2 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress3 = PostalAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPostalAddress(postalAddress1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddressCount()).isEqualTo(1);

        // This one is a duplicate by id!
        postalAddress2.setId(postalAddress1.getId());
        assertThat(person.addPostalAddress(postalAddress2)).isFalse();
        assertThat(person.getPostalAddressCount()).isEqualTo(1);

        // This one is a duplicate by value!
        postalAddress3.setStreetName(postalAddress1.getStreetName());
        postalAddress3.setStreetNumber(postalAddress1.getStreetNumber());
        postalAddress3.setLocality(postalAddress1.getLocality());
        postalAddress3.setArea(postalAddress1.getArea());
        postalAddress3.setZipCode(postalAddress1.getZipCode());
        postalAddress3.setCountryCode(postalAddress1.getCountryCode());
        assertThat(person.addPostalAddress(postalAddress3)).isFalse();
        assertThat(person.getPostalAddressCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Cannot add multiple default postal addresses")
    final void testCannotAddMultipleDefaultPostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress1 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress2 = PostalAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();

        postalAddress1.setIsDefault(true);
        person.addPostalAddress(postalAddress1);
        assertThat(person.getPostalAddressCount()).isEqualTo(1);

        postalAddress2.setIsDefault(true);
        assertThat(person.addPostalAddress(postalAddress2)).isFalse();
        assertThat(person.getPostalAddressCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Delete a postal address")
    final void testDeletePostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress1 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress2 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress3 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress4 = PostalAddressRandomizer.generate(true, false);


        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addPostalAddress(postalAddress1);
        person.addPostalAddress(postalAddress2);
        person.addPostalAddress(postalAddress3);
        person.addPostalAddress(postalAddress4);

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddresses()).hasSize(4);

        // Delete by UUID
        person.deletePostalAddress(postalAddress1);
        assertThat(person.getPostalAddresses()).hasSize(3);
        assertThat(person.existPostalAddressById(postalAddress1.getId())).isFalse();

        // Delete by id
        person.deletePostalAddress(postalAddress2);
        assertThat(person.getPostalAddresses()).hasSize(2);
        assertThat(person.existPostalAddressById(postalAddress2.getId().toString())).isFalse();

        // Delete by value
        person.deletePostalAddress(postalAddress3);
        assertThat(person.getPostalAddresses()).hasSize(1);
        assertThat(person.existPostalAddressByName(postalAddress3.getName())).isFalse();

        // Delete by instance
        person.deletePostalAddress(postalAddress4);
        assertThat(person.getPostalAddresses()).isEmpty();
        assertThat(person.existPostalAddress(postalAddress4)).isFalse();
    }

    @Test
    @DisplayName("Delete all postal addresses")
    final void testDeleteAllPostalAddress() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, true, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddresses()).hasSize(count);

        person.deleteAllPostalAddress();
        assertThat(person.getPostalAddresses()).isEmpty();
    }

    @Test
    @DisplayName("Retrieve a postal address")
    final void testRetrievePostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress = PostalAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPostalAddress(postalAddress)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddressCount()).isEqualTo(1);

        // Retrieve by UUID
        assertThat(person.getPostalAddressById(postalAddress.getId())).isEqualTo(postalAddress);

        // Retrieve by id
        assertThat(person.getPostalAddressById(postalAddress.getId().toString())).isEqualTo(postalAddress);

        // Retrieve by email address
        assertThat(person.getPostalAddressByName(postalAddress.getName())).isEqualTo(postalAddress);

        // Retrieve by instance
        assertThat(person.getPostalAddress(postalAddress)).isEqualTo(postalAddress);
    }

    @Test
    @DisplayName("Retrieve all postal addresses")
    final void testRetrieveAllPostalAddress() throws DataModelEntityException, GeneratorException
    {
        int count = DocumentRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, true, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddresses()).hasSize(count);
    }

    @Test
    @DisplayName("Check if a postal address exist")
    final void testExistPostalAddress() throws DataModelEntityException, GeneratorException
    {
        IPostalAddress postalAddress1 = PostalAddressRandomizer.generate(true, false);
        IPostalAddress postalAddress2 = PostalAddressRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPostalAddress(postalAddress1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPostalAddresses()).hasSize(1);

        // Check by UUID
        assertThat(person.existPostalAddressById(postalAddress1.getId())).isTrue();
        assertThat(person.existPostalAddressById(postalAddress2.getId())).isFalse();

        // Check by id
        assertThat(person.existPostalAddressById(postalAddress1.getId().toString())).isTrue();
        assertThat(person.existPostalAddressById(postalAddress2.getId().toString())).isFalse();

        // Check by name
        assertThat(person.existPostalAddressByName(postalAddress1.getName())).isTrue();
        assertThat(person.existPostalAddressByName(postalAddress2.getName())).isFalse();

        // Check by instance
        assertThat(person.existPostalAddress(postalAddress1)).isTrue();
        assertThat(person.existPostalAddress(postalAddress2)).isFalse();
    }

    @Test
    @DisplayName("Count the phone numbers")
    final void testCountPhoneNumbers() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, false, true, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumberCount()).isEqualTo(count);
    }

    @Test
    @DisplayName("Add a phone number")
    final void testAddPhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber = PhoneNumberRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPhoneNumber(phoneNumber)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumbers()).hasSize(1);

        person.addPhoneNumber(PhoneNumberRandomizer.generate(true, false));
        assertThat(person.getPhoneNumbers()).hasSize(2);
    }

    @Test
    @DisplayName("Cannot add duplicate phone number")
    final void testCannotAddDuplicatePhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber1 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber2 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber3 = PhoneNumberRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPhoneNumber(phoneNumber1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);

        // This one is a duplicate by id!
        phoneNumber2.setId(phoneNumber1.getId());
        assertThat(person.addPhoneNumber(phoneNumber2)).isFalse();
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);

        // This one is a duplicate by value!
        phoneNumber3.setNumber(phoneNumber1.getNumber());
        phoneNumber3.setCountryCode(phoneNumber1.getCountryCode());
        assertThat(person.addPhoneNumber(phoneNumber3)).isFalse();
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Cannot add multiple default phone number")
    final void testCannotAddMultipleDefaultPhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber1 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber2 = PhoneNumberRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();

        phoneNumber1.setIsDefault(true);
        person.addPhoneNumber(phoneNumber1);
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);

        phoneNumber2.setIsDefault(true);
        assertThat(person.addPhoneNumber(phoneNumber2)).isFalse();
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Delete a phone number")
    final void testDeletePhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber1 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber2 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber3 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber4 = PhoneNumberRandomizer.generate(true, false);


        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addPhoneNumber(phoneNumber1);
        person.addPhoneNumber(phoneNumber2);
        person.addPhoneNumber(phoneNumber3);
        person.addPhoneNumber(phoneNumber4);

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumbers()).hasSize(4);

        // Delete by UUID
        person.deletePhoneNumber(phoneNumber1);
        assertThat(person.getPhoneNumbers()).hasSize(3);
        assertThat(person.existPhoneNumberById(phoneNumber1.getId())).isFalse();

        // Delete by id
        person.deletePhoneNumber(phoneNumber2);
        assertThat(person.getPhoneNumbers()).hasSize(2);
        assertThat(person.existPhoneNumberById(phoneNumber2.getId().toString())).isFalse();

        // Delete by value
        person.deletePhoneNumber(phoneNumber3);
        assertThat(person.getPhoneNumbers()).hasSize(1);
        assertThat(person.existPhoneNumberByName(phoneNumber3.getName())).isFalse();

        // Delete by instance
        person.deletePhoneNumber(phoneNumber4);
        assertThat(person.getPhoneNumbers()).isEmpty();
        assertThat(person.existPhoneNumber(phoneNumber4)).isFalse();
    }

    @Test
    @DisplayName("Delete all phone numbers")
    final void testDeleteAllPhoneNumbers() throws DataModelEntityException, GeneratorException
    {
        final int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, false, true, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumbers()).hasSize(count);

        person.deleteAllPhoneNumber();
        assertThat(person.getPhoneNumbers()).isEmpty();
    }

    @Test
    @DisplayName("Retrieve a phone number")
    final void testRetrievePhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber = PhoneNumberRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPhoneNumber(phoneNumber)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumberCount()).isEqualTo(1);

        // Retrieve by UUID
        assertThat(person.getPhoneNumberById(phoneNumber.getId())).isEqualTo(phoneNumber);

        // Retrieve by id
        assertThat(person.getPhoneNumberById(phoneNumber.getId().toString())).isEqualTo(phoneNumber);

        // Retrieve by name
        assertThat(person.getPhoneNumberByName(phoneNumber.getName())).isEqualTo(phoneNumber);

        // Retrieve by instance
        assertThat(person.getPhoneNumber(phoneNumber)).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("Retrieve all phone numbers")
    final void testRetrieveAllPhoneNumber() throws DataModelEntityException, GeneratorException
    {
        int count = AbstractDataModelEntityRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, false, false, true, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumbers()).hasSize(count);
    }

    @Test
    @DisplayName("Check if a pphone number exist")
    final void testExistPhoneNumber() throws DataModelEntityException, GeneratorException
    {
        IPhoneNumber phoneNumber1 = PhoneNumberRandomizer.generate(true, false);
        IPhoneNumber phoneNumber2 = PhoneNumberRandomizer.generate(true, false);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withPhoneNumber(phoneNumber1)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPhoneNumbers()).hasSize(1);

        // Check by UUID
        assertThat(person.existPhoneNumberById(phoneNumber1.getId())).isTrue();
        assertThat(person.existPhoneNumberById(phoneNumber2.getId())).isFalse();

        // Check by id
        assertThat(person.existPhoneNumberById(phoneNumber1.getId().toString())).isTrue();
        assertThat(person.existPhoneNumberById(phoneNumber2.getId().toString())).isFalse();

        // Check by name
        assertThat(person.existPhoneNumberByName(phoneNumber1.getName())).isTrue();
        assertThat(person.existPhoneNumberByName(phoneNumber2.getName())).isFalse();

        // Check by instance
        assertThat(person.existPhoneNumber(phoneNumber1)).isTrue();
        assertThat(person.existPhoneNumber(phoneNumber2)).isFalse();
    }
}
