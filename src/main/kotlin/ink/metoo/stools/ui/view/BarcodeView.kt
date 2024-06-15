package ink.metoo.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.tabs.TabsVariant
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.upload.receivers.MemoryBuffer
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.*
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ink.metoo.stools.configuration.SToolsProperties
import ink.metoo.stools.logging.useSlf4jLogger
import ink.metoo.stools.ui.stye.*
import ink.metoo.stools.ui.util.pageLayout
import ink.metoo.stools.ui.util.updateDOM
import java.awt.image.BufferedImage
import java.net.URL
import java.util.*
import javax.imageio.ImageIO


/**
 *
 * @author chenmoand
 */
@Route("barcode/:mode?")
@PageTitle("条形码&二维码一键生成 - metoo.ink")
@UIScope
@SpringComponent
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
    private var isUploadSourceImage = true
    private var urlSourceImage = ""
    private var bufferedImage: BufferedImage? = null

    private val encodeTab = Tab("Encode").css {
        pointerStyle()
    }
    private val decodeTab = Tab("Decode").css {
        pointerStyle()
    }
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
            tabs.selectedTab = if (Mode.ENCODE == mode) encodeTab else decodeTab
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

    private fun typeSelect(): Select<BarcodeFormat> = Select<BarcodeFormat>().apply {
        label = "类型"
        setItems(*BarcodeFormat.values())
        value = format
        addValueChangeListener { format = it.value }
    }

    private fun Div.decodePage() {
        formLayout {
            formStyle()
            val radioButtonGroup = radioButtonGroup<String>("图片资源类型") {
                setItems("Upload", "Input")
                value = "Upload"
                pointerStyle()
            }
            val imageBuffer = MemoryBuffer()
            val uploadSourceImage = upload(imageBuffer) {
                uploadStyle()
                // note: set 2M
                maxFileSize = 1024 * 1024 * 2
                setAcceptedFileTypes("image/*")
                addSucceededListener {
                    bufferedImage = ImageIO.read(imageBuffer.inputStream)
                }
                addFailedListener {
                    Notification.show(it.reason.message, 3000, Notification.Position.TOP_CENTER)
                }
                addFileRejectedListener {
                    Notification.show(it.errorMessage, 3000, Notification.Position.TOP_CENTER)
                }
                addFileRejectedListener {
                    Notification.show(it.errorMessage, 3000, Notification.Position.TOP_CENTER)
                }
            }
            val textAreaSourceImage = textArea("来源") {
                textAreaStyle().hideStyle()
                placeholder = "请输入一个Url或者base64"
                valueChangeMode = ValueChangeMode.ON_CHANGE
                addValueChangeListener {
                    urlSourceImage = it.value
                }
            }

            radioButtonGroup.addValueChangeListener {
                isUploadSourceImage = it.value == "Upload"
                if (isUploadSourceImage) {
                    uploadSourceImage.showStyle()
                    textAreaSourceImage.hideStyle()
                } else {
                    bufferedImage = null
                    uploadSourceImage.hideStyle()
                    textAreaSourceImage.showStyle()
                }
            }
            val result = textArea("结果") {
                textAreaStyle()
                isReadOnly = true
            }
            button("解码") {
                pointerStyle()
                addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                onLeftClick {
                    decodeBarcode(result)
                }
            }
        }
    }

    private fun getBufferedImageByUrlOrBase64(): BufferedImage? {
        if (urlSourceImage.isBlank()) {
            return null
        }
        if (urlSourceImage.startsWith("http")) {
            return try {
                ImageIO.read(URL(urlSourceImage))
            } catch (e: Throwable) {
                logger.debug(e.message, e)
                null
            }
        }
        return try {
            ImageIO.read(
                Base64.getDecoder().decode(urlSourceImage.replace("data:image/(jpeg|png|gif);base64,".toRegex(), ""))
                    .inputStream()
            )
        } catch (e: Throwable) {
            logger.debug(e.message, e)
            null
        }
    }


    private fun decodeBarcode(result: TextArea) {
        val formatReader = MultiFormatReader()
        val localBufferedImage = if (isUploadSourceImage) bufferedImage else getBufferedImageByUrlOrBase64()
        if (localBufferedImage == null) {
            result.value = "❌图片错误"
            return
        }
        val binaryBitmap = BinaryBitmap(HybridBinarizer(BufferedImageLuminanceSource(localBufferedImage)))
        val decodeResult = formatReader.decode(binaryBitmap, mapOf(DecodeHintType.CHARACTER_SET to "UTF-8"))
        result.value = decodeResult.text
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
            add(typeSelect())
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
                onLeftClick { encodeBarcode() }
            }
        }
        div {
            marginZeroStyle().textAlignCenterStyle()
            result = image {
                resultImageStyle()
            }
        }
    }


    fun encodeBarcode() {
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
        private val logger = BarcodeView.useSlf4jLogger()
        private const val PAGE_MAX_WIDTH = "400px"

        private fun FlexLayout.mainStyle() = css {
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setFlexDirection(FlexLayout.FlexDirection.COLUMN)
            alignContent = FlexLayout.ContentAlignment.CENTER
            alignItems = FlexComponent.Alignment.BASELINE
        }

        private fun Tabs.tabsStyle() = css {
            width = PAGE_MAX_WIDTH
            addThemeVariants(TabsVariant.LUMO_CENTERED)
        }

        private fun KFormLayout.formStyle() = css {
            width = PAGE_MAX_WIDTH
            marginZeroStyle()
        }

        private fun HasStyle.uploadStyle() = css {
            style["margin-top"] = "10px"
        }

        private fun TextArea.textAreaStyle() = css {
            minHeight = "150px"
            maxHeight = "150px"
            marginZeroStyle()
            setWidthFull()
        }

        private fun Image.resultImageStyle() = css {
            maxHeight = "400px"
            maxWidth = PAGE_MAX_WIDTH
        }

    }

}