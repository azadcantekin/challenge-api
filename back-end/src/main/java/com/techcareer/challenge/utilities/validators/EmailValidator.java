package com.techcareer.challenge.utilities.validators;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserModel u WHERE u.email = :email");
        query.setParameter("email", email);
        Integer count = (Integer) query.getSingleResult();
        return count == 0;
    }
}
