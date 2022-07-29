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
package com.hemajoo.commerce.cherry.base.i18n.localization.exception;

import com.hemajoo.commerce.cherry.base.commons.exception.CherryException;

/**
 * Exception thrown to indicate an error occurred when trying to translate a resource.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationException extends CherryException
{
    /**
     * Thrown to indicate that an error occurred when trying to translate a resource.
     *
     * @param exception Parent exception.
     */
    public TranslationException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred when trying to translate a resource.
     *
     * @param message Message describing the error being the cause of the raised exception.
     */
    public TranslationException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred when trying to translate a resource.
     *
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public TranslationException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}

