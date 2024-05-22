package org.gaming.hub.business.usecase.base

import org.gaming.hub.business.exception.UseCaseException
import org.gaming.hub.org.gaming.hub.aspect.logger.LogFun
import org.springframework.transaction.annotation.Transactional

sealed interface IBusinessUseCase<IN, OUT> {
    @LogFun
    @Transactional
    @Throws(UseCaseException::class)
    fun execute(input: IN): OUT
}