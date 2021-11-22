package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.google.zxing.BarcodeFormat
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope


/**
 *
 * @author chenmoand
 */
@Route("barcode")
@PageTitle("二维码生成工具 - static.run")
@UIScope
@SpringComponent
class BarcodeView : KComposite() {

    private var content = ""
    private var format = BarcodeFormat.QR_CODE
    private var imageWith = 24
    private var imageHeight = 24


    private val root = ui {
        verticalLayout  {
            formLayout {
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
            }
//            image {
//
//            }
        }
    }

}