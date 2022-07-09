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
import java.io.Serializable;

/**
 * High level exception thrown to indicate an error occurred with the <b>Cherry</b> solution.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class CherryException extends Exception implements Serializable
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with the <b>Cherry</b> solution.
     * @param exception Parent exception.
     */
    public CherryException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with the <b>Cherry</b> solution.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public CherryException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred with the <b>Cherry</b> solution.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public CherryException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
