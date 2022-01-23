package runstatic.stools.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import runstatic.stools.controller.ControllerPosition
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.service.exception.ServiceNotCompletedException
import runstatic.stools.service.exception.ServiceNotCompletedInfo
import javax.servlet.http.HttpServletRequest

/**
 *
 * @author chenmoand
 */
@ControllerAdvice(basePackageClasses = [ControllerPosition::class])
class GlobalExceptionControllerAdapter @Autowired constructor(
    private val environment: Environment
) {

    private val logger = useSlf4jLogger()

    @Autowired
    lateinit var request: HttpServletRequest

    @ExceptionHandler(ServiceNotCompletedException::class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ResponseBody
    fun serviceNotCompletedExceptionHandler(exception: ServiceNotCompletedException): ResponseEntity<ServiceNotCompletedInfo> {
        return ResponseEntity.status(exception.httpStatus).body(exception.result.copy(path = request.servletPath))
    }

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun otherExceptionHandler(exception: Throwable): ResponseEntity<ServiceNotCompletedInfo> {
        logger.error(exception.message, exception)

        val result = ServiceNotCompletedInfo(
            path = request.servletPath ?: ServiceNotCompletedInfo.DEFAULT_PATH,
            message = exception.message ?: ServiceNotCompletedInfo.DEFAULT_MESSAGE,

        )
        if (!environment.acceptsProfiles { it.test("online") }) {
            result.properties["exception"] = exception.javaClass.name
            result.properties["stacktrace"] = exception.stackTrace.map { it.toString() }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
    }

}