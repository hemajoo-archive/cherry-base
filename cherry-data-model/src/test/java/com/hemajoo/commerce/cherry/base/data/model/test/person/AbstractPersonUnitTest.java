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

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.person.*;
import com.hemajoo.commerce.cherry.base.data.model.test.document.AbstractDocumentUnitTest;

import java.util.Date;

/**
 * Abstract implementation of a <b>person</b> data model entity unit test.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractPersonUnitTest extends AbstractDocumentUnitTest
{
    /**
     * Number of dependencies to generate when generating a random person.
     */
    protected static final int COUNT_PERSON_DEPENDENCY = 5;

    /**
     * Test person first name.
     */
    protected final String testPersonFirstName = FAKER.name().firstName();

    /**
     * Test person last name.
     */
    protected final String testPersonLastName = FAKER.name().lastName();

    /**
     * Test person birthdate.
     */
    protected final Date testPersonBirthDate = FAKER.date().birthday();

    /**
     * Test person tag #1.
     */
    protected final String testPersonTag1 = FAKER.animal().name();

    /**
     * Test person tag #2.
     */
    protected final String testPersonTag2 = FAKER.artist().name();

    /**
     * Test person tag #3.
     */
    protected final String testPersonTag3 = FAKER.book().genre();

    /**
     * Test person description.
     */
    protected final String testPersonDescription = FAKER.book().title();

    /**
     * Test person reference.
     */
    protected final String testPersonReference = FAKER.aviation().METAR();

    /**
     * Create a test person.
     * @return Person.
     * @throws DataModelEntityException Thrown in case an error occurred while creating a new person.
     */
    protected IPerson createTestPerson() throws DataModelEntityException
    {
        return Person.builder()
                .withLastName(testPersonLastName)
                .withFirstName(testPersonFirstName)
                .withDescription(testPersonDescription)
                .withBirthDate(testPersonBirthDate)
                .withPersonType(PersonType.PHYSICAL)
                .withGenderType(GenderType.MALE)
                .withTags(new String[]{testPersonTag1, testPersonTag2, testPersonTag3})
                .withReference(testPersonReference)
                .withOwner(createTestDocument())
                .build();
    }

    /**
     * Return a new random person.
     * @return Person.
     * @throws DataModelEntityException Thrown in case an error occurred while generating a new random person.
     */
    protected IPerson getRandomPerson() throws DataModelEntityException
    {
        return PersonRandomizer.generate(true, true, true, true, true, true, COUNT_PERSON_DEPENDENCY);
    }
}
