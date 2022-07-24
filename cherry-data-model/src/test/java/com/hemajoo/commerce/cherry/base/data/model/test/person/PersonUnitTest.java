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
import com.hemajoo.commerce.cherry.base.data.model.person.*;
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
    @DisplayName("Create a person with mandatory fields")
    final void testCreatePersonWithName() throws DataModelEntityException
    {
        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a person without a last name")
    final void testCannotCreatePersonWithoutLastName()
    {
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withFirstName(personFirstName)
                        .withPersonType(personType)
                        .withGenderType(genderType)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a person without a first name")
    final void testCannotCreatePersonWithoutFirstName()
    {
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withPersonType(personType)
                        .withLastName(personLastName)
                        .withGenderType(genderType)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a person without a person type")
    final void testCannotCreatePersonWithoutPersonType()
    {
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withLastName(personLastName)
                        .withFirstName(personFirstName)
                        .withGenderType(genderType)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Cannot create a person without a gender type when person type is 'PHYSICAL'")
    final void testCannotCreatePersonWithoutGenderTypeForPhysicalPerson()
    {
        assertThrows(PersonException.class, () ->
                Person.builder()
                        .withLastName(personLastName)
                        .withFirstName(personFirstName)
                        .withPersonType(PersonType.PHYSICAL)
                        .build());
    }

    @Test
    @DisplayName("Create a person with a maximum of attributes")
    final void testCreatePersonWithMaxAttributes() throws DataModelEntityException
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
        assertThat((IDataModelEntity) person.getParent()).isNotNull();
        assertThat(((IPerson) person.getParent()).getLastName()).isEqualTo(parent.getLastName());
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addDocument(document);

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNotNull();
    }

    @Test
    @DisplayName("Remove a document")
    final void testRemoveDocument() throws DataModelEntityException
    {
        IDocument document = DocumentRandomizer.generate(true, true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .build();

        person.addDocument(document);

        assertThat(person.getDocumentCount()).isPositive();

        person.removeDocument(document);

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);
        assertThat(person.getDocumentCount()).isZero();
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNull();
    }

    @Test
    @DisplayName("Get a document (by: UUID, id, name)")
    final void testGetDocument() throws DataModelEntityException
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
        assertThat(person.getPersonType()).isEqualTo(personType);
        assertThat(person.getLastName()).isEqualTo(personLastName);
        assertThat(person.getFirstName()).isEqualTo(personFirstName);

        assertThat((IDocument) person.getDocumentById(document.getId())).isNotNull();
        assertThat((IDocument) person.getDocumentById(document.getId().toString())).isNotNull();
        assertThat((IDocument) person.getDocumentByName(document.getName())).isNotNull();
    }

    @Test
    @DisplayName("Check if a document exist (by: UUID, id, name)")
    final void testExistDocument() throws DataModelEntityException
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

        assertThat(person.existDocument(document)).isTrue();
        assertThat(person.existDocumentById(document.getId())).isTrue();
        assertThat(person.existDocumentById(document.getId().toString())).isTrue();
        assertThat(person.existDocumentByName(document.getName())).isTrue();
    }

    @Test
    @DisplayName("Retrieve all documents")
    final void testRetrieveAllDocuments() throws DataModelEntityException
    {
        final int count = PersonRandomizer.getRandomInt(1, 10);

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
    @DisplayName("Create 100 persons with light dependencies in less than 3 seconds")
    final void testPerformanceCreateMultiplePersonsWithoutDocumentContent() throws DataModelEntityException
    {
        final int COUNT = 100;
        List<IPerson> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            // 1 <= X <= 5
            // For each person entity created, we also create:
            // - X documents
            // - X email addresses  without any document
            // - X postal addresses without any document
            // - X phone numbers    without any document
            list.add(PersonRandomizer.generate(true,true, true, true, false, false, DocumentRandomizer.getRandomInt(1, 5)));
        }

        assertThat(list).hasSize(COUNT);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Create 100 persons with full dependencies in less than 10 seconds")
    final void testPerformanceCreateMultiplePersonsWithDocumentContent() throws DataModelEntityException
    {
        final int COUNT = 100;
        List<IPerson> list = new ArrayList<>();

        for (int i = 0; i < COUNT; i++)
        {
            // 1 <= X <= 5
            // For each person entity created, we also create:
            // - X documents
            // - X email addresses  with X documents for each
            // - X postal addresses with X documents for each
            // - X phone numbers    with X documents for each
            list.add(PersonRandomizer.generate(true,true, true, true, true, true, DocumentRandomizer.getRandomInt(1, 5)));
        }

        assertThat(list).hasSize(COUNT);
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Add an email address")
    final void testAddEmailAddress() throws DataModelEntityException
    {
        IEmailAddress emailAddress = EmailAddressRandomizer.generate(true);

        IPerson person = Person.builder()
                .withPersonType(personType)
                .withGenderType(genderType)
                .withLastName(personLastName)
                .withFirstName(personFirstName)
                .withEmailAddress(emailAddress)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(1);
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Delete an email address")
    final void testDeleteEmailAddress() throws DataModelEntityException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress3 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress4 = EmailAddressRandomizer.generate(true);

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

        int count = person.getEmailAddresses().size();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(count);

        // Delete email by UUID
        person.deleteEmailAddress(emailAddress1);
        assertThat(person.getEmailAddresses()).hasSize(count - 1);
        assertThat(person.existEmailAddressById(emailAddress1.getId())).isFalse();

        // Delete email by id
        person.deleteEmailAddress(emailAddress2);
        assertThat(person.getEmailAddresses()).hasSize(count - 2);
        assertThat(person.existEmailAddressById(emailAddress2.getId().toString())).isFalse();

        // Delete email by value
        person.deleteEmailAddress(emailAddress3);
        assertThat(person.getEmailAddresses()).hasSize(count - 3);
        assertThat(person.existEmailAddressByValue(emailAddress3.getEmail())).isFalse();

        // Delete email by instance
        person.deleteEmailAddress(emailAddress4);
        assertThat(person.getEmailAddresses()).hasSize(count - 4);
        assertThat(person.existEmailAddress(emailAddress4)).isFalse();
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Delete all email addresses")
    final void testDeleteAllEmailAddress() throws DataModelEntityException
    {
        final int count = PersonRandomizer.getRandomInt(1, 10);;

        IPerson person = PersonRandomizer.generate(true, true, false, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(count);

        person.deleteAllEmailAddress();
        assertThat(person.getEmailAddresses()).isEmpty();
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Retrieve an email address")
    final void testRetrieveEmailAddress() throws DataModelEntityException
    {
        IEmailAddress emailAddress = EmailAddressRandomizer.generate(true);

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
        assertThat(person.getEmailAddressByValue(emailAddress.getEmail())).isEqualTo(emailAddress);

        // Retrieve an email address by instance
        assertThat(person.getEmailAddress(emailAddress)).isEqualTo(emailAddress);
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Retrieve all email addresses")
    final void testRetrieveAllEmailAddress() throws DataModelEntityException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress3 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress4 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress5 = EmailAddressRandomizer.generate(true);

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
        person.addEmailAddress(emailAddress5);

        int count = person.getEmailAddressCount();

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddresses()).hasSize(5);
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Count the email addresses")
    final void testCountEmailAddress() throws DataModelEntityException
    {
        final int count = PersonRandomizer.getRandomInt(1, 10);

        IPerson person = PersonRandomizer.generate(true, true, false, false, false, false, count);

        assertThat(person).isNotNull();
        assertThat(person.getEmailAddressCount()).isEqualTo(count);
    }

    //@Disabled(TEST_NOT_YET_IMPLEMENTED)
    @Test
    @DisplayName("Check if an email address exist")
    final void testExistEmailAddress() throws DataModelEntityException
    {
        IEmailAddress emailAddress1 = EmailAddressRandomizer.generate(true);
        IEmailAddress emailAddress2 = EmailAddressRandomizer.generate(true);

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
        assertThat(person.existEmailAddressByValue(emailAddress1.getEmail())).isTrue();
        assertThat(person.existEmailAddressByValue(emailAddress2.getEmail())).isFalse();

        // Check by instance
        assertThat(person.existEmailAddress(emailAddress1)).isTrue();
        assertThat(person.existEmailAddress(emailAddress2)).isFalse();
    }
}
