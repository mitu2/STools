package ink.metoo.stools.service.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import ink.metoo.stools.constant.TerminateCode
import ink.metoo.stools.entity.support.BaseEntity
import java.time.LocalDateTime

/**
 *
 * @author chenmoand
 */
class ServiceNotCompletedException(
    val result: ServiceNotCompletedInfo = ServiceNotCompletedInfo(),
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : RuntimeException()


data class ServiceNotCompletedInfo(
    var code: Int = TerminateCode.NOT_STATED,
    var path: String = DEFAULT_PATH,
    var timestamp: LocalDateTime = LocalDateTime.now(),
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var message: String = DEFAULT_MESSAGE
) : BaseEntity() {

    companion object {
        const val DEFAULT_MESSAGE = "Unexplained message"
        const val DEFAULT_PATH = "Unexplained path"
    }

}

@Throws(ServiceNotCompletedException::class)
fun terminate(
    httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    block: ServiceNotCompletedInfo.() -> Unit = {}
): Nothing {
    val result = ServiceNotCompletedInfo(code = httpStatus.value(), message = httpStatus.reasonPhrase)
    result.block()
    throw ServiceNotCompletedException(result, httpStatus)
}