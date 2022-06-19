package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.util.VariableNameTool
import runstatic.stools.util.pageLayout

/**
 *
 * @author chenmoand
 */
@Route("variable-name-util")
@PageTitle("变量名工具 - static.run")
@SpringComponent
@UIScope
class VariableNameView @Autowired constructor(

) : KComposite() {



    private val bigCamelCaseField = TextField("大驼峰").apply {
        isReadOnly = true
    }

    private val littleCamelCaseField = TextField("小驼峰").apply {
        isReadOnly = true
    }

    private val allUpperCaseField = TextField("常量").apply {
        isReadOnly = true
    }

    private val horizontalLineCaseField = TextField("`-`分割").apply {
        isReadOnly = true
    }

    private val underscoreCaseField = TextField("`_`分割").apply {
        isReadOnly = true
    }

    private val root = ui {
        pageLayout {
            flexLayout {
                flexWrap = FlexLayout.FlexWrap.WRAP
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                setFlexDirection(FlexLayout.FlexDirection.COLUMN)
                alignContent = FlexLayout.ContentAlignment.CENTER
                alignItems = FlexComponent.Alignment.BASELINE
                h3("变量名格式化工具")
                formLayout {
                    width = "400px"
                    style["margin"] = "0 auto"
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
        if(value.isNullOrBlank()) {
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


}