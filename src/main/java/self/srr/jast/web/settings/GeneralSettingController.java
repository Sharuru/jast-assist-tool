package self.srr.jast.web.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import self.srr.jast.facade.SettingFacade;

/**
 * Settings controller
 */
@Controller
@Slf4j
@RequestMapping("/settings/general")
public class GeneralSettingController {

    @Autowired
    SettingFacade settingFacade;

    @RequestMapping(method = RequestMethod.GET)
    String index(Model model) {
        model.addAttribute("page", "settings");
        return "/page/settings/general";
    }
}
