package runstatic.stools.exception.support

import org.springframework.http.HttpStatus

/**
 *
 * @author chenmoand
 */
class TerminateExecutionException(
    val result: TerminateExecutionResult = TerminateExecutionResult(),
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : RuntimeException()