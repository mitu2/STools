package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.google.zxing.BarcodeFormat
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.tabs.TabsVariant
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MemoryBuffer
import com.vaadin.flow.router.*
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.ui.stye.css
import runstatic.stools.ui.stye.hideStyle
import runstatic.stools.ui.stye.pointerStyle
import runstatic.stools.ui.stye.showStyle
import runstatic.stools.ui.util.pageLayout
import runstatic.stools.ui.util.updateDOM


/**
 *
 * @author chenmoand
 */
@Route("barcode/:mode?")
@PageTitle("条形码/二维码一键生成 - static.run")
@UIScope
@SpringComponent
@JsModule("./lib/copytoclipboard.js")
class BarcodeView @Autowired constructor(
    private val properties: SToolsProperties
) : KComposite(), BeforeEnterObserver {

    private var content = ""
    private var format = BarcodeFormat.QR_CODE
    private var imageWith: Int = 240
    private var imageHeight: Int = 240
    private var contentDiv: Div = Div()
    private lateinit var result: Image
    private var mode: Mode = Mode.ENCODE
    private val encodeTab = Tab("Encode")
    private val decodeTab = Tab("Decode")
    private val tabs = Tabs().apply {
        tabsStyle()
        add(encodeTab)
        add(decodeTab)
        addSelectedChangeListener {
            if (it.selectedTab != null) {
                ui.ifPresent { currentUi ->
                    currentUi.navigate(
                        BarcodeView::class.java,
                        RouteParameters(RouteParam("mode", it.selectedTab.label.lowercase()))
                    )
                }
            }
        }
    }

    private val imageBuffer = MemoryBuffer()


    enum class Mode {
        DECODE, ENCODE;

        companion object {
            fun parseMode(txt: String) = valueOf(txt.uppercase())
        }

    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        try {
            val modeParam = event.routeParameters.get("mode").orElse(Mode.ENCODE.name)
            mode = Mode.parseMode(modeParam)
            tabs.selectedTab = if(Mode.ENCODE == mode) encodeTab else decodeTab
            contentDiv.updateDOM {
                if (Mode.ENCODE == mode) {
                    encodePage()
                } else {
                    decodePage()
                }
            }
        } catch (e: Throwable) {
            event.forwardTo(BarcodeView::class.java, RouteParameters(RouteParam("mode", "encode")))
        }
    }



    init {
        ui {
            pageLayout {
                flexLayout {
                    mainStyle()
                    add(tabs)
                    contentDiv.run {
                        encodePage()
                    }
                    add(contentDiv)
                }
            }
        }
    }




    private fun Div.decodePage() {
        formLayout {
            formStyle()
            val radioButtonGroup = radioButtonGroup<String>("Image Source Type") {
                setItems("Upload", "Input")
                value = "Upload"
            }
            val upload = upload(imageBuffer) {
                uploadStyle()
                setAcceptedFileTypes("image/*")
                addSucceededListener {
                    // TODO: 暂时先close
                    imageBuffer.inputStream.close()
                }
                addFailedListener {
                    Notification.show(it.reason.message, 3000, Notification.Position.TOP_CENTER)
                }

                addFileRejectedListener {
                    Notification.show(it.errorMessage, 3000, Notification.Position.TOP_CENTER)
                }
            }
            radioButtonGroup.addValueChangeListener {
                if(it.value == "Upload") {
                    upload.showStyle()
                } else {
                    upload.hideStyle()
                }
            }
            text("建设中")
        }
    }

    private fun Div.encodePage() {
        formLayout {
            formStyle()
            textField("内容") {
                value = content
                isRequired = true
                placeholder = "请输入任意字符或网址"
                addValueChangeListener { content = it.value }
            }
            select<BarcodeFormat>("类型") {
                setItems(*BarcodeFormat.values())
                value = format
                addValueChangeListener { format = it.value }
            }
            numberField("宽") {
                max = 4096.0
                min = 20.0
                value = imageWith.toDouble()
                addValueChangeListener { imageWith = it.value.toInt() }
            }
            numberField("高") {
                max = 4096.0
                min = 20.0
                value = imageHeight.toDouble()
                addValueChangeListener { imageHeight = it.value.toInt() }
            }
            button("制作") {
                pointerStyle()
                addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                onLeftClick { makeImage() }
            }
        }
        result = image {
            style["margin"] = "0 auto"
            onLeftClick {
                UI.getCurrent().page.executeJs("window.copyToClipboard($0)", src)
            }
        }
    }


    fun makeImage() {
        if (content.isBlank()) {
            Notification.show("请输入内容后再点击制作", 3000, Notification.Position.TOP_CENTER)
            return
        }
        if (imageWith <= 0 || imageHeight <= 0) {
            Notification.show("请输入正确的宽或高,范围是(20-4096)", 3000, Notification.Position.TOP_CENTER)
            return
        }
        result.src =
            "${properties.baseUrl}/api/barcode?text=${content}&width=${imageWith}&height=${imageHeight}&type=${format}"
        result.width = "${imageWith}px"
        result.height = "${imageHeight}px"
    }

    companion object {
        private fun FlexLayout.mainStyle() = css {
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setFlexDirection(FlexLayout.FlexDirection.COLUMN)
            alignContent = FlexLayout.ContentAlignment.CENTER
            alignItems = FlexComponent.Alignment.BASELINE
        }

        private fun Tabs.tabsStyle() = css {
            width = "400px"
            addThemeVariants(TabsVariant.LUMO_CENTERED)
        }

        private fun KFormLayout.formStyle() = css {
            width = "400px"
            style["margin"] = "0 auto"
        }

        private fun HasStyle.uploadStyle() {
            style["margin-top"] = "10px"
        }

    }

}