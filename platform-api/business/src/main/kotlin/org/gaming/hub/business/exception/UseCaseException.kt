package org.gaming.hub.business.exception

import org.gaming.hub.business.exception.enumeration.UseCaseErrorCode
import org.gaming.hub.business.exception.enumeration.UseCaseExceptionStatus
import org.gaming.hub.business.exception.enumeration.UseCaseExceptionStatus.*

class UseCaseException private constructor(
    val status: UseCaseExceptionStatus,
    override val message: String,
    val code: Int,
) : RuntimeException(message) {
    internal companion object {
        fun validationError(code: UseCaseErrorCode, vararg messageParams: Any?): Nothing =
            throw UseCaseException(
                status = VALIDATION,
                message = code.getExceptionMessage(*messageParams),
                code = code.number
            )

        fun notFoundError(code: UseCaseErrorCode, vararg messageParams: Any?): Nothing =
            throw UseCaseException(
                status = NOT_FOUND,
                message = code.getExceptionMessage(*messageParams),
                code = code.number
            )

        fun internalError(code: UseCaseErrorCode, vararg messageParams: Any?): Nothing =
            throw UseCaseException(
                status = INTERNAL,
                message = code.getExceptionMessage(*messageParams),
                code = code.number
            )
    }
}