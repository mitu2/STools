package runstatic.stools.exception.support

import org.springframework.http.HttpStatus

/**
 *
 * @author chenmoand
 */

@Throws(TerminateExecutionException::class)
fun <R> terminate(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    callback: TerminateExecutionResult.() -> Unit = {}
): R {
    val result = TerminateExecutionResult()
    callback(result)
    throw TerminateExecutionException(result, status)
}
