package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.addCloseButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import okhttp3.internal.closeQuietly
import java.io.ByteArrayOutputStream


/**
 *
 * @author chenmoand
 */
@Route("barcode")
@PageTitle("条形码/二维码一键生成 - static.run")
@UIScope
@SpringComponent
class BarcodeView : KComposite() {

    private var content = ""
    private var format = BarcodeFormat.QR_CODE
    private var imageWith = 240
    private var imageHeight = 240

    private lateinit var result: Image

    private val contentErr = Notification("请输入内容后再点击制作", 3000, Notification.Position.TOP_CENTER)/*.addCloseButton()*/

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
                    onLeftClick { makeImage() }
                }
            }
            result = image {

            }
            add(contentErr)
        }

    }

    fun makeImage() {
        if(content.isBlank()) {
            contentErr.open()
            return
        }
        val writer = MultiFormatWriter()
        val bitMatrix = writer.encode(content, format, imageWith, imageHeight)
        val outputStream = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        result.setSrc(StreamResource("make.png", InputStreamFactory {
            return@InputStreamFactory outputStream.toByteArray().inputStream()
        }))
        result.width = "${imageHeight}px"
        result.height = "${imageWith}px"
        outputStream.closeQuietly()
    }

}