package self.srr.jast.web.tools;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import self.srr.jast.service.GitService;

/**
 * Tools controller
 */
@Controller
@Slf4j
@RequestMapping("/tools/productivity")
public class ProductivityToolController {

    @Autowired
    GitService gitService;

    @RequestMapping(method = RequestMethod.GET)
    String index(Model model){
        model.addAttribute("page", "tools");

        return "/page/tools/productivity";

    }

    @RequestMapping(value="/statistics",method = RequestMethod.POST)
    @ResponseBody
    String statistics(Model model){
        return "GET";
    }
}
