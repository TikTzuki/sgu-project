package tik.englishcenterstudent.services;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class OneOfValidator implements ConstraintValidator<OneOf, Object> {

    private String[] fields;

    @Override
    public void initialize(OneOf annotation) {
        this.fields = annotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        int matches = countNumberOfMatches(wrapper);

        if (matches > 1) {
            setValidationErrorMessage(context, "one.of.too.many.matches.message");
            return false;
        } else if (matches == 0) {
            setValidationErrorMessage(context, "one.of.no.matches.message");
            return false;
        }

        return true;
    }

    private int countNumberOfMatches(BeanWrapper wrapper) {
        int matches = 0;
        for (String field : fields) {
            Object value = wrapper.getPropertyValue(field);
            boolean isPresent = detectOptionalValue(value);

            if (value != null && isPresent) {
                matches++;
            }
        }
        return matches;
    }

    private boolean detectOptionalValue(Object value) {
        if (value instanceof Optional) {
            return ((Optional) value).isPresent();
        }
        return true;
    }

    private void setValidationErrorMessage(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate("{" + template + "}")
                .addConstraintViolation();
    }

}