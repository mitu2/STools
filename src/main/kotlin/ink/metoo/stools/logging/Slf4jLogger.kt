package ink.metoo.stools.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author chenmoand
 */

@LoggerDsl
inline fun <reified S : Any> (@LoggerDsl S).useSlf4jLogger(): Logger {
    return LoggerFactory.getLogger(S::class.java)!!
}

@LoggerDsl
fun (@LoggerDsl Logger).debug(block: () -> String?) = state(isDebugEnabled) { debug(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).info(block: () -> String?) = state(isInfoEnabled) { info(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).error(block: () -> String?) = state(isErrorEnabled) { error(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).trace(block: () -> String?) = state(isTraceEnabled) { trace(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).warn(block: () -> String?) = state(isWarnEnabled) { warn(block()) }

private inline fun state(condition: Boolean, block: () -> Unit) {
    if (condition) {
        block()
    }
}