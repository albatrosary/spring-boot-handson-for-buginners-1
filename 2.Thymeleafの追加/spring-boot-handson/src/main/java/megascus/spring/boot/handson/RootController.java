
package megascus.spring.boot.handson;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author megascus
 */
//@RestController
@Controller
public class RootController {
    
    @RequestMapping("/")
    public String get() {
        // return "hello world";
        return "index"; //resources/templates/index.htmlファイルの中身を参照しに行く
    }
}
