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

import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

/**
 * Concrete implementation of a translation request.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationRequest implements ITranslationRequest
{
    @Getter
    private final List<TranslationRequestEntry> entries = new ArrayList<>();

    /**
     * Source property file entries.
     */
    @Getter
    private final Map<String, String> sources = new HashMap<>();

    /**
     * Target property file entries.
     */
    @Getter
    private final Map<String, String> targets = new HashMap<>();

    /**
     * Compact mode.
     */
    @Getter
    private boolean compactMode = false;

    /**
     * Source language.
     */
    @Getter
    @Setter
    private Locale sourceLocale;

    /**
     * Target language.
     */
    @Getter
    @Setter
    private Locale targetLocale;

    @Override
    public final String getQuery()
    {
        compactMode = true;

        StringBuilder query = new StringBuilder("[");

        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.requireTranslation())
            {
                query.append(entry.getSource()).append(",");
            }
        }

        // Remove the last "," character.
        query.replace(query.length() -1, query.length(), "");
        query.append("]");

        return query.toString();
    }

    @Override
    public final String getQuery(final @NonNull String key) throws TranslationException
    {
        compactMode = false;

        ITranslationRequestEntry entry = getEntry(key);

        if (!entry.requireTranslation())
        {
            throw new TranslationException(String.format("Entry: '%s' is not supposed to be translated!", key));
        }

        return entry.getSource();
    }

    @Override
    public final void setSourceProperties(final @NonNull String content)
    {
        String[] entryParts;

        String[] parts = content.split("\n");
        if (parts.length > 1)
        {
            for (String s : parts)
            {
                entryParts = s.split("=");
                if (entryParts.length > 1)
                {
                    sources.put(entryParts[0], entryParts[1]);
                }
            }
        }
    }

    @Override
    public final int getCount()
    {
        return (int) entries.stream()
                .filter(ITranslationRequestEntry::requireTranslation)
                .count();
    }

    @Override
    public final void setTargetProperties(final @NonNull String content)
    {
        String[] entryParts;

        String[] parts = content.split("\n");
        if (parts.length > 1)
        {
            for (String s : parts)
            {
                entryParts = s.split("=");
                if (entryParts.length > 1)
                {
                    targets.put(entryParts[0], entryParts[1]);
                }
            }
        }
    }

    @Override
    public final ITranslationRequestEntry findEntryKeyFor(@NonNull String value) throws TranslationException
    {
        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.getSource().equals(value))
            {
                return entry;
            }
        }

        throw new TranslationException(String.format("No translation request entry found for source value: '%s'", value));
    }

    @Override
    public final ITranslationRequestEntry getEntry(final @NonNull String key) throws TranslationException
    {
        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.getKey().equals(key))
            {
                return entry;
            }
        }

        throw new TranslationException(String.format("No entry with key: '%s' found in translation request!", key));
    }

    @Override
    public final String getTarget(String key)
    {
        return targets.get(key);
    }

    @Override
    public final void setCompactMode(final boolean mode)
    {
        this.compactMode = mode;
    }
}
