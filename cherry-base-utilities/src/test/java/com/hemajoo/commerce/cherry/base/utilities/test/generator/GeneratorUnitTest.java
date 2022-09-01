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
package com.hemajoo.commerce.cherry.base.utilities.test.generator;

import com.hemajoo.commerce.cherry.base.utilities.generator.RandomNumberGenerator;
import com.hemajoo.commerce.cherry.base.utilities.test.base.BaseUnitTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test the {@link RandomNumberGenerator} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
class GeneratorUnitTest extends BaseUnitTest
{
    @Test
    @DisplayName("Generate random float values with invalid bounds")
    final void testGenerateRandomFloatWithInvalidBounds()
    {
        assertThrows(IllegalArgumentException.class, () ->
                RandomNumberGenerator.nextFloat(1000f, 800f));
    }
}
