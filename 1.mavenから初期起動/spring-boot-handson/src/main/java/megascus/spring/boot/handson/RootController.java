
package megascus.spring.boot.handson;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author megascus
 */
@RestController
public class RootController {
    
    @RequestMapping("/")
    public String get() {
        return "hello world";
    }
}
