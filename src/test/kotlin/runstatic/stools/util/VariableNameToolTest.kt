package runstatic.stools.util

import org.junit.jupiter.api.Test

/**
 *
 * @author chenmoand
 */
internal class VariableNameToolTest {

    @Test
    fun testField() {
        val variableNameTool = VariableNameTool("I like java and kotlin")
        println(
            """
             bigCamelCase:   ${variableNameTool.bigCamelCase} 
             littleCamelCase:    ${variableNameTool.littleCamelCase}
             allUpperCase:    ${variableNameTool.allUpperCase}
             horizontalLineCase:    ${variableNameTool.horizontalLineCase}
             underscoreCase:    ${variableNameTool.underscoreCase}
            """.trimIndent()
        )
    }


}