/*
 * Copyright(c) 2020 by Resse Christophe.
 * --------------------------------------------------------------------------------------
 * This file is part of Resse Christophe public projects which is licensed
 * under the Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 *
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * --------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.base.i18n.localization.exception;

import com.hemajoo.commerce.cherry.base.commons.exception.CherryException;

import java.io.Serial;

/**
 * Exception thrown to indicate an error occurred with a <b>language</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class LanguageException extends CherryException
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with a language.
     *
     * @param exception Parent {@link Exception}.
     */
    public LanguageException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with a language.
     *
     * @param message Message describing the error being the cause of the raised exception.
     */
    public LanguageException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred with a language.
     *
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent {@link Exception}.
     */
    public LanguageException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
