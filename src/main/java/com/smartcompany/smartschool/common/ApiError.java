package com.smartcompany.smartschool.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String code;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;
    private List<ApiSubError> errors;

    @Enumerated(EnumType.STRING)
    private ResponseType responseType;

    private Object object;
    private String objectName;

    public ApiError() {
    }

    public ApiError(HttpStatus status) {
        this();
        this.httpStatus = status;
        this.code = String.valueOf(status.value());
        this.message = status.getReasonPhrase();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.httpStatus = status;
        this.code = String.valueOf(status.value());
        this.message = ex.getLocalizedMessage();
    }


    public ApiError(HttpStatus status, String message) {
        this();
        this.httpStatus = status;
        this.code = String.valueOf(status.value());
        this.message = message;
    }

    public ApiError(String errorCode, ResponseType responseType, String message) {
        this();
        this.responseType = responseType;
        this.httpStatus = HttpStatus.OK;
        this.code = String.valueOf(errorCode);
        this.message = message;
    }

    public ApiError(String errorCode, ResponseType responseType, String message, Object object) {
        this();
        this.responseType = responseType;
        this.httpStatus = HttpStatus.OK;
        this.code = String.valueOf(errorCode);
        this.message = message;
        this.object = object;
        if (object != null && object.getClass() != null) {
            this.objectName = object.getClass().getSimpleName();
        }

    }

    private void addSubError(ApiSubError subError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(subError);
    }

    private void addValidationError(String field, String message) {
        addSubError(new ApiValidationError(field, message));
    }


    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getField(),
                fieldError.getDefaultMessage());
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a
     *
     * @param cv the ConstraintViolation
     * @Validated validation fails.
     */


    abstract class ApiSubError {

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    class ApiValidationError extends ApiSubError {

        private String field;
        private String message;

        ApiValidationError(String message) {
            this.message = message;
        }
    }
}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object value) {
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
