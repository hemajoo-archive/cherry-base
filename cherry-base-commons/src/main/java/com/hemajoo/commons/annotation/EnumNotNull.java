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

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Annotation used to validate enum fields and check they are not null.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumNotNull.Validator.class)
public @interface EnumNotNull
{
    /**
     * Message used when the validation of the value has failed.
     * @return Message.
     */
    String message() default "{com.hemajoo.constraints.validation.EnumNotNull.message}";

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
     * Validator of the {@link EnumNotNull} annotation.
     * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
     * @version 1.0.0
     */
    class Validator implements ConstraintValidator<EnumNotNull, Enum<?>>
    {
        /**
         * Enumeration class.
         */
        private Class<? extends Enum<?>> enumClass;

        @Override
        public void initialize(EnumNotNull enumValue)
        {
            enumClass = enumValue.enumClass();
        }

        @Override
        public boolean isValid(Enum<?> value, ConstraintValidatorContext context)
        {
            if (enumClass == null)
            {
                return true;
            }

            if (value != null)
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

            return false;
        }
    }
}
