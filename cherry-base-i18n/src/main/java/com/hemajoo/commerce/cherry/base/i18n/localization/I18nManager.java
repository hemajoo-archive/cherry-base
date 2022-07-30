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
import com.hemajoo.commerce.cherry.base.commons.exception.AnnotationException;
import com.hemajoo.commerce.cherry.base.commons.exception.NotYetImplementedException;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18n;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18nBundle;
import com.hemajoo.commerce.cherry.base.i18n.localization.annotation.I18nEnum;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.I18nException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.LocalizationException;
import com.hemajoo.commerce.cherry.base.i18n.localization.exception.ResourceException;
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
     * Locale of the <b>I18n manager</b>.
     */
    @Getter
    private Locale locale;

    /**
     * Collection of resource bundles (k = locale, v = Resource bundle name, w = Resource bundle).
     */
    private final Map<Locale, Map<String, ResourceBundle>> bundles = new HashMap<>();

    /**
     * Google free translation processor.
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
        //Locale.setDefault(Locale.forLanguageTag("en"));
        this.locale = Locale.forLanguageTag("en"); // Set the default manager's locale to english.

    }

    /**
     * Set the locale to use.
     * @param locale Locale to set.
     */
    @Synchronized
    public void setLocale(final @NonNull Locale locale)
    {
        LOGGER.info(String.format("Locale set to: '%s'", locale.getDisplayLanguage()));
        //Locale.setDefault(locale);
        this.locale = locale;
    }

    /**
     * Load and register a resource bundle using the default locale.
     * @param path Resource bundle path.
     */
    public void load(final @NonNull String path)
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
     */
    public void load(final @NonNull String path, final Locale locale)
    {
        if (locale != null)
        {
            try
            {
                add(path,locale);
            }
            catch (MissingResourceException e)
            {
                LOGGER.warn(String.format("No resource bundle found: '%s' for locale: '%s'", path, locale));
            }
        }
        else
        {
            for (Locale current : getFilteredLocales())
            {
                try
                {
                    add(path,current);
                }
                catch (MissingResourceException e)
                {
                    LOGGER.warn(String.format("No resource bundle found: '%s' for locale: '%s'", path, current));
                }
            }
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
     */
    private void add(final @NonNull String path, final @NonNull Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(path, locale);
        bundles.computeIfAbsent(Locale.forLanguageTag(locale.getLanguage()), function -> Maps.newHashMap()).put(path, bundle);
        LOGGER.debug(String.format("Resource bundle: '%s' for language: '%s' loaded", path, locale));
    }

    /**
     * Retrieve the value (localized) of the given resource bundle key.
     * @param key Resource bundle key.
     * @return Value (localized).
     */
    public String get(final @NonNull String key) throws ResourceException
    {
        return get(key, locale);
    }

    /**
     * Retrieve the value (localized) of the given resource bundle key.
     * @param key Resource bundle key.
     * @param locale Locale for the localization.
     * @return Value (localized).
     */
    public String get(final @NonNull String key, final @NonNull Locale locale) throws ResourceException
    {
        return lookup(key, locale);
    }

    /**
     * Retrieve the value (localized) of a given resource bundle key.
     * @param bundle Resource bundle (path and name).
     * @param key Resource bundle key.
     * @param locale Locale.
     * @return value (localized).
     */
    public String get(final @NonNull String bundle, final @NonNull String key, final @NonNull Locale locale) throws ResourceException
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
     */
    public String get(final @NonNull String bundle, final @NonNull String key, final @NonNull Locale locale, final Object instance) throws StringExpanderException, ResourceException
    {
        load(StringExpander.expandVariables(instance, bundle), locale);
        return lookup(StringExpander.expandVariables(instance, key), locale);
    }

    /**
     * Lookup the first matching resource bundle entry matching the given key in registered resource bundles.
     * @param key Resource bundle key.
     * @param locale Locale.
     * @return Resource bundle value matching the key, <b>null</b> otherwise.
     */
    private String lookup(final @NonNull String key, final @NonNull Locale locale) throws ResourceException
    {
        if (bundles.isEmpty())
        {
            throw new ResourceException(String.format(
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

        throw new ResourceException(String.format("Resource key: '%s' for locale: '%s' not found!", key, locale));
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

    public String localize(final Locale locale) throws LocalizationException
    {
        return localize(null, locale);
    }

    public String localize(final Object instance, final Locale locale) throws LocalizationException
    {
        LocalizationInvocationContext context = new LocalizationInvocationContext();
        context.setInvocationType(LocalizationInvocationType.UNKNOWN);

        // Compute the invocation context
        findInvocationMethod(context, instance);

        switch (context.getInvocationType())
        {
            case METHOD:
                return localizeFromMethod(context, instance, locale);

            case FIELD:
                localizeFromField(context, instance, locale);
                break;

            case UNKNOWN:
            default:
                throw new LocalizationException(String.format("Unknown localize() service invocation for object of type: '%s' and locale: '%s'", instance.getClass().getName(), locale));
        }

        throw new LocalizationException("What is the hell!");
    }

    private String localizeFromMethod(final @NonNull LocalizationInvocationContext context, final Object instance, final Locale locale) throws LocalizationException
    {
        String classBundle = null;
        String classKey = null;
        String bundle = null;
        String key = null;
        String localized;

        try
        {
            if (context.getInstanceClassAnnotation() != null)
            {
                I18n annotation = (I18n) context.getInstanceClassAnnotation();

                if (!annotation.bundle().isEmpty())
                {
                    classBundle = StringExpander.expandVariables(instance, annotation.bundle());
                }

                if (!annotation.key().isEmpty())
                {
                    classKey = StringExpander.expandVariables(instance, annotation.key());
                }
            }

            if (context.getMethodAnnotation() != null)
            {
                I18n annotation = (I18n) context.getMethodAnnotation();

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
                throw new LocalizationException("classBundle & bundle cannot be both null or blank!"); // TODO Re-write exception message!
            }

            if ((key == null || key.isBlank()) && (classKey == null || Objects.requireNonNull(classKey).isBlank()))
            {
                throw new LocalizationException("classKey & key cannot be both null or blank!"); // TODO Re-write exception message!
            }

            localized = getKey(bundle != null ? bundle : classBundle, key != null ? key : classKey, locale != null ? locale : Locale.forLanguageTag(locale.getLanguage()));
        }
        catch (Exception e)
        {
            throw new LocalizationException(e.getMessage());
        }

        return localized;
    }

    private void localizeFromField(final @NonNull LocalizationInvocationContext context, final Object instance, final Locale locale)
    {

    }

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

    private void verifyMethodSignature(final @NonNull LocalizationInvocationContext context, final Object instance, final @NonNull Method method)
    {
        if (method.isAnnotationPresent(I18n.class)) // Method should be annotated with I18n annotation
        {
            fillInvocationContext(context, instance, method);
        }

        if (method.getDeclaringClass().isAssignableFrom(LocalizeEnumAware.class)) // Method's class should implement the LocalizeEnumAware interface
        {
            fillInvocationContext(context, instance, method);
        }
    }

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

        if (declaringClass.isAnnotationPresent(I18nBundle.class))
        {
            context.setDeclaringClassAnnotation(declaringClass.getAnnotation(I18nBundle.class));
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
     * Localize an enum element.
     * @param instance Object instance containing the element to localize.
     * @param locale Locale.
     * @return Localized value.
     * @throws LocalizationException Thrown to indicate an error with a localization.
     */
    public String localizeEnum(final @NonNull Object instance, final Locale locale) throws LocalizationException
    {
        Method method = getCallerMethod();

        if (instance instanceof Enum)
        {
            return resolveEnum(instance, locale);
        }
        else
        {
            if (method != null && method.isAnnotationPresent(I18n.class))
            {
                resolveMethod(instance, method.getAnnotation(I18n.class), method, locale);
            }
        }

        throw new LocalizationException(String.format("Service localizeEnum() can only be invoked on a Enum types! Class of type: '%s' is not an enum.", instance.getClass().getName()));
    }

    /**
     * Resolve the localization for an enum class.
     * @param instance Enumerated value instance.
     * @param locale Locale.
     * @return Localized value.
     * @throws LocalizationException Thrown to indicate an error with a localization.
     */
    public String resolveEnum(final @NonNull Object instance, final Locale locale) throws LocalizationException
    {
        Class<?> declaringClass = ((Enum<?>) instance).getDeclaringClass();
        Method method = getCallerMethod();


        // For enumerations, the class itself can be annotated with the I18nEnum annotation
        // or some of the methods can be annotated with the I18nEnum annotation.

        // Does the method of the enumeration class is annotated with I18nEnum annotation?
        if (method != null)
        {
            if (method.isAnnotationPresent(I18nEnum.class))
            {
                return resolveAnnotatedEnumMethod(instance, method, locale);
            }
            else
            {
                // Does the enumeration itself is annotated with I18nEnum annotation?
                if (declaringClass.isAnnotationPresent(I18nEnum.class))
                {
                    return resolveAnnotatedEnumClass(instance, locale);
                }
                else
                {
                    throw new LocalizationException(
                            String.format(
                                    "Object of type: '%s' is an enum class that should be annotated with: '%s' annotation!",
                                    declaringClass.getSimpleName(), I18nEnum.class.getSimpleName()));
                }
            }
        }
        else
        {
            // Does the enumeration class itself is annotated with I18nEnum annotation?
            if (declaringClass.isAnnotationPresent(I18nEnum.class))
            {
                return resolveAnnotatedEnumClass(instance, locale);
            }
            else
            {
                throw new LocalizationException(
                        String.format(
                                "Object of type: '%s' is an enum class that should be annotated with: '%s' annotation!",
                                declaringClass.getSimpleName(), I18nEnum.class.getSimpleName()));
            }
        }
    }

    /**
     * Resolve the localization for an annotated enum class.
     * @param instance Enumerated value instance.
     * @param locale Locale.
     * @return Localized string.
     * @throws LocalizationException Thrown to indicate an error with a localization.
     */
    private String resolveAnnotatedEnumClass(final @NonNull Object instance, final Locale locale) throws LocalizationException
    {
        I18nEnum annotation = ((Enum<?>) instance).getDeclaringClass().getAnnotation(I18nEnum.class);

        // Ensure the resource bundle properties files are loaded
        load(annotation.bundle());

        try
        {
            return getKey(StringExpander.expandVariables(instance, annotation.bundle()),StringExpander.expandVariables(instance, annotation.key()), locale);
        }
        catch (StringExpanderException | ResourceException e)
        {
            throw new LocalizationException(e.getMessage());
        }
    }

    /**
     * Resolve the localization of an annotated enum method.
     * @param instance Enumerated value instance.
     * @param locale Locale.
     * @return Localized value.
     * @throws LocalizationException Thrown to indicate an error with a localization.
     */
    private String resolveAnnotatedEnumMethod(final @NonNull Object instance, final @NonNull Method method, final Locale locale) throws LocalizationException
    {
        Class<?> declaringClass = ((Enum<?>) instance).getDeclaringClass();

        I18nEnum annotation = method.getAnnotation(I18nEnum.class);
        if (annotation == null)
        {
            throw new LocalizationException(String.format("Method: '%s' of enum type: '%s' should be annotated with: '%s' annotation!", method.getName(), declaringClass.getName(), I18nEnum.class.getName()));
        }

        return resolveMethod(instance, annotation, method, locale);
    }

    /**
     * Localize a method.
     * @param instance Instance of the object holding the resource to localize.
     * @param annotation Annotation.
     * @param method Method.
     * @param locale Locale.
     * @throws LocalizationException Thrown to indicate an error with a localization.
     */
    private String resolveMethod(final @NonNull Object instance, final @NonNull Annotation annotation, final @NonNull Method method, final @NonNull Locale locale) throws LocalizationException
    {
        String i18nBundle = null;
        String i18nKey = null;
        String annotationClassName = null;

        if (annotation instanceof I18n i18n)
        {
            annotationClassName = I18n.class.getName();
            i18nKey = i18n.key();
            i18nBundle = i18n.bundle();
        }
        else if (annotation instanceof I18nEnum i18n)
        {
            annotationClassName = I18nEnum.class.getName();
            i18nKey = i18n.key();
            i18nBundle = i18n.bundle();
        }

        if (i18nBundle == null)
        {
            throw new LocalizationException(
                    String.format(
                            "Method: '%s' of class: '%s' annotated with: '%s' must have the parameter: 'bundle' set to a valid value!",
                            method.getName(), instance.getClass().getName(), annotationClassName));
        }

        if (i18nKey == null)
        {
            throw new LocalizationException(
                    String.format(
                            "Method: '%s' of class: '%s' annotated with: '%s' must have the parameter: 'key' set to a valid value!",
                            method.getName(), instance.getClass().getName(), annotationClassName));
        }

        try
        {
            // Are there some variable substitutions to resolve (if some) for the 'bundle' and the 'key' properties.
            String key = StringExpander.expandVariables(instance, i18nKey);
            String bundle = StringExpander.expandVariables(instance, i18nBundle);

            return getKey(bundle, key, Locale.forLanguageTag(locale.getLanguage()));
        }
        catch (Exception e)
        {
            throw new LocalizationException(e.getMessage());
        }
    }



























    /**
     * Directly resolve the localization of an individual element.
     * @param instance Object instance containing the element to localize.
     * @param locale Locale.
     * @return Value (localized).
     */
    public String resolveDirect(final @NonNull Object instance, final Locale locale) throws AnnotationException, LocalizationException
    {
        I18n annotation;
        Method method;

        // Invoked from an enumeration?
        if (instance instanceof Enum)
        {
            throw new LocalizationException(String.format("Type: '%s' is an enum type and must not be annotated with the: '%s' annotation! Use the: '%s' one instead.", instance.getClass().getName(), I18nEnum.class.getSimpleName(), I18nEnum.class.getSimpleName()));
        }
        else
        {
            // Invoked from a normal class that should implement the Localized interface.
            method = getCallerMethod();
            if (method == null)
            {
                try
                {
                    throw new AnnotationException(String.format(
                            "Service 'I18nManager#resolveDirect' invoked from a class not being an enum nor from an annotated method! Please check class: '%s'",
                            instance.getClass().getDeclaringClass().getName()));
                }
                catch (NullPointerException e)
                {
                    throw new AnnotationException(String.format(
                            "Service 'I18nManager#resolveDirect' invoked from a class not being an enum nor from an annotated method! Please check class: '%s'",
                            instance.getClass().getName()));
                }
            }
            annotation = method.getDeclaredAnnotation(I18n.class);
        }

        return resolveMethod(instance, annotation, method, locale);
    }

    /**
     * Resolve all entities annotated with the <b>I18n</b> annotation.
     * <br>To get the localized value of an element, simply use its associated getter.
     * @param instance Object instance containing elements to localize.
     * @param locale Locale to use for resolving localization.
     */
    public void resolveIndirect(final @NonNull Object instance, final Locale locale) throws StringExpanderException, AnnotationException, ResourceException, LocalizationException
    {
        // Resolve annotated fields
        resolveFields(instance, locale);

        // Resolve caller method if annotated
        Method method = getCallerMethod();
        if (method != null && method.isAnnotationPresent(I18n.class))
        {
            resolveMethod(instance, method.getAnnotation(I18n.class), method, locale);
        }
    }

    /**
     * Resolve the value of an i18n string.
     * @param instance Object instance to update.
     * @param reference Object reference.
     * @param locale Locale for localization resolution.
     * @throws I18nException Thrown to indicate an error occurred when trying to resolve the localization.
     */
    public void resolveI18nString(final @NonNull Object instance, final Object reference, final Locale locale) throws I18nException
    {
        Method method;
        String expandedKey;
        String expandedBundle;
        String translated;

        if (instance instanceof Localization element)
        {
            if (element.getKey() != null)
            {
                try
                {
                    expandedBundle = StringExpander.expandVariables(
                            reference != null ? reference : instance,
                            element.getBundle() != null ? element.getBundle() : element.getKey());

                    expandedKey = StringExpander.expandVariables(
                            reference != null ? reference : instance,
                            element.getKey());

                    if (element.getBundle() != null)
                    {
                        translated = get(expandedBundle, expandedKey, locale);
                    }
                    else
                    {
                        translated = get(expandedKey, locale);
                    }

                    method = instance.getClass().getDeclaredMethod("setValue", String.class);
                    method.invoke(element, translated);
                }
                catch (Exception e)
                {
                    throw new I18nException(e.getMessage());
                }
            }
            else
            {
                throw new I18nException(String.format("Localize service has been invoked for object of type: '%s' but its property named: 'key' has not been set! Ensure the 'key' property references a valid resource bundle key!", instance.getClass().getSimpleName()));
            }
        }
    }

    /**
     *
     * @param instance
     * @param locale
     * @throws StringExpanderException
     * @throws AnnotationException
     * @throws ResourceException
     */
    private void resolveFields(final @NonNull Object instance, final Locale locale) throws StringExpanderException, AnnotationException, ResourceException
    {
        I18n annotation;
        Localization localization;

        List<Field> fields = ReflectionHelper.findAnnotatedFieldsInClassHierarchy(instance.getClass(), I18n.class);

        for (Field field : fields)
        {
            annotation = field.getDeclaredAnnotation(I18n.class);

            try
            {
                field.setAccessible(true);  // TODO Change this as it's forbidden! Use a public accessor instead!
                if (field.get(instance) instanceof Localization)
                {
                    localization = (Localization) field.get(instance);
                    if (localization != null)
                    {
                        if (localization.getValue() == null && localization.getBundle() == null && localization.getKey() == null)
                        {
                            resolveField(instance, annotation, field, locale);
                        }
                        else
                        {
                            resolveI18nStringAnnotatedField(instance,field, annotation, locale);
                        }
                    }
                }
                else if (Localize.class.isAssignableFrom(field.getDeclaringClass()))
                {
                    if (annotation != null)
                    {
                        if (field.getType() == String.class)
                        {
                            resolveField(instance, annotation, field, locale);
                        }
                        else if (field.getType() == Localization.class)
                        {
                            localization = (Localization) field.get(instance);
                            if (localization != null)
                            {
                                if (localization.getValue() == null && localization.getBundle() == null && localization.getKey() == null)
                                {
                                    resolveField(instance, annotation, field, locale);
                                }
                                else
                                {
                                    resolveI18nStringAnnotatedField(instance,field, annotation, locale);
                                }
                            }
                        }
                    }
                    else
                    {
                        throw new NotYetImplementedException(""); // TODO Fix this!
                    }
                }
                else
                {
                    // Normal field, just localize its value according to the annotation
                }
            }
            catch (IllegalAccessException | NoSuchFieldException e)
            {
                e.printStackTrace(); // TODO Log and Throw an exception!
            }
        }
    }

    /**
     *
     * @param instance
     * @param field
     * @param annotation
     * @param locale
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws StringExpanderException
     * @throws ResourceException
     */
    private void resolveI18nStringAnnotatedField(final @NonNull Object instance, final @NonNull Field field, final @NonNull I18n annotation, final @NonNull Locale locale) throws IllegalAccessException, NoSuchFieldException, StringExpanderException, ResourceException
    {
        String expandedBundle;
        String expandedKey;
        Field value;
        Localization localization = (Localization) field.get(instance);

        value = field.get(instance).getClass().getDeclaredField("value");
        value.setAccessible(true);  // TODO Change this as it's forbidden! Use a public accessor instead!

        if (localization.getBundle() != null && localization.getKey() != null)
        {
            expandedBundle = StringExpander.expandVariables(instance, localization.getBundle());
            expandedKey = StringExpander.expandVariables(instance, localization.getKey());
        }
        else
        {
            expandedBundle = StringExpander.expandVariables(instance, annotation.bundle());
            expandedKey = StringExpander.expandVariables(instance, annotation.key());
        }

        String translated = get(expandedBundle, expandedKey, locale);
        value.set(localization, translated);
    }

    /**
     * Resolve the localization of a field annotated with the <b>I18n</b> annotation.
     * @param instance Instance of the object holding the field to localize.
     * @param annotation I18n annotation.
     * @param field Field.
     * @param locale Locale to use for resolving the localization.
     * @throws AnnotationException
     * @throws StringExpanderException
     * @throws ResourceException
     */
    private void resolveField(final @NonNull Object instance, final @NonNull I18n annotation, final @NonNull Field field, final @NonNull Locale locale) throws AnnotationException, StringExpanderException, ResourceException
    {
        String value;
        String bundle;
        String key;

        if (annotation.key().isEmpty())
        {
            throw new AnnotationException(
                    String.format(
                            "Field: '%s' of class: '%s' annotated with: '%s' must have the property: 'key' set!",
                            field.getName(),
                            instance.getClass().getName(),
                            I18n.class.getName()));
        }

        if (annotation.bundle().isEmpty())
        {
            throw new AnnotationException(
                    String.format(
                            "Field: '%s' of class: '%s' annotated with: '%s' must have the property: 'bundle' set!",
                            field.getName(),
                            instance.getClass().getName(),
                            I18n.class.getName()));
        }

        // Do the variables' substitution (if some) for the 'bundle' and the 'key' properties.
        key = StringExpander.expandVariables(instance, annotation.key());
        bundle = StringExpander.expandVariables(instance, annotation.bundle());

        value = getKey(bundle, key, locale);
        setFieldValue(instance, field, value);
    }

    /**
     * Set dynamically the value of a given annotated field.
     * @param instance Object containing the field.
     * @param field Field for which the value is to be set.
     * @param value Value.
     */
    private void setFieldValue(final @NonNull Object instance, final @NonNull Field field, final @NonNull String value) throws AnnotationException
    {
        if (!field.canAccess(instance))
        {
            field.setAccessible(true);  // TODO Change this as it's forbidden! Use a public accessor instead!
        }

        try
        {
            field.set(instance, value);
        }
        catch (IllegalArgumentException e)
        {
            try
            {
                field.set(instance, Integer.valueOf(value));
            }
            catch (IllegalAccessException iae)
            {
                throw new AnnotationException(iae);
            }
        }
        catch (IllegalAccessException ex)
        {
            throw new AnnotationException(ex);
        }
    }

    /**
     * Retrieve the caller method.
     * @return Caller method.
     */
    private Method getCallerMethod()
    {
        Class<?> clazz;

        for (StackTraceElement trace : Thread.currentThread().getStackTrace())
        {
            try
            {
                clazz = Class.forName(trace.getClassName());

                Method method = checkCallerMethod(clazz, trace);
                if (method != null)
                {
                    return method;
                }
            }
            catch (ClassNotFoundException e)
            {
                // Do nothing, just process the next trace!
            }
        }

        return null; // No method found annotated with I18n annotation!
    }

    /**
     * Evaluate the method to check if it's the caller method.
     * @param clazz Class containing the method to evaluate.
     * @param trace Stack trace element containing the method name.
     * @return {@link Method} if it's the caller method, null otherwise.
     */
    private Method checkCallerMethod(final @NonNull Class<?> clazz, final @NonNull StackTraceElement trace)
    {
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        Method method;

        try
        {
            method = clazz.getMethod(trace.getMethodName());
            if (method.isAnnotationPresent(I18n.class))
            {
                return method;
            }
            else if (method.getDeclaringClass().isAssignableFrom(LocalizeEnumAware.class))
            {
                return method;
            }

            if (method.getName().startsWith("getI18n"))
            {
                // Have to find which method and class have invoked the localize service
                int index = whoCallLocalizeService(traces);
                StackTraceElement initiator = traces[index + 1];
                // Intend to use I18n functionality on a method but this method is not annotated!
                throw new AnnotationException(
                        String.format(
                                "A method calling the localize() service must be annotated with the I18n annotation! Such methods initiate a localize() request from class: '%s', method: '%s'",
                                initiator.getClassName(),
                                initiator.getMethodName()));
            }

        }
        catch (NoSuchMethodException | AnnotationException nsme)
        {
            // Maybe the method has a Locale parameter
            try
            {
                method = clazz.getMethod(trace.getMethodName(), Locale.class);

                // We found our caller.
                if (method.isAnnotationPresent(I18n.class))
                {
                    return method;
                }

                if (method.getName().startsWith("getI18n"))
                {
                    // Have to find which method and class called the localize() service
                    int index = whoCallLocalizeService(traces);
                    StackTraceElement initiator = traces[index + 1];

                    // Intend to use I18n functionality on a method but this method is not annotated!
                    throw new AnnotationException(
                            String.format(
                                    "A method calling the localize() service must be annotated with the I18n annotation! Such methods initiate a localize() request from class: '%s', method: '%s'",
                                    initiator.getClassName(),
                                    initiator.getMethodName()));
                }
            }
            catch (NoSuchMethodException | AnnotationException e)
            {
                // Do nothing, seems to be the wrong method!
            }
        }

        return null;
    }

    private int whoCallLocalizeService(final @NonNull StackTraceElement[] traces)
    {
        int index = 0;
        for (StackTraceElement element : traces)
        {
            if (!element.getMethodName().equals("localize")) // TODO Should be a class static field!
            {
                index += 1;
            }
            else
            {
                return index;
            }
        }

        return -1;
    }

    /**
     * Retrieve the given key from the given resource bundle path.
     * @param filePath Resource bundle path and name.
     * @param key Key.
     * @param locale Locale.
     * @return Value (localized)).
     */
    private String getKey(final @NonNull String filePath, final @NonNull String key, final @NonNull Locale locale) throws ResourceException
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
                    throw new ResourceException(
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
            LOGGER.warn(String.format("No resource bundle: '%s', language-tag: '%s', language: '%s' found! Use of the default language-tag: '%s', language: '%s' instead",
                    filePath, currentLocale, currentLocale.getDisplayLanguage(), Locale.getDefault().toLanguageTag(), Locale.getDefault().getDisplayLanguage()));
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

    @Synchronized
    public String translate(final @NonNull Translation text, final @NonNull Locale source, final @NonNull Locale target) throws TranslationException
    {
        return translationProcessor.translate(text,source,target);
    }
}
