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
package com.hemajoo.commerce.cherry.base.i18n.localization;

import com.google.common.collect.Maps;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.internal.LocalizationFieldContext;
import com.hemajoo.commerce.cherry.base.i18n.localization.internal.LocalizationInvocationContext;
import com.hemajoo.commerce.cherry.base.i18n.localization.type.LocalizationInvocationType;
import com.hemajoo.commerce.cherry.base.i18n.translation.Translation;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.ITranslator;
import com.hemajoo.commerce.cherry.base.i18n.translation.engine.google.GoogleFreeTranslator;
import com.hemajoo.commerce.cherry.base.i18n.translation.exception.TranslationException;
import com.hemajoo.commerce.cherry.base.utilities.helper.ReflectionHelper;
import com.hemajoo.commerce.cherry.base.utilities.helper.StringExpander;
import com.hemajoo.commerce.cherry.base.utilities.helper.StringExpanderException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * A localization manager (singleton) that serves as a central access point for resource bundle localization.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public final class I18nManager
{
    /**
     * Create the unique (per JVM) instance of the singleton.
     */
    private static final I18nManager INSTANCE = new I18nManager();

    /**
     * Setter method name.
     */
    public static final String SETTER_FIELD_NAME = "setValue";

    /**
     * Locale of the manager.
     */
    @Getter
    private Locale locale;

    /**
     * Collection of resource bundles (k = locale, v = Resource bundle name, w = Resource bundle).
     */
    private final Map<Locale, Map<String, ResourceBundle>> bundles = new HashMap<>();

    /**
     * <b>Google</b> free translation processor.
     */
    private final ITranslator translationProcessor = new GoogleFreeTranslator();

    /**
     * Return the unique instance of the <b>I18nManager</b>.
     * @return Manager's instance.
     */
    public static I18nManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * Avoid direct instantiation!
     */
    private I18nManager()
    {
        this.locale = Locale.forLanguageTag("en"); // Set the default manager's locale to english.
        LOGGER.debug(String.format("I18nManager initialized with locale: '%s (%s)'", this.locale, this.locale.getDisplayLanguage()));
    }

    /**
     * Set the locale to use.
     * @param locale Locale to set.
     */
    @Synchronized
    public void setLocale(final @NonNull Locale locale)
    {
        this.locale = locale;
        LOGGER.info(String.format("Locale set to: '%s (%s)'", this.locale, this.locale.getDisplayLanguage()));
    }

    /**
     * Load and register a resource bundle using the default locale.
     * @param path Resource bundle path.
     * @throws LocalizationException Thrown to indicate a resource bundle cannot be found.
     */
    public void load(final @NonNull String path) throws LocalizationException
    {
        load(path, null);
    }

    /**
     * Load a resource bundle or a set of resource bundles.
     * <br>
     * If locale is set to <b>null</b>, all available resource bundle properties files will be loaded!
     * If locale is not set to <b>null</b>, only the matching resource bundle properties file will be loaded!
     * @param path Resource bundle path.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate a resource bundle cannot be found.
     */
    public void load(final @NonNull String path, final Locale locale) throws LocalizationException
    {
        if (locale != null)
        {
            add(path,locale);
        }
        else
        {
            for (Locale current : getFilteredLocales())
            {
                add(path,current);
            }
        }
    }

    /**
     * Retrieve the value of the given resource bundle key.
     * @param key Resource bundle key.
     * @return Value (localized).
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public String get(final @NonNull String key) throws LocalizationException
    {
        return get(key, locale);
    }

    /**
     * Retrieve the value of the given resource bundle key.
     * @param key Resource bundle key.
     * @param locale Locale for the localization.
     * @return Value (localized).
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public String get(final @NonNull String key, final @NonNull Locale locale) throws LocalizationException
    {
        return lookup(key, locale);
    }

    /**
     * Retrieve the value (localized) of a given resource bundle key.
     * @param bundle Resource bundle (path and name).
     * @param key Resource bundle key.
     * @param locale Locale.
     * @return value (localized).
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public String get(final @NonNull String bundle, final @NonNull String key, final @NonNull Locale locale) throws LocalizationException
    {
        load(bundle, locale);
        return lookup(key,locale);
    }

    /**
     * Retrieve the value (localized) of a given resource bundle key.
     * @param bundle Resource bundle (path and name).
     * @param key Resource bundle key.
     * @param locale Locale.
     * @param instance Instance of the object containing the values of the variables to substitute in {@code bundle} and {@code key}.
     * @return Value (localized).
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public String get(final @NonNull String bundle, final @NonNull String key, final @NonNull Locale locale, final Object instance) throws LocalizationException
    {
        try
        {
            load(StringExpander.expandVariables(instance, bundle), locale);
            return lookup(StringExpander.expandVariables(instance, key), locale);
        }
        catch (StringExpanderException e)
        {
            throw new LocalizationException(e.getMessage());
        }
    }

//    /**
//     * Localize a resource.
//     * @param locale Locale.
//     * @return Localized value.
//     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
//     */
//    public String localize(final Locale locale) throws LocalizationException
//    {
//        return localize(null, locale != null ? locale : this.locale);
//    }

    /**
     * Localize a resource.
     * @param instance Instance containing the resource to localize.
     * @param locale Locale.
     * @return Localized value.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    public String localize(final @NonNull Object instance, final Locale locale) throws LocalizationException
    {
        LocalizationInvocationContext context = new LocalizationInvocationContext();
        context.setInvocationType(LocalizationInvocationType.UNKNOWN);

        // Compute the invocation context
        findInvocationMethod(context, instance);

        if (context.getInvocationType() == LocalizationInvocationType.UNKNOWN)
        {
            findInvocationField(context, instance);
        }

        switch (context.getInvocationType())
        {
            case METHOD:
                return localizeElement(context.getInstanceClassAnnotation(), context.getMethodAnnotation(), instance, locale);

            case FIELD:
                localizeAllField(context, instance, locale);
                return null;

            case UNKNOWN:
            default:
                throw new LocalizationException(String.format("Unknown localize() service invocation for object of type: '%s' and locale: '%s'", instance.getClass().getName(), locale));
        }
    }

    /**
     * Returns a list of filtered locales based on a range of authorized languages.
     * @return List of filtered locales.
     */
    private List<Locale> getFilteredLocales()
    {
        final String languagesPriorityRange = "en;q=1.0,fr;q=0.5,de;q=0.5,it;q=0.5,es;q=0.5,ja;q=0.5,af;q=0.5," +
                "ar;q=0.5,bg;q=0.5,cs;q=0.5,da;q=0.5,el;q=0.5,et;q=0.5,fi;q=0.5,hi;q=0.5,hu;q=0.5,iw;q=0.5,ko;q=0.5," +
                "nl;q=0.5,no;q=0.5,pl;q=0.5,pt;q=0.5,ro;q=0.5,ru;q=0.5,sq;q=0.5,th;q=0.5,tr;q=0.5,zh;q=0.5";

        List<String> listWithDuplicates = new ArrayList<>();

        // Get only locales for languages
        for (Locale current : Locale.getAvailableLocales())
        {
            listWithDuplicates.add(current.getLanguage());
        }

        // Remove duplicates
        List<String> listWithoutDuplicates = listWithDuplicates.stream()
                .distinct()
                .toList();

        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(languagesPriorityRange);
        List<String> filtered = Locale.filterTags(languageRanges, listWithoutDuplicates);

        return filtered.stream().map(Locale::forLanguageTag).toList();
    }

    /**
     * Add a resource bundle.
     * @param path Resource bundle path and name.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate a resource bundle cannot be found.
     */
    private void add(final @NonNull String path, final @NonNull Locale locale) throws LocalizationException
    {
        ResourceBundle bundle = getBundleFor(path, locale);

        if (bundle == null)
        {
            bundle = ResourceBundle.getBundle(path, locale);

            if (bundle == null)
            {
                throw new LocalizationException(String.format("Cannot find bundle: '%s'", path));
            }

            if (bundle.getLocale().equals(locale))
            {
                LOGGER.debug(String.format("Found resource bundle: '%s' for language: '%s (%s)' with: '%s' entries", path, locale, locale.getDisplayLanguage(), bundle.keySet().size()));
            }
            else
            {
                LOGGER.debug(String.format("Cannot find resource bundle: '%s' for language: '%s (%s)'. Replacing with default: '%s (%s)' with: '%s' entries", path, locale, locale.getDisplayLanguage(), bundle.getLocale(), bundle.getLocale().getDisplayLanguage(), bundle.keySet().size()));
            }

            bundles.computeIfAbsent(Locale.forLanguageTag(locale.getLanguage()), function -> Maps.newHashMap()).put(path, bundle);
        }
    }

    /**
     * Return a registered resource bundle.
     * @param path Path (and name) of the resource bundle.
     * @param locale Locale.
     * @return Resource bundle if one matches, <b>null</b> otherwise.
     */
    private ResourceBundle getBundleFor(final @NonNull String path, final @NonNull Locale locale)
    {
        Map<String, ResourceBundle> bundlesForLocale = bundles.get(locale);

        return bundlesForLocale == null ? null : bundlesForLocale.get(path);
    }

    /**
     * Lookup the first matching resource bundle entry matching the given key in registered resource bundles.
     * @param key Resource bundle key.
     * @param locale Locale.
     * @return Resource bundle value matching the key, <b>null</b> otherwise.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    private String lookup(final @NonNull String key, final @NonNull Locale locale) throws LocalizationException
    {
        if (bundles.isEmpty())
        {
            throw new LocalizationException(String.format(
                    "No resource bundle found containing key: '%s' for locale: '%s'. Try loading the resource bundle first using I18nManager#load service!",
                    key,
                    locale));
        }

        Locale currentLocale = Locale.forLanguageTag(locale.getLanguage());

        Map<String, ResourceBundle> elements = bundles.get(currentLocale);
        for (ResourceBundle bundle : elements.values())
        {
            if (!currentLocale.getDisplayLanguage().equals(locale.getDisplayLanguage()))
            {
                LOGGER.warn(String.format("No resource bundle: '%s', language-tag: '%s', language: '%s' found!",
                        bundle.getBaseBundleName(), locale.toLanguageTag(), locale.getDisplayLanguage()));
            }

            if (bundle.containsKey(key))
            {
                return bundle.getString(key);
            }
            else
            {
                loadBundle(bundle, locale);
                if (bundle.containsKey(key))
                {
                    return bundle.getString(key);
                }
            }
        }

        throw new LocalizationException(String.format("Resource key: '%s' for locale: '%s' not found!", key, locale));
    }

    /**
     * Load a resource bundle, if absent.
     * @param bundle Resource bundle.
     * @param locale Locale.
     */
    private void loadBundle(final @NonNull ResourceBundle bundle, final @NonNull Locale locale)
    {
        try
        {
            load(bundle.getBaseBundleName(), locale);
        }
        catch (Exception e)
        {
            // Do nothing!
        }
    }

    /**
     * Localize all fields of an object.
     * @param context Localization context.
     * @param instance Object instance.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    private void localizeAllField(final @NonNull LocalizationInvocationContext context, final Object instance, final Locale locale) throws LocalizationException
    {
        Method method;
        String localized;

        // Localize value for each field found annotated with I18n annotation
        for (LocalizationFieldContext fieldContext : context.getFields())
        {
            try
            {
                method = findSetterForField(context, fieldContext);
                if (method != null)
                {
                    localized = localizeElement(context.getDeclaringClassAnnotation(), fieldContext.getFieldAnnotation(), instance, locale);
                    method.invoke(instance, localized);
                }
            }
            catch (Exception e)
            {
                throw new LocalizationException(e.getMessage());
            }
        }
    }

    /**
     * Find the invocation method being the one which invoked the localization.
     * @param context Localization context.
     * @param instance Object instance.
     */
    private void findInvocationMethod(final @NonNull LocalizationInvocationContext context, final Object instance)
    {
        Class<?> clazz;

        for (StackTraceElement trace : Thread.currentThread().getStackTrace())
        {
            try
            {
                clazz = Class.forName(trace.getClassName());

                isInvocationMethodValid(context, instance, clazz, trace);
                if (context.getMethod() != null)
                {
                    return;
                }
            }
            catch (ClassNotFoundException e)
            {
                // Do nothing, just process the next trace!
            }
        }
    }

    /**
     * Find the invocation field being the one which invoked the localization.
     * @param context Localization context.
     * @param instance Object instance.
     */
    private void findInvocationField(final @NonNull LocalizationInvocationContext context, final Object instance)
    {
        Class<?> declaringClass;

        // Find all fields annotated with I18n annotation
        List<Field> fields = ReflectionHelper.findAnnotatedFieldsInClassHierarchy(instance.getClass(), I18n.class);
        for (Field field : fields)
        {
            declaringClass = field.getDeclaringClass();

            context.setInvocationType(LocalizationInvocationType.FIELD);
            context.addField(new LocalizationFieldContext(field, field.getAnnotation(I18n.class)));

            if (context.getDeclaringClass() == null)
            {
                context.setDeclaringClass(declaringClass);
                if (declaringClass.isAnnotationPresent(I18n.class))
                {
                    context.setDeclaringClassAnnotation(declaringClass.getAnnotation(I18n.class));
                }
            }
        }
    }

    /**
     * Check if the found method is a valid method which may have invoked the localization?
     * @param context Localization context.
     * @param instance Object instance.
     * @param clazz Class.
     * @param trace Stack trace element.
     */
    private void isInvocationMethodValid(final @NonNull LocalizationInvocationContext context, final Object instance, final @NonNull Class<?> clazz, final @NonNull StackTraceElement trace)
    {
        Method method;

        try
        {
            method = clazz.getMethod(trace.getMethodName());
            verifyMethodSignature(context, instance, method);
        }
        catch (NoSuchMethodException e)
        {
            try
            {
                // Maybe the method has a parameter of type Locale
                method = clazz.getMethod(trace.getMethodName(), Locale.class);
                verifyMethodSignature(context, instance, method);
            }
            catch (NoSuchMethodException oe)
            {
                // Do nothing, seems to be the wrong method!
            }
        }
    }

    /**
     * Verify the method signature.
     * @param context Localization context.
     * @param instance Object instance.
     * @param method Method.
     */
    private void verifyMethodSignature(final @NonNull LocalizationInvocationContext context, final Object instance, final @NonNull Method method)
    {
        if (method.isAnnotationPresent(I18n.class)) // Method should be annotated with I18n annotation
        {
            fillInvocationContext(context, instance, method);
        }

        if (method.getDeclaringClass().isAssignableFrom(LocalizeEnum.class)) // Method's class should implement the LocalizeEnum interface
        {
            fillInvocationContext(context, instance, method);
        }
    }

    /**
     * Fill the localization context.
     * @param context Localization context.
     * @param instance Object instance.
     * @param method Method.
     */
    private void fillInvocationContext(final @NonNull LocalizationInvocationContext context, final Object instance, final @NonNull Method method)
    {
        // Fill the method's context data
        context.setMethod(method);
        context.setInvocationType(LocalizationInvocationType.METHOD);

        if (method.isAnnotationPresent(I18n.class))
        {
            context.setMethodAnnotation(method.getAnnotation(I18n.class));
        }

        // Fill the class's context data
        Class<?> declaringClass = method.getDeclaringClass();
        context.setDeclaringClass(declaringClass);

        if (declaringClass.isAnnotationPresent(I18n.class))
        {
            context.setDeclaringClassAnnotation(declaringClass.getAnnotation(I18n.class));
        }

        if (instance instanceof Enum<?> value)
        {
            Class<?> instanceClass = value.getClass();
            context.setInstanceClass(instanceClass);
            if (instanceClass.isAnnotationPresent(I18n.class))
            {
                context.setInstanceClassAnnotation(instanceClass.getAnnotation(I18n.class));
            }
        }
    }

    /**
     * Find the setter method for a given field.
     * @param context Localization context.
     * @param fieldContext Field localization context.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    private Method findSetterForField(LocalizationInvocationContext context, LocalizationFieldContext fieldContext) throws LocalizationException
    {
        Method method = null;

        try
        {
            method = context.getDeclaringClass().getMethod("set" + StringUtils.capitalize(fieldContext.getField().getName()), String.class);
        }
        catch (Exception e)
        {
            try
            {
                method = context.getDeclaringClass().getMethod("set" + StringUtils.capitalize(fieldContext.getField().getName()), String.class, Locale.class);
            }
            catch (Exception ex)
            {
                throw new LocalizationException(e.getMessage());
            }
        }

        return method;
    }

    /**
     * Localize an element.
     * @param classAnnotation Annotation on the class.
     * @param elementAnnotation Annotation on the element.
     * @param instance Instance.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    private String localizeElement(final Annotation classAnnotation, final Annotation elementAnnotation, final Object instance, final @NonNull Locale locale) throws LocalizationException
    {
        String classBundle = null;
        String classKey = null;
        String bundle = null;
        String key = null;
        String localized;

        try
        {
            if (classAnnotation != null)
            {
                I18n annotation = (I18n) classAnnotation;

                if (!annotation.bundle().isEmpty())
                {
                    classBundle = StringExpander.expandVariables(instance, annotation.bundle());
                }

                if (!annotation.key().isEmpty())
                {
                    classKey = StringExpander.expandVariables(instance, annotation.key());
                }
            }

            if (elementAnnotation != null)
            {
                I18n annotation = (I18n) elementAnnotation;

                if (!annotation.bundle().isEmpty())
                {
                    bundle = StringExpander.expandVariables(instance, annotation.bundle());
                }

                if (!annotation.key().isEmpty())
                {
                    key = StringExpander.expandVariables(instance, annotation.key());
                }
            }

            if ((bundle == null || bundle.isBlank()) && (classBundle == null || Objects.requireNonNull(classBundle).isBlank()))
            {
                throw new LocalizationException("Element annotated with the I18n annotation must provide a valid 'bundle' parameter!");
            }

            if ((key == null || key.isBlank()) && (classKey == null || Objects.requireNonNull(classKey).isBlank()))
            {
                throw new LocalizationException("Element annotated with the I18n annotation must provide a valid 'key' parameter!");
            }

            localized = getKey(bundle != null ? bundle : classBundle, key != null ? key : classKey, locale);
        }
        catch (Exception e)
        {
            throw new LocalizationException(e.getMessage());
        }

        return localized;
    }

    /**
     * Localize the value of an <b>instant localization</b> object.
     * @param instance Instance.
     * @param reference Object reference.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate an error occurred when trying to localize a resource.
     */
    public void localizeInstantLocalization(final @NonNull Object instance, final Object reference, final Locale locale) throws LocalizationException
    {
        Method method;
        String expandedKey;
        String expandedBundle;
        String translated;

        if (instance instanceof InstantLocalization element)
        {
            if (element.getKey() != null)
            {
                try
                {
                    expandedBundle = StringExpander.expandVariables(reference != null ? reference : instance, element.getBundle() != null ? element.getBundle() : element.getKey());
                    expandedKey = StringExpander.expandVariables(reference != null ? reference : instance, element.getKey());

                    if (element.getBundle() != null)
                    {
                        translated = get(expandedBundle, expandedKey, locale);
                    }
                    else
                    {
                        translated = get(expandedKey, locale);
                    }

                    method = instance.getClass().getDeclaredMethod(SETTER_FIELD_NAME, String.class);
                    method.invoke(element, translated);
                }
                catch (Exception e)
                {
                    throw new LocalizationException(e.getMessage());
                }
            }
            else
            {
                throw new LocalizationException(String.format("Localize service has been invoked for object of type: '%s' but its property named: 'key' has not been set! Ensure the 'key' property references a valid resource bundle key!", instance.getClass().getSimpleName()));
            }
        }
    }

    /**
     * Retrieve the given key from the given resource bundle path.
     * @param filePath Resource bundle path and name.
     * @param key Key.
     * @param locale Locale.
     * @return Value (localized)).
     */
    private String getKey(final @NonNull String filePath, final @NonNull String key, final @NonNull Locale locale) throws LocalizationException
    {
        ResourceBundle bundle;
        Locale currentLocale = locale;

        // Ensure the resource bundles are loaded
        load(filePath);

        Map<String, ResourceBundle> elements = bundles.get(currentLocale);
        if (elements != null)
        {
            bundle = elements.get(filePath);
            if (bundle != null)
            {
                if (!bundle.getLocale().toLanguageTag().equals(currentLocale.toLanguageTag()))
                {
                    LOGGER.warn(String.format("Warning: cannot find resource bundle: '%s', language-tag: '%s', language: '%s'!",
                            bundle.getBaseBundleName(), currentLocale, currentLocale.getDisplayLanguage()));
                }

                try
                {
                    return bundle.getString(key);
                }
                catch (MissingResourceException e)
                {
                    throw new LocalizationException(
                            String.format(
                                    "Cannot find key: '%s' in bundle: '%s' for locale: '%s'",
                                    key,
                                    filePath,
                                    currentLocale));
                }
            }
        }
        else
        {
            LOGGER.warn(String.format("No resource bundle found for bundle: '%s', language: '%s (%s)'! Use of default language: '%s (%s)' instead",
                    filePath,
                    currentLocale,
                    currentLocale.getDisplayLanguage(),
                    getLocale().toLanguageTag(),
                    getLocale().getDisplayLanguage()));

            currentLocale = Locale.forLanguageTag(getLocale().getLanguage());
        }

        return getKey(filePath, key, currentLocale);
    }

    /**
     * Clear all loaded resource bundles.
     */
    public void clearAll()
    {
        bundles.clear();
    }

//    @Synchronized
//    public GoogleTranslationResult translate(final @NonNull Translation text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
//    {
//        return (GoogleTranslationResult) translationProcessor.translate(text,source,target);
//    }

    /**
     * Translate a text.
     * @param text Text to translate.
     * @param source Source locale.
     * @param target Target locale.
     * @return Translated text.
     * @throws TranslationException Thrown to indicate an error occurred while trying to translate a text.
     */
    @Synchronized
    public String translate(final @NonNull Translation text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
    {
        return translationProcessor.translate(text,source,target);
    }
}
