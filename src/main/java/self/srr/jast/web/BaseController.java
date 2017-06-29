package self.srr.jast.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller
 * <p>
 * Created by Sharuru on 2017/06/29.
 */
@Controller
@Slf4j
public class BaseController {

    @RequestMapping(value = "/")
    String index() {
        return "index";
    }

}
