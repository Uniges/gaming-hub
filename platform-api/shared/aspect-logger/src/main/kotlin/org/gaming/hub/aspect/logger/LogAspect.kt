package org.gaming.hub.org.gaming.hub.aspect.logger

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAspect {

    private val logger = LoggerFactory.getLogger(LogAspect::class.java)

    private companion object {
        const val EXECUTING_MESSAGE = "%s executing with: %s"
        const val VOID_EXECUTED = "%s executed"
        const val EXECUTED_WITH_RESULT = "%s executed with: %s"
    }

    @Before("@annotation(LogFun)")
    fun logMethodExecution(joinPoint: JoinPoint) {
        logger.info(String.format(EXECUTING_MESSAGE, getFullMethodName(joinPoint), getArgs(joinPoint)))
    }

    @AfterReturning(pointcut = "@annotation(LogFun)", returning = "result")
    fun logMethodResult(joinPoint: JoinPoint, result: Any?) {
        if (result == null) {
            logger.info(String.format(VOID_EXECUTED, getFullMethodName(joinPoint)))
        } else {
            logger.info(String.format(EXECUTED_WITH_RESULT, getFullMethodName(joinPoint), result))
        }
    }

    private fun getFullMethodName(joinPoint: JoinPoint): String =
        "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"

    private fun getArgs(joinPoint: JoinPoint): String =
        joinPoint.args.joinToString { it?.toString().orEmpty() }
}