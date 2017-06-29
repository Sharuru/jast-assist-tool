package self.srr.jast.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sharuru on 2017/06/29.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/")
    String index() {
        return "index";
    }

    @RequestMapping(value = "/login")
    String login() {
        return "login";
    }

}
