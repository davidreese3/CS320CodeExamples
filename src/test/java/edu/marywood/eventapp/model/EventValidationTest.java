package edu.marywood.eventapp.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EventValidationTest{

    @Test
    void blankNameAndDateAreInvalid() {
        // Arrange
        Event event = new Event(
                "",
                "",
                "An event without required information"
        );

        try (ValidatorFactory factory =
                     Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            // Act
            Set<ConstraintViolation<Event>> violations =
                    validator.validate(event);

            // Assert
            assertEquals(2, violations.size());
        }
    }
}
