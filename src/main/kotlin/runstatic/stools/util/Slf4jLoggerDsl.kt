package runstatic.stools.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import runstatic.stools.annoatation.LoggerDsl

/**
 *
 * @author chenmoand
 */

@LoggerDsl
inline fun <reified S : Any> (@LoggerDsl S).useSlf4jLogger(): Logger {
    return LoggerFactory.getLogger(S::class.java)!!
}

@LoggerDsl
fun (@LoggerDsl Logger).ifDebugEnabled(block: () -> String?) = state(isDebugEnabled) { debug(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).ifInfoEnabled(block: () -> String?) = state(isInfoEnabled) { info(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).ifErrorEnabled(block: () -> String?) = state(isErrorEnabled) { error(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).ifTraceEnabled(block: () -> String?) = state(isTraceEnabled) { trace(block()) }

@LoggerDsl
fun (@LoggerDsl Logger).ifWarnEnabled(block: () -> String?) = state(isWarnEnabled) { warn(block()) }

inline fun (@LoggerDsl Logger).state(condition: Boolean, block: @LoggerDsl Logger.() -> Unit) {
    if (condition) {
        block()
    }
}