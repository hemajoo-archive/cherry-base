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
import lombok.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface defining the behavior of a translation request.
 * <br>
 * A translation request is composed of individual entries. Each of them represents a single word or a phrase or even
 * a text to be translated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * see {@link ITranslationRequestEntry}
 */
public interface ITranslationRequest
{
    /**
     * Return the source property entries.
     * @return Source property entries.
     */
    Map<String, String> getSources();

    /**
     * Return the target property entries.
     * @return Target property entries.
     */
    Map<String, String> getTargets();

    /**
     * Return the translation request entries.
     * @return Translation request entries.
     */
    List<TranslationRequestEntry> getEntries();

    /**
     * Retrieve a target property entry given its key.
     * @param key Target property key.
     * @return Value of the target property key.
     */
    String getTarget(String key);

    /**
     * Return the query containing the translation to realize.
     * @return String representing the query to translate.
     */
    String getQuery();

    /**
     * Return the query containing the translation to realize for a specific request entry.
     * @param key Key.
     * @return String representing the query to translate for a specific request entry.
     * @throws TranslationException Thrown in case an error occurred while retrieving the query.
     */
    String getQuery(final @NonNull String key) throws TranslationException;

    /**
     * Set the source properties.
     * @param content Source property content.
     */
    void setSourceProperties(final @NonNull String content);

    /**
     * Set the locale for the source.
     * @param locale Locale for the source.
     */
    void setSourceLocale(final @NonNull Locale locale);

    /**
     * Set the target properties.
     * @param content Target property content.
     */
    void setTargetProperties(final @NonNull String content);

    /**
     * Set the locale for the target.
     * @param locale Locale for the target.
     */
    void setTargetLocale(final @NonNull Locale locale);

    /**
     * Return the locale for the source translation.
     * @return Locale.
     */
    Locale getSourceLocale();

    /**
     * Return the locale for the target translation.
     * @return Locale.
     */
    Locale getTargetLocale();


    /**
     * Return if the request is to be processed in {@code compact mode} or not.
     * @return True if the translation is to be processed in compact mode, false otherwise.
     */
    boolean isCompactMode();

    /**
     * Set the compact mode.
     * @param mode True if the translation has to be realized in compact mode, false otherwise.
     */
    void setCompactMode(final boolean mode);

    /**
     * Return the number of request entries to translate.
     * @return Number of request entries to translate.
     */
    int getCount();

    /**
     * Find the translation request entry for the given value.
     * @param value value.
     * @return Translation request entry if one matches, <b>null</b> otherwise.
     * @throws TranslationException Throw in case an error occurred while looking for a translation request entry.
     */
    ITranslationRequestEntry findEntryKeyFor(@NonNull String value) throws TranslationException;

    /**
     * Return the request entry for the given key.
     * @param key Key of the entry to retrieve.
     * @return {@link ITranslationRequestEntry} representing the request entry.
     * @throws TranslationException Throw in case an error occurred while retrieving a translation request entry.
     */
    ITranslationRequestEntry getEntry(final @NonNull String key) throws TranslationException;
}
