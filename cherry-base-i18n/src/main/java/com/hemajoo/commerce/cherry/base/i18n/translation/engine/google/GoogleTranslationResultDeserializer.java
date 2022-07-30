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
package com.hemajoo.commerce.cherry.base.i18n.translation.engine.google;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.ITranslationResult;

import java.lang.reflect.Type;

/**
 * A <b>Google</b> translation result deserializer used when JSON encounters a {@link ITranslationResult} interface. It then
 * indicates which concrete implementation to use for processing the de-serialization.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class GoogleTranslationResultDeserializer implements JsonDeserializer<ITranslationResult>
{
    @Override
    public ITranslationResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        return context.deserialize(json, GoogleTranslationResult.class);
    }
}
