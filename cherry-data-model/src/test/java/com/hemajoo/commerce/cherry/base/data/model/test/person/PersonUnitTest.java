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
import com.hemajoo.commerce.cherry.base.data.model.test.base.AbstractDataModelEntityUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests the <b>person</b> data model entity class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@SpringBootTest
class PersonUnitTest extends AbstractDataModelEntityUnitTest
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
    final void testCreatePersonWithName() throws PersonException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Creating a person without a last name should raise a ConstraintViolationException")
    final void testCannotCreatePersonWithoutLastName()
    {
        final String FIRST_NAME = FAKER.name().firstName();

        assertThrows(ConstraintViolationException.class, () ->
                Person.builder()
                        .withPersonType(PersonType.PHYSICAL)
                        .withFirstName(FIRST_NAME)
                        .withGenderType(GenderType.MALE)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Creating a person without a first name should raise a ConstraintViolationException")
    final void testCannotCreatePersonWithoutFirstName()
    {
        final String LAST_NAME = FAKER.name().lastName();

        assertThrows(ConstraintViolationException.class, () ->
                Person.builder()
                        .withPersonType(PersonType.PHYSICAL)
                        .withLastName(LAST_NAME)
                        .withGenderType(GenderType.MALE)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Creating a person without a person type should raise a ConstraintViolationException")
    final void testCannotCreatePersonWithoutPersonType()
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        assertThrows(ConstraintViolationException.class, () ->
                Person.builder()
                        .withLastName(LAST_NAME)
                        .withFirstName(FIRST_NAME)
                        .withGenderType(GenderType.MALE)
                        .build());
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Creating a person without a gender type when person type is physical should raise a ConstraintViolationException")
    final void testCannotCreatePersonWithoutGenderTypeForPhysicalPerson()
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        assertThrows(ConstraintViolationException.class, () ->
                Person.builder()
                        .withLastName(LAST_NAME)
                        .withFirstName(FIRST_NAME)
                        .withPersonType(PersonType.PHYSICAL)
                        .build());
    }

    @Test
    @DisplayName("Create a person with all attributes")
    final void testCreatePersonWithAllAttributes() throws PersonException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();
        final Date BIRTH_DATE = FAKER.date().birthday();
        final String DESCRIPTION = "An unknown person";
        final String REFERENCE = "UJHH-4589663";
        final String TAG1 = "USA";
        final String TAG2 = "Texas";
        final String TAG3 = "Austin";

        IPerson owner = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withLastName("Einstein")
                .withFirstName("Albert")
                .withGenderType(GenderType.MALE)
                .build();

        IPerson person = Person.builder()
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .withDescription(DESCRIPTION)
                .withBirthDate(BIRTH_DATE)
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withTags(new String[]{ TAG1, TAG2, TAG3 })
                .withReference(REFERENCE)
                .withOwner(owner)
                .build();

        assertThat(person).isNotNull();
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getGenderType()).isEqualTo(GenderType.MALE);
        assertThat(person.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(person.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(person.getReference()).isEqualTo(REFERENCE);
        assertThat(person.getTags()).containsOnlyOnce(TAG1);
        assertThat(person.getTags()).containsOnlyOnce(TAG2);
        assertThat(person.getTags()).containsOnlyOnce(TAG3);
        assertThat((IDataModelEntity) person.getParent()).isNotNull();
        assertThat(((IPerson) person.getParent()).getLastName()).isEqualTo("Einstein");
    }

    @Test
    @DisplayName("Add a document")
    final void testAddDocument() throws DataModelEntityException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        person.addDocument(DocumentRandomizer.generate(true));

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(person.getDocumentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Remove a document")
    final void testRemoveDocument() throws DataModelEntityException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        IDocument document = DocumentRandomizer.generate(true);
        person.addDocument(document);

        assertThat(person.getDocumentCount()).isPositive();

        person.removeDocument(document);

        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(person.getDocumentCount()).isZero();
    }

    @Test
    @DisplayName("Get a document by its name")
    final void testGetDocumentByName() throws DataModelEntityException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        IDocument document = DocumentRandomizer.generate(true);
        person.addDocument(document);

        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);

        IDocument other = person.getDocumentByName(document.getName());
        assertThat(other).isNotNull();
        assertThat(other.getName()).isEqualTo(document.getName());
    }

    @Test
    @DisplayName("Get a document by its identifier")
    final void testGetDocumentById() throws DataModelEntityException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        IDocument document = DocumentRandomizer.generate(true);
        person.addDocument(document);

        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);

        IDocument other = person.getDocumentById(document.getId());
        assertThat(other).isNotNull();
        assertThat(other.getId().toString()).isEqualTo(document.getId().toString());
    }

    @Test
    @DisplayName("Check if a document exist")
    final void testExistDocument() throws DataModelEntityException
    {
        final String LAST_NAME = FAKER.name().lastName();
        final String FIRST_NAME = FAKER.name().firstName();

        IPerson person = Person.builder()
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withLastName(LAST_NAME)
                .withFirstName(FIRST_NAME)
                .build();

        IDocument document = DocumentRandomizer.generate(true);
        person.addDocument(document);

        assertThat(person.getDocumentCount()).isEqualTo(1);
        assertThat(person).isNotNull();
        assertThat(person.getPersonType()).isEqualTo(PersonType.PHYSICAL);
        assertThat(person.getLastName()).isEqualTo(LAST_NAME);
        assertThat(person.getFirstName()).isEqualTo(FIRST_NAME);

        assertThat(person.existDocument(document)).isTrue();
    }
}
