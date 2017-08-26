package self.srr.jast.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller
 */
@Controller
@Slf4j
public class BaseController {

    @RequestMapping(value = "/")
    String index() {
        return "index";
    }

    @RequestMapping(value = "403")
    String accessDenied(Model model) {
        model.addAttribute("sysmsg", "您没有权限访问这个页面。");
        return "fragments/redirect";
    }

}
