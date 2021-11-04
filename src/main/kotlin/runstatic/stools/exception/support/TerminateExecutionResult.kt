package runstatic.stools.exception.support

import runstatic.stools.constant.TerminateCode
import runstatic.stools.entity.support.BaseEntity
import java.time.LocalDateTime

data class TerminateExecutionResult(
    var code: Int = TerminateCode.NOT_STATED,
    var message: String = DEFAULT_MESSAGE,
    var path: String = DEFAULT_PATH,
    var timestamp: LocalDateTime = LocalDateTime.now()
) : BaseEntity() {

    companion object {
        const val DEFAULT_MESSAGE = "Unexplained message"
        const val DEFAULT_PATH = "Unexplained path"
    }

}