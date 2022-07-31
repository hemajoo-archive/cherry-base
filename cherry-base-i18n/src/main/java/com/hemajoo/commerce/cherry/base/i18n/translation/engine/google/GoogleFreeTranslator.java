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

import com.hemajoo.commerce.cherry.base.i18n.translation.Translation;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.ITranslator;
import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.NonNull;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * A <b>Google</b> free translator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class GoogleFreeTranslator implements ITranslator
{
    private static final String GOOGLE_TRANSLATE_API = "https://translate.googleapis.com/translate_a/t?client=dict-chrome-ex&sl=";

//    private ITranslationResult translationResult;
//
//    private List<ITranslationResultSentence> sentences = new ArrayList<>();

    /**
     * Http client.
     */
    private CloseableHttpClient httpClient = null;

    /**
     * Gson builder.
     */
//    private final Gson gsonBuilder;

    /**
     * Creates a new Google translation processor.
     */
    public GoogleFreeTranslator()
    {
//        gsonBuilder = new GsonFireBuilder()
//                .createGsonBuilder()
//                .setDateFormat("yyyy-MM-dd")
//                .setPrettyPrinting()
//                .registerTypeAdapter(ITranslationResult.class, new GoogleTranslationResultDeserializer())
//                .registerTypeAdapter(ITranslationResultSentence.class, new GoogleTranslationResultSentenceDeserializer())
//                .enableComplexMapKeySerialization()
//                .create();

        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public String translate(final @NonNull Translation text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
    {
        String url;

        try
        {
            url = buildUrl(text.getText(),source.getLanguage(), target.getLanguage());
            HttpGet http = new HttpGet(url);
            http.setHeader( "Accept", "application/json" );
            HttpResponse response = httpClient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
//                return deserializeResponse(getResponseString(response));
                return getResponseString(response);
            }
            else
            {
                throw new TranslationException(response.getStatusLine().getReasonPhrase());
            }
        }
        catch (Exception e)
        {
            throw new TranslationException(e);
        }
   }

    /**
     * Builds the URL to be used for the translation.
     * @param text Text to be translated.
     * @param sourceLanguage Source language.
     * @param targetLanguage Target language.
     * @return Translation URL.
     */
    private String buildUrl(String text, String sourceLanguage, String targetLanguage)
    {
        String textEncoded = URLEncoder.encode(text, StandardCharsets.UTF_8);
        return GOOGLE_TRANSLATE_API + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&q=" + textEncoded;
    }

    /**
     * Extracts the response string from the received HTTP response.
     * @param response HTTP response.
     * @return Response string.
     * @throws IOException Thrown to indicate an error occurred while trying to extracts the response string.
     */
    private String getResponseString(HttpResponse response) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();

        return responseString.replace("[\"", "").replace("\"]", "");
    }

//    /**
//     * Deserializes the response.
//     * @param response Response.
//     * @return De-serialized {@link ITranslationResult} representing the translation result.
//     */
//    private ITranslationResult deserializeResponse(String response)
//    {
//        return GsonHelper.deserialize(gsonBuilder, response, new TypeToken<ITranslationResult>(){}.getType());
//    }

    /**
     * Close the http connection.
     * @throws IOException Thrown to indicate an error occurred when trying to close the http connection.
     */
    public void destroy() throws IOException
    {
        if (httpClient != null)
        {
            httpClient.close();
        }
    }
}
