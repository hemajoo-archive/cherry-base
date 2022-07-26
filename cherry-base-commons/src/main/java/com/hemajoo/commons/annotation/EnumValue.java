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
package com.hemajoo.commons.annotation;

import lombok.SneakyThrows;
import org.assertj.core.util.Strings;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Annotation used to validate fields for which the value is coming from an enumeration.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue
{
    /**
     * Message used when the validation of the value has failed.
     * @return Message.
     */
    String message() default "{com.hemajoo.constraints.validation.EnumValueExcluded.message}";

    /**
     * Validation groups.
     * @return Validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     * @return Payload.
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Enumeration class from which the value to validate belongs to.
     * @return Enumeration class.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Enumeration method used to validate if the value.
     * @return Validation method name.
     */
    String enumMethod() default "";

    /**
     * Array of excluded values.
     * @return Excluded values.
     */
    String[] excluded() default {};

    /**
     * Validator of the {@link EnumValue} annotation.
     * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
     * @version 1.0.0
     */
    class Validator implements ConstraintValidator<EnumValue, Enum<?>>
    {
        /**
         * Enumeration class.
         */
        private Class<? extends Enum<?>> enumClass;

        /**
         * Validation method name.
         */
        private String enumMethod;

        /**
         * Excluded values.
         */
        private List<String> excluded;

        @Override
        public void initialize(EnumValue enumValue)
        {
            enumMethod = enumValue.enumMethod();
            enumClass = enumValue.enumClass();
            excluded = Arrays.asList(enumValue.excluded());
        }

        @SneakyThrows
        @Override
        public boolean isValid(Enum<?> value, ConstraintValidatorContext context)
        {
            boolean result = true;

            if (value == null || enumClass == null || enumMethod == null)
            {
                return true;
            }

            // Check for excluded values
            result = checkEnumExcludedValues(value, context);

            // Check for valid value
            if (result)
            {
                result = checkEnumValidValue(value, context);
            }

            return result;
        }

        /**
         * Check the enumerated value if it is not part of some excluded values.
         * @param value Enumerated value.
         * @param context Constraint validator context.
         * @return <b>True</b> if the value is valid, <b>false</b> otherwise.
         */
        private boolean checkEnumExcludedValues(final Enum<?> value, final ConstraintValidatorContext context)
        {
            Class<?> valueClass = value.getClass();

            if (valueClass.isEnum() && excluded.contains(value.name()))
            {
                // Filter the allowed values.
                List<String> includedValues = new ArrayList<>(Arrays.stream(enumClass.getEnumConstants())
                        .map(Enum::name)
                        .toList());
                includedValues.removeAll(excluded);

                // Inject message parameters
                ((ConstraintValidatorContextImpl) context).addMessageParameter("invalidValue", value);
                ((ConstraintValidatorContextImpl) context).addMessageParameter("validValues", includedValues);
                ((ConstraintValidatorContextImpl) context).addMessageParameter("enumClassName", enumClass.getSimpleName());

                return false;
            }

            return true;
        }

        /**
         * Check the enumerated value if it contains a valid value.
         * @param value Enumerated value.
         * @param context Constraint validator context.
         * @return <b>True</b> if the value is valid, <b>false</b> otherwise.
         */
        private boolean checkEnumValidValue(final Enum<?> value, final ConstraintValidatorContext context) throws EnumValueException
        {
            if (enumClass != null && enumMethod != null)
            {
                try
                {
                    Method method = enumClass.getMethod(enumMethod, value.getClass());

                    if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType()))
                    {
                        throw new EnumValueException(Strings.formatIfArgs("Method: '%s' return type is not of type boolean in class: '%s'!", enumMethod, enumClass));
                    }

                    if (!Modifier.isStatic(method.getModifiers()))
                    {
                        throw new EnumValueException(Strings.formatIfArgs("Method: '%s' is not a static method in class: '%s'!", enumMethod, enumClass));
                    }

                    Boolean invocation = (Boolean) method.invoke(null, value);

                    ((ConstraintValidatorContextImpl) context).addMessageParameter("enumClassName", enumClass.getSimpleName());

                    // Inject message parameters
                    String values = Arrays.stream(enumClass.getEnumConstants())
                            .map(Enum::name)
                            .collect(Collectors.joining(", "));
                    ((ConstraintValidatorContextImpl) context).addMessageParameter("validValues", values);

                    return invocation != null && invocation;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                {
                    throw new EnumValueException(e);
                }
                catch (NoSuchMethodException | SecurityException e)
                {
                    throw new EnumValueException(Strings.formatIfArgs("The method: '%s(%s)' does not exist in class: '%s'", enumMethod, value.getClass(), enumClass), e);
                }
            }

            return true;
        }
    }
}
