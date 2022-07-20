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
package com.hemajoo.commerce.cherry.base.commons.test;

import com.github.javafaker.Faker;

/**
 * Abstract implementation of a <b>Cherry</b> unit test.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractCherryUnitTest
{
    /**
     * Test is not yet implemented.
     */
    protected static final String TEST_NOT_YET_IMPLEMENTED = "Test not yet implemented";

    /**
     * Java data faker.
     */
    protected static final Faker FAKER = new Faker();

    /**
     * Create a new abstract <b>Cherry</b> unit test.
     */
    protected AbstractCherryUnitTest()
    {
        // Empty!
    }
}
