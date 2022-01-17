package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.util.inputRight
import runstatic.stools.util.pageLayout
import runstatic.stools.util.pointer


/**
 *
 * @author chenmoand
 */
@Route("chenmoand")
@CssImport("./css/chenmoand.css")
@PageTitle("Author Chenmoand - static.run")
@SpringComponent
@UIScope
class ChenmoandView : KComposite() {


    private val root = ui {
        pageLayout {
            addClassName("welcome")
            h3("你好, Hello World!")
            p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
            formLayout {
                width = "40%"
                style["margin"] = "auto"
                responsiveSteps = listOf(
                    FormLayout.ResponsiveStep("0", 1)
                )
                for (fieldState in FINAL_TEXT_FIELD_STATES) {
                    textField(fieldState.label) {
                        value = fieldState.value
                        isReadOnly = true
                        suffixComponent = button("Open", VaadinIcon.EYE.create()) {
                            pointer()
                            inputRight()
                            onLeftClick { open(fieldState.url) }
                        }
                    }
                }
                textField("QQ Group") {
                    value = "558504614"
                    isReadOnly = true
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


    data class TextFieldState(var label: String, var value: String, var url: String = value)

    companion object {

        val FINAL_TEXT_FIELD_STATES = listOf(
            TextFieldState("Github", "https://github.com/mitu2"),
            TextFieldState("Blog", "https://blog.static.run"),
            TextFieldState("Email", "chenmoand@outlook.com", "mailto:chenmoand@outlook.com")
        )

    }

}