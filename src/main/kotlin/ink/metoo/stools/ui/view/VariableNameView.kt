package ink.metoo.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ink.metoo.stools.ui.stye.css
import ink.metoo.stools.ui.stye.marginZeroStyle
import ink.metoo.stools.ui.util.pageLayout
import ink.metoo.stools.util.VariableNameTool

/**
 *
 * @author chenmoand
 */
@Route("variable-name-util")
@PageTitle("变量名工具 - metoo.ink")
@SpringComponent
@UIScope
class VariableNameView @Autowired constructor(

) : KComposite() {


    private val bigCamelCaseField = createTextField("大驼峰")
    private val littleCamelCaseField = createTextField("小驼峰")
    private val allUpperCaseField = createTextField("常量")
    private val horizontalLineCaseField = createTextField("`-`分割")
    private val underscoreCaseField = createTextField("`_`分割")

    private fun createTextField(label: String) = TextField(label).apply {
        isReadOnly = true
        isAutoselect = true
    }

    private val root = ui {
        pageLayout {
            flexLayout {
                mainStyle()
                h3("变量名格式化工具")
                formLayout {
                    contentStyle()
                    textField("VariableName") {
                        isRequired = true
                        placeholder = "请输入一个单词或者句子"
                        addValueChangeListener {
                            formatVariableName(it.value)
                        }
                    }
                    add(bigCamelCaseField)
                    add(littleCamelCaseField)
                    add(allUpperCaseField)
                    add(horizontalLineCaseField)
                    add(underscoreCaseField)
                }
            }
        }
    }

    private fun formatVariableName(value: String?) {
        if (value.isNullOrBlank()) {
            return
        }
        VariableNameTool(value).apply {
            bigCamelCaseField.value = bigCamelCase
            littleCamelCaseField.value = littleCamelCase
            allUpperCaseField.value = allUpperCase
            horizontalLineCaseField.value = horizontalLineCase
            underscoreCaseField.value = underscoreCase
        }
    }

    companion object {
        private fun FlexLayout.mainStyle() = css {
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setFlexDirection(FlexLayout.FlexDirection.COLUMN)
            alignContent = FlexLayout.ContentAlignment.CENTER
            alignItems = FlexComponent.Alignment.BASELINE
        }

        private fun KFormLayout.contentStyle() = css {
            width = "400px"
            marginZeroStyle()
        }

    }
}