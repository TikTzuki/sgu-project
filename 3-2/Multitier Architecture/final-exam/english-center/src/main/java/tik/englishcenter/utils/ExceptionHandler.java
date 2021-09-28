package tik.englishcenter.utils;

import org.springframework.stereotype.Component;
import tik.englishcenter.models.Examinee;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Component
public class ExceptionHandler {

    public void handle(Set<ConstraintViolation<Object>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            new JOptionPane(
                    constraintViolations.stream().map(ConstraintViolation::getMessage)
            ).setVisible(true);
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
