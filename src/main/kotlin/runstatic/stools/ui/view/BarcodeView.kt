package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.google.zxing.BarcodeFormat
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.util.pageLayout
import runstatic.stools.util.pointer


/**
 *
 * @author chenmoand
 */
@Route("barcode")
@PageTitle("条形码/二维码一键生成 - static.run")
@UIScope
@SpringComponent
class BarcodeView @Autowired constructor(
    private val properties: SToolsProperties
) : KComposite() {

    private var content = ""
    private var format = BarcodeFormat.QR_CODE
    private var imageWith: Int = 240
    private var imageHeight: Int = 240

    private lateinit var result: Image

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
                        pointer()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick { makeImage() }
                    }
                }
                result = image {
                    style["margin"] = "0 auto"
                }
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
        result.width = "${imageHeight}px"
        result.height = "${imageWith}px"
    }

}