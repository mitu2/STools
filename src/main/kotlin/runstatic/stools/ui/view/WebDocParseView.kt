package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.util.VaadinProp
import runstatic.stools.util.inputRight
import runstatic.stools.util.pageLayout
import runstatic.stools.util.pointer
import java.util.LinkedList

/**
 *
 * @author chenmoand
 */
@Route("web-doc-parse")
@PageTitle("web-doc url生成工具 - static.run")
@SpringComponent
@UIScope
class WebDocParseView @Autowired constructor(
    private val properties: SToolsProperties
) : KComposite() {

    private val logger = useSlf4jLogger()

    private val repositoryKeys = LinkedList(properties.webDocResources.keys)

    private val repositorySelect = Select<String>().apply {
        setItems(repositoryKeys)
        label = "仓库"
    }

    private val groupIdInput = TextField("group").apply {
        isRequired = true
        placeholder = "please input groupId"
        isClearButtonVisible = true
        isAutoselect = true
    }

    private val artifactIdInput = TextField("artifactId").apply {
        isRequired = true
        placeholder = "please input artifactId"
        isClearButtonVisible = true
        isAutoselect = true
    }

    private val versionSelect = Select<String>().apply {
        label = "version"
        setItems(mutableListOf(LATEST_VERSION))
    }

    private val resultField: TextField = TextField("结果地址").apply {
        isReadOnly = true
        suffixComponent = Button("打开", VaadinIcon.OPTION.create()).apply {
            pointer()
            inputRight()
            addClickListener {
                if(value.isNotBlank()) {
                    UI.getCurrent().page.executeJs("window.open($0, '_blank')", value)
                }
            }
        }
    }


    private var repository by VaadinProp(repositoryKeys[0], repositorySelect)
    private var groupId by VaadinProp("", groupIdInput)
    private var artifactId by VaadinProp("", artifactIdInput)
    private var version by VaadinProp(LATEST_VERSION, versionSelect)
    private var result by VaadinProp("", resultField)



    private val root = ui {
        pageLayout {
            flexLayout {
                flexWrap = FlexLayout.FlexWrap.WRAP
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                setFlexDirection(FlexLayout.FlexDirection.COLUMN)
                alignContent = FlexLayout.ContentAlignment.CENTER
                alignItems = FlexComponent.Alignment.BASELINE
                formLayout {
                    width = "400px"
                    style["margin"] = "0 auto"
                    add(repositorySelect)
                    add(groupIdInput)
                    add(artifactIdInput)
                    add(versionSelect)
                    add(resultField)
                    button("生成") {
                        pointer()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            makeWebDocUrl()
                        }
                    }
                }
            }
        }
    }

    private fun makeWebDocUrl() {
        var letResult = "${properties.baseUrl}/web-doc/${repository}/${groupId}:${artifactId}"
        if(version != LATEST_VERSION) {
            letResult += ":$version"
        }
        result = letResult
    }


    companion object {
        const val LATEST_VERSION = "latest"
    }


}