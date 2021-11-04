package runstatic.stools.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author chenmoand
 */

inline fun <reified S: Any> S.useSlf4jLogger(): Logger {
    return LoggerFactory.getLogger(S::class.java)!!;
}

fun Logger.ifDebugEnabled(callback: Logger.() -> Unit) {
    if(isDebugEnabled) {
        callback(this)
    }
}

fun Logger.ifInfoEnabled(callback: Logger.() -> Unit) {
    if(isInfoEnabled) {
        callback(this)
    }
}

fun Logger.isErrorEnabled(callback: Logger.() -> Unit) {
    if(isErrorEnabled) {
        callback(this)
    }
}


fun Logger.ifTraceEnabled(callback: Logger.() -> Unit) {
    if(isTraceEnabled) {
        callback(this)
    }
}

fun Logger.ifWarnEnabled(callback: Logger.() -> Unit) {
    if(isWarnEnabled) {
        callback(this)
    }
}

