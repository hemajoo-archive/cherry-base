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

import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;

import java.io.Serializable;
import java.util.List;

/**
 * Interface defining the behavior of a translation result.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * see {@link ITranslationRequest}
 * see {@link ITranslationRequestEntry}
 */
public interface ITranslationResult extends Serializable
{
    /**
     * Enumeration providing the available values for a <b>translation provider</b> type.
     * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
     * @since 0.3.0
     * @version 1.0.0
     * @see I18n
     */
    enum TranslationProviderType
    {
        /**
         * Unknown translation provider.
         */
        UNKNOWN,

        /**
         * <b>Microsoft Azure</b>> translation provider.
         */
        AZURE_TRANSLATE_API,

        /**
         * <b>IBM</b>> translation provider.
         */
        IBM_TRANSLATE_API,

        /**
         * <b>Google</b>> free translation provider.
         */
        GOOGLE_FREE_TRANSLATE_API,

        /**
         * <b>Google</b>> translation provider.
         */
        GOOGLE_TRANSLATE_API;
    }

    /**
     * Return the result sentences of a translation.
     * @return List of translation sentences.
     */
    List<ITranslationResultSentence> getSentences();

    /**
     * Return the translation provider type.
     * @return Translation provider type.
     */
    TranslationProviderType getProviderType();
}
