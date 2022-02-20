package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired

@Route("web-doc")
@PageTitle("WebDoc配置 - static.run")
@SpringComponent
@UIScope
class WebDocConfigView @Autowired constructor(

) : KComposite() {


}