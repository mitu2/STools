package runstatic.stools.exception.support

import org.springframework.http.HttpStatus

/**
 *
 * @author chenmoand
 */

@Throws(TerminateExecutionException::class)
fun terminate(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    callback: TerminateExecutionResult.() -> Unit = {}
): Nothing {
    val result = TerminateExecutionResult()
    callback(result)
    throw TerminateExecutionException(result, status)
}
