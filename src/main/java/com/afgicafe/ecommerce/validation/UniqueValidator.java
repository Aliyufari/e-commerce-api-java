package com.afgicafe.ecommerce.validation;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;

    private Class<?> entity;
    private boolean ignoreCurrent;
    private Field fieldName;
    private String idField;

    @Override
    public void initialize(Unique annotation) {
        this.entity = annotation.entity();
        this.ignoreCurrent = annotation.ignoreCurrent();
        this.fieldName = findFieldName(entity, annotation);
        this.idField = annotation.idField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            Object id = null;

            if (ignoreCurrent) {
                HibernateConstraintValidatorContext hibernateContext =
                        context.unwrap(HibernateConstraintValidatorContext.class);

                Object rootBean = hibernateContext.getConstraintValidatorPayload(Object.class);

                if (rootBean != null) {
                    Field declaredField = findField(rootBean.getClass(), idField);
                    declaredField.setAccessible(true);
                    id = declaredField.get(rootBean);
                }
            }

            String jpql = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                    entity.getSimpleName(), fieldName);

            if (ignoreCurrent && id != null) {
                jpql += String.format(" AND e.%s != :id", idField);
            }

            var query = entityManager.createQuery(jpql, Long.class)
                    .setParameter("value", value);

            if (ignoreCurrent && id != null) {
                query.setParameter("id", id);
            }

            return query.getSingleResult() == 0;

        } catch (Exception e) {
            return false;
        }
    }

    private Field findFieldName(Class<?> entity, Unique annotation) {
        for (Field field : entity.getDeclaredFields()){
            if (field.isAnnotationPresent(Unique.class)){
                Unique foundField = field.getAnnotation(Unique.class);
                if (foundField.equals(annotation)) return  field;
            }
        }
        return  null;
    }

    private Field findField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null) {
                return findField(cls.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
}