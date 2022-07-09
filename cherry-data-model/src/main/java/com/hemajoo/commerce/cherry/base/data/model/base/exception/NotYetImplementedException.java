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
package com.hemajoo.commerce.cherry.base.data.model.base.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate a service is not yet implemented.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class NotYetImplementedException extends RuntimeException
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate a service is not yet implemented.
     */
    public NotYetImplementedException()
    {
        super();
    }

    /**
     * Thrown to indicate a service is not yet implemented.
     * @param exception Parent exception.
     */
    public NotYetImplementedException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate a service is not yet implemented.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public NotYetImplementedException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate a service is not yet implemented.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public NotYetImplementedException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
