package org.gaming.hub.business.usecase.base

import org.gaming.hub.business.exception.UseCaseException

internal sealed interface IBusinessUseCaseBase<IN, OUT> : IBusinessUseCase<IN, OUT> {
    @Throws(UseCaseException::class)
    fun executeBusiness(input: IN): OUT

    @Throws(UseCaseException::class)
    fun validateInput(input: IN)
}