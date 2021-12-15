package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.service.ShortUrlService
import runstatic.stools.ui.component.PageFooter
import runstatic.stools.util.pointer
import runstatic.stools.util.prop

/**
 *
 * @author chenmoand
 */
@Route("short-url")
@PageTitle("短网址一键生成 - static.run")
@UIScope
@SpringComponent
@CssImport("./css/shortUrl.css")
class ShortUrlView(
    private val shortUrlService: ShortUrlService
) : KComposite() {

    // private val logger = useSlf4jLogger()

    private val protocolSelect = Select<String>().apply {
        setItems(*PROTOCOLS)
        addClassName("protocol")
    }

    private val urlField: TextField = TextField("网址").apply {
        isRequired = true
        placeholder = "请在此输入一个的网址"
        isClearButtonVisible = true
        isAutoselect = true
        prefixComponent = FlexLayout().apply {
            add(protocolSelect)
        }
    }

    private val resultField: TextField = TextField("结果").apply {
        isReadOnly = true
        value = "https://example.org"
        suffixComponent = Button("复制").apply {
            pointer()
            addClassName("copy-button")
        }
    }


    private var protocol by protocolSelect.prop(DEFAULT_PROTOCOL)
    private var url by urlField.prop("")

    private val root = ui {
        flexLayout {
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setFlexDirection(FlexLayout.FlexDirection.COLUMN)
            alignContent = FlexLayout.ContentAlignment.CENTER
            alignItems = FlexComponent.Alignment.BASELINE
            formLayout {
                width = "400px"
                style["margin"] = "0 auto"
                add(urlField)
                add(resultField)
                button("制作") {
                    pointer()
                    addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                    onLeftClick {

                    }
                }
            }
            add(PageFooter())
        }
    }

    init {
        // add select inner style
        root.element.executeJs(
            """document.querySelector('.protocol')
                .shadowRoot
                .querySelector('vaadin-select-text-field')
                .style['padding'] = '0px'
            """.trimIndent()
        )
    }

    companion object {

        @Suppress("HttpUrlsUsage")
        val PROTOCOLS = arrayOf("http://", "https://")

        @Suppress("HttpUrlsUsage")
        const val DEFAULT_PROTOCOL = "http://"
    }

}