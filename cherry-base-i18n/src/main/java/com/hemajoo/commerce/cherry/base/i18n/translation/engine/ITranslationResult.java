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
import java.util.List;

/**
 * Provides the basic behavior of a generic translation result.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * see {@link ITranslationRequest}
 * see {@link ITranslationRequestEntry}
 */
public interface ITranslationResult extends Serializable
{
    enum TranslationProviderType
    {
        UNKNOWN,
        AZURE_TRANSLATE_API,
        IBM_TRANSLATE_API,
        GOOGLE_FREE_TRANSLATE_API,
        GOOGLE_TRANSLATE_API;
    }

    /**
     * Returns the result sentences of a translation.
     * @return List of translation sentences.
     */
    List<ITranslationResultSentence> getSentences();

    /**
     * Returns the translation provider type.
     * @return Translation provider type.
     */
    TranslationProviderType getProviderType();
}
