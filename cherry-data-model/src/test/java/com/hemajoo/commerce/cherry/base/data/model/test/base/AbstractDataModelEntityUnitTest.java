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
package com.hemajoo.commerce.cherry.base.data.model.test.base;

import com.hemajoo.commerce.cherry.base.commons.test.AbstractCherryUnitTest;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import org.junit.jupiter.api.BeforeEach;

import java.util.Set;

/**
 * Abstract implementation of a unit test for a data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractDataModelEntityUnitTest extends AbstractCherryUnitTest
{
    /**
     * Name.
     */
    protected String name;

    /**
     * Description.
     */
    protected String description;

    /**
     * Reference.
     */
    protected String reference;

    /**
     * Tags.
     */
    protected Set<String> tags;

    /**
     * Filename.
     */
    protected String filename;

    /**
     * Document status type.
     */
    protected EntityStatusType statusType;

    @BeforeEach
    protected void beforeEach() throws DataModelEntityException
    {
        name = AbstractDataModelEntityRandomizer.getRandomName();
        description = AbstractDataModelEntityRandomizer.getRandomName();
        reference = AbstractDataModelEntityRandomizer.getRandomName();
        tags = AbstractDataModelEntityRandomizer.getRandomTagList();
        statusType = AbstractDataModelEntityRandomizer.getRandomStatusType();
    }
}
