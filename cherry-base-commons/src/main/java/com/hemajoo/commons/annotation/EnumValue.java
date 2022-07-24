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

        @Override
        public boolean isValid(Enum<?> value, ConstraintValidatorContext context)
        {
            if (value == null)
            {
                return true;
            }

            if (enumClass == null || enumMethod == null)
            {
                return true;
            }

            // Add some message parameters
            ((ConstraintValidatorContextImpl) context).addMessageParameter("invalidValue", value);
            ((ConstraintValidatorContextImpl) context).addMessageParameter("enumClassName", enumClass.getSimpleName());

            String values = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            ((ConstraintValidatorContextImpl) context).addMessageParameter("validValues", values);

            Class<?> valueClass = value.getClass();

            if (valueClass.isEnum())
            {
                // Check if the value is an excluded one?
                if (excluded.contains(value.name()))
                {
                    // Filter the allowed values.
                    List<String> includedValues = new ArrayList<>(Arrays.stream(enumClass.getEnumConstants())
                            .map(Enum::name)
                            .toList());
                    includedValues.removeAll(excluded);

                    // Inject message parameter(s).
                    ((ConstraintValidatorContextImpl) context).addMessageParameter("validValues", includedValues);

                    return false;
                }
            }
            else
            {
                try
                {
                    Method method = enumClass.getMethod(enumMethod, valueClass);
                    if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType()))
                    {
                        throw new RuntimeException(Strings.formatIfArgs("%s method return is not boolean type in the %s class", enumMethod, enumClass));
                    }

                    if (!Modifier.isStatic(method.getModifiers()))
                    {
                        throw new RuntimeException(Strings.formatIfArgs("%s method is not static method in the %s class", enumMethod, enumClass));
                    }

                    Boolean result = (Boolean)method.invoke(null, value);

                    return result != null && result;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                {
                    throw new RuntimeException(e);
                }
                catch (NoSuchMethodException | SecurityException e)
                {
                    throw new RuntimeException(Strings.formatIfArgs("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
                }
            }

            return true;
        }
    }
}
