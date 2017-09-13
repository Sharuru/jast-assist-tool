package self.srr.jast.web.tools;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import self.srr.jast.common.AstConstant;
import self.srr.jast.facade.ProductivityFacade;
import self.srr.jast.facade.SettingFacade;
import self.srr.jast.model.form.ProductivitySettingForm;
import self.srr.jast.service.GitService;

/**
 * Tools controller
 */
@Controller
@Slf4j
@RequestMapping("/tools/productivity")
public class ProductivityToolController {

    @Autowired
    ProductivityFacade productivityFacade;

    @Autowired
    SettingFacade settingFacade;

    @RequestMapping(method = RequestMethod.GET)
    String index(Model model) {
        model.addAttribute("page", "tools");
        model.addAttribute("repoSettingForm", settingFacade.getSettingForm(AstConstant.SETTING_PROD_GIT, ProductivitySettingForm.class));
        return "/page/tools/productivity";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    String statistics(Model model) {
        //productivityFacade.calculateProductivity();
        return "GET";
    }
}
