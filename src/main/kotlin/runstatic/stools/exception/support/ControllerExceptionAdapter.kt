package runstatic.stools.exception.support

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import runstatic.stools.controller.ControllerPosition
import runstatic.stools.logging.useSlf4jLogger
import javax.servlet.http.HttpServletRequest

/**
 *
 * @author chenmoand
 */
@ControllerAdvice(basePackageClasses = [ControllerPosition::class])
class ControllerExceptionAdapter {

    private val logger = useSlf4jLogger()

    @Autowired
    lateinit var request: HttpServletRequest

    @ExceptionHandler(TerminateExecutionException::class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ResponseBody
    fun terminateExecutionExceptionHandler(exception: TerminateExecutionException): ResponseEntity<TerminateExecutionResult> =
        ResponseEntity
            .status(exception.httpStatus)
            .body(exception.result)

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun otherExceptionHandler(exception: Throwable): ResponseEntity<TerminateExecutionResult> {
        logger.error(exception.message, exception)
        val result = TerminateExecutionResult(
            path = request.servletPath ?: TerminateExecutionResult.DEFAULT_PATH,
            message = exception.message ?: TerminateExecutionResult.DEFAULT_MESSAGE
        )
        result.properties["exception"] = exception.javaClass.name
        result.properties["stackTrace"] = exception.stackTrace.map { it.toString() }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(result)
    }

}