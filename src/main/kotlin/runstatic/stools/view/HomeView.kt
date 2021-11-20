package runstatic.stools.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import org.springframework.stereotype.Controller
import javax.annotation.security.PermitAll


/**
 *
 * @author chenmoand
 */
@Route("")
@PageTitle("Home - static.run")
@CssImport("css/Home.css")
//@PWA(name = "Home - static.run", shortName = "Home")
@Controller
class HomeView : AppLayout() {


    init {
        isDrawerOpened = true
        navbar {
            drawerToggle()
            h3("Beverage Buddy")
        }
        drawer {
            div {
            }
            div {
            }
        }
    }


}