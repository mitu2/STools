package runstatic.stools.util

import org.springframework.http.HttpStatus
import runstatic.stools.exception.support.TerminateExecutionException
import runstatic.stools.exception.support.TerminateExecutionResult

/**
 *
 * @author chenmoand
 */
private val EMPTY_CALLBACK: TerminateExecutionResult.() -> Unit = {}

@Throws(TerminateExecutionException::class)
fun <R> terminate(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    callback: TerminateExecutionResult.() -> Unit = EMPTY_CALLBACK
): R {
    val result = TerminateExecutionResult()
    callback(result)
    throw TerminateExecutionException(result, status)
}
