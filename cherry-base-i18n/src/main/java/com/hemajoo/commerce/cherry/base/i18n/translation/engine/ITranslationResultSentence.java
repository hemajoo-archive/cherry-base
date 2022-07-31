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
package com.hemajoo.commerce.cherry.base.i18n.translation.engine;

import java.io.Serializable;

/**
 * Interface defining the behavior of a translation result sentence.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * see {@link ITranslationRequest}
 */
public interface ITranslationResultSentence extends Serializable
{
    /**
     * Return the original text of the translation result sentence.
     * @return Original text.
     */
    String getOriginal();

    /**
     * Return the translated text of the translation result sentence.
     * @return Translated text.
     */
    String getTranslation();
}
