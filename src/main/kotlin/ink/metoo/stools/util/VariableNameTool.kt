package ink.metoo.stools.util

/**
 *
 * @author chenmoand
 */
class VariableNameTool(keyword: String) {

    val bigCamelCase: String
    val littleCamelCase: String
    val allUpperCase: String
    val horizontalLineCase: String
    val underscoreCase: String


    init {
        if (keyword.isBlank()) {
            bigCamelCase = ""
            littleCamelCase = ""
            allUpperCase = ""
            horizontalLineCase = ""
            underscoreCase = ""
        } else {
            var letBigCamelCase = ""
            var letLittleCamelCase = ""
            val upperCaseList = mutableListOf<String>()
            val lowerCaseList = mutableListOf<String>()
            keyword
                .map {
                    return@map if (it.isUpperCase()) " $it" else "$it"
                }
                .joinToString("")
                .trim()
                .split(delimiters = charArrayOf('|', ' ', '_', '-'))
                .filter { it.isNotEmpty() }
                .forEachIndexed { index, it ->
                    letBigCamelCase += it[0].uppercase() + it.substring(1)
                    letLittleCamelCase += if (index == 0)
                        it[0].lowercase() + it.substring(1)
                    else
                        it[0].uppercase() + it.substring(1)
                    upperCaseList.add(it.uppercase())
                    lowerCaseList.add(it.lowercase())
                }
            bigCamelCase = letBigCamelCase
            littleCamelCase = letLittleCamelCase
            allUpperCase = upperCaseList.joinToString("_")
            horizontalLineCase = lowerCaseList.joinToString("-")
            underscoreCase = lowerCaseList.joinToString("_")
        }
    }


}