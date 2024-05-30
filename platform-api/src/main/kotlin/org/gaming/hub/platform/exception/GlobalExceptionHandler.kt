package org.gaming.hub.platform.exception

import org.gaming.hub.business.exception.UseCaseException
import org.gaming.hub.business.exception.enumeration.UseCaseExceptionStatus
import org.gaming.hub.platform.model.response.ErrorResponse
import org.gaming.hub.platform.model.response.SimpleErrorResponse
import org.gaming.hub.token.manager.exception.TokenAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UseCaseException::class)
    fun handleUseCaseException(ex: UseCaseException): ResponseEntity<ErrorResponse> {
        val status = when (ex.status) {
            UseCaseExceptionStatus.VALIDATION -> HttpStatus.BAD_REQUEST
            UseCaseExceptionStatus.NOT_FOUND -> HttpStatus.NOT_FOUND
            UseCaseExceptionStatus.ALREADY_EXISTS -> HttpStatus.CONFLICT
            UseCaseExceptionStatus.INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val errorResponse = ErrorResponse(
            code = ex.code,
            message = ex.message,
            status = status.value()
        )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(TokenAccessException::class)
    fun handleTokenAccessException(ex: TokenAccessException): ResponseEntity<SimpleErrorResponse> {
        val errorResponse = SimpleErrorResponse(
            message = ex.message,
            status = HttpStatus.UNAUTHORIZED.value()
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(AuthenticateException::class)
    fun handleAuthenticateException(ex: AuthenticateException): ResponseEntity<SimpleErrorResponse> {
        val errorResponse = SimpleErrorResponse(
            message = ex.message,
            status = HttpStatus.UNAUTHORIZED.value()
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}