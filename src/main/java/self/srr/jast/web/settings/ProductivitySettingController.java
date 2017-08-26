package self.srr.jast.web.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import self.srr.jast.common.AstConstant;
import self.srr.jast.facade.SettingFacade;
import self.srr.jast.model.form.ProductivitySettingForm;


@Controller
@Slf4j
@RequestMapping("/settings/productivity")
public class ProductivitySettingController {

    @Autowired
    SettingFacade settingFacade;

    @RequestMapping(method = RequestMethod.GET)
    String index(Model model) {
        model.addAttribute("page", "settings");
        model.addAttribute("repoSettingForm", settingFacade.getSettingForm(AstConstant.SETTING_PROD_GIT, ProductivitySettingForm.class));
        return "/page/settings/productivity";
    }
}
