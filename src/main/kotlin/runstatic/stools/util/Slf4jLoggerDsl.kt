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
fun (@LoggerDsl Logger).ifDebugEnabled(callback: @LoggerDsl Logger.() -> Unit) = state(isDebugEnabled, callback)

@LoggerDsl
fun (@LoggerDsl Logger).ifInfoEnabled(callback: @LoggerDsl Logger.() -> Unit) = state(isInfoEnabled, callback)

@LoggerDsl
fun (@LoggerDsl Logger).isErrorEnabled(callback: @LoggerDsl Logger.() -> Unit) = state(isErrorEnabled, callback)

@LoggerDsl
fun (@LoggerDsl Logger).ifTraceEnabled(callback: @LoggerDsl Logger.() -> Unit) = state(isTraceEnabled, callback)

@LoggerDsl
fun (@LoggerDsl Logger).ifWarnEnabled(callback: @LoggerDsl Logger.() -> Unit) = state(isWarnEnabled, callback)

inline fun (@LoggerDsl Logger).state(bol: Boolean, callback: @LoggerDsl Logger.() -> Unit) {
    if (bol) {
        callback(this)
    }
}