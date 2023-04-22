package com.smartcompany.smartschool.common;


import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public final class APIException extends RuntimeException {

    private final ApiError apiError;

    public APIException(final ApiError apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }

    public static APIException badRequest(final String message, final Object... args) {
        return new APIException(
                new ApiError(HttpStatus.BAD_REQUEST, MessageFormat.format(message, args))
        );
    }

    public static APIException notFound(final String message, final Object... args) {
        return new APIException(
                new ApiError(HttpStatus.NOT_FOUND, MessageFormat.format(message, args))
        );
    }

    public static APIException conflict(final String message, final Object... args) {
        return new APIException(
                new ApiError(HttpStatus.CONFLICT, MessageFormat.format(message, args))
        );
    }

    public static APIException internalError(final String message, final Object... args) {

        return new APIException(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MessageFormat.format(message, args))
        );
    }

    public static APIException unauthorized(final String message, final Object... args) {

        return new APIException(
                new ApiError(HttpStatus.UNAUTHORIZED, MessageFormat.format(message, args))
        );
    }

    public static APIException customCode(final String errorCode, final String message, final ResponseType responseType, Object object, final Object... args) {

        return new APIException(
                new ApiError(errorCode, responseType, MessageFormat.format(message, args), object)
        );
    }

    public ApiError apiError() {
        return this.apiError;
    }

    @Override
    public String toString() {
        return "APIException{"
                + "apiError=" + apiError
                + '}';
    }

}

