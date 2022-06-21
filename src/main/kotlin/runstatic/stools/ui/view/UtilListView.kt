package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.ui.entity.TextFieldState
import runstatic.stools.util.inputRight
import runstatic.stools.util.pageLayout
import runstatic.stools.util.pointer

/**
 *
 * @author chenmoand
 */
@Route("util-list")
@PageTitle("工具列表 - static.run")
@CssImport("./css/util-list.css")
@SpringComponent
@UIScope
class UtilListView @Autowired constructor() : KComposite() {

    private val root = ui {
        pageLayout {
            addClassName("app")
            h3("哇卡卡卡的小工具")
            p("希望能帮到你哦!")
            formLayout {
                className = "url-list"
                responsiveSteps = listOf(
                    FormLayout.ResponsiveStep("0", 1)
                )
                for (fieldState in FINAL_TEXT_FIELD_STATES) {
                    textField(fieldState.label) {
                        value = fieldState.value
                        isReadOnly = true
                        suffixComponent = button("See", VaadinIcon.EYE.create()) {
                            pointer()
                            inputRight()
                            onLeftClick { open(fieldState.url) }
                        }
                    }
                }
            }
        }
    }

    fun open(url: String, type: String = "_blank") {
        root.ui.ifPresent { ui ->
            ui.access {
                ui.page.open(url, type)
            }
        }
    }

    companion object {
        val FINAL_TEXT_FIELD_STATES = listOf(
            TextFieldState("二维码工具", "https://static.run/ui/barcode"),
            TextFieldState("JSON格式化工具", "https://static.run/ui/json-format-util"),
            TextFieldState("短网址工具", "https://static.run/ui/short-url"),
            TextFieldState("Doc工具", "https://static.run/ui/web-doc-parse"),
            TextFieldState("变量名工具", "https://static.run/ui/variable-name-util")
        )
    }

}