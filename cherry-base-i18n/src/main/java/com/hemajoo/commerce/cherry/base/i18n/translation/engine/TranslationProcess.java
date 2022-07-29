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

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * Concrete implementation of a translation process.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationProcess implements ITranslationProcess
{
    /**
     * Translation request.
     */
    @Getter
    private ITranslationRequest request;

    /**
     * Translation result.
     */
    @Getter
    private ITranslationResult result;

    /**
     * Translation processor.
     */
    @Getter
    private ITranslationProcessor processor;

    /**
     * Does this translation process has been translated?
     */
    @Getter
    private boolean translated = false;

    /**
     * Does this translation process requires a translation by a translation processor?
     */
    private boolean requireProcessing = false;

    /**
     * Document content.
     */
    private final StringBuilder document = new StringBuilder();

    public TranslationProcess(final @NonNull ITranslationProcessor processor)
    {
        this.processor = processor;
    }

    @Override
    public final boolean requireProcessing()
    {
        return requireProcessing;
    }

    @Override
    public final void hasBeenTranslated()
    {
        translated = true;
    }

    @Override
    public void setRequest(final @NonNull ITranslationRequest request)
    {
        this.request = request;

        generateRequestEntries();
        computeRequireProcessing();
    }

    @Override
    public final void setResult(@NonNull ITranslationResult result)
    {
        this.result = result;
        translated = true;
    }

    @Override
    public final String getDocument()
    {
        generateDocument();

        return document.toString();
    }

    /**
     * Generates the translation request entries based on the source and target properties.
     */
    private void generateRequestEntries()
    {
        TranslationRequestEntry requestEntry;
        String targetValue;

        for (Map.Entry<String, String> entry : request.getSources().entrySet())
        {
            targetValue = request.getTarget(entry.getKey());
            if (targetValue == null || targetValue.isBlank())
            {
                requestEntry = new TranslationRequestEntry(entry.getKey(), entry.getValue(), true);
                request.getEntries().add(requestEntry);
            }
            else
            {
                requestEntry = new TranslationRequestEntry(entry.getKey(), entry.getValue(), false);
                requestEntry.setTranslation(targetValue);
                request.getEntries().add(requestEntry);
            }
        }
    }

    /**
     * Computes if a translation processing is required or not.
     */
    private void computeRequireProcessing()
    {
        // Do we have at least one entry that require translation?
        Optional<TranslationRequestEntry> element = getRequest().getEntries().stream()
                .filter(TranslationRequestEntry::requireTranslation)
                .findAny();

        requireProcessing = element.isPresent();
    }

    /**
     * Generates the document.
     */
    private void generateDocument()
    {
        for (ITranslationRequestEntry entry : request.getEntries())
        {
            document.append(entry.getKey()).append("=").append(entry.getTranslation()).append("\n");
        }
    }

    // Not used for the moment, not accurate enough!
//    private void prepareDocumentForCompactMode() throws TranslationException
//    {
//        ITranslationRequestEntry entry;
//
//        for (ITranslationResultSentence sentence : getResult().getSentences())
//        {
//            if (sentence.getOriginal() != null)
//            {
//                String[] originals = sentence.getOriginal().replace("[", "").replace("]", "").split(",");
//                String[] translations = sentence.getTranslation().replace("[", "").replace("]", "").split(",");
//                if (originals.length != translations.length)
//                {
//                    // Separator characters translated for other alphabets
//                    translations = sentence.getTranslation().replace("[", "").replace("]", "").split("、");
//                }
//                for (int i = 0; i < originals.length; i++)
//                {
//                    // Find the key of the value in the source
//                    entry = getRequest().findEntryKeyFor(originals[i]);
//                    entry.setTranslation(translations[i].trim());
//                }
//            }
//        }
//
//        generateDocument();
//    }
}
