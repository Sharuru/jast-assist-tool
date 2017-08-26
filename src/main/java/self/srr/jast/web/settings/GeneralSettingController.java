package self.srr.jast.web.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import self.srr.jast.common.AstConstant;
import self.srr.jast.facade.SettingFacade;
import self.srr.jast.model.form.ProductivityRepoSettingForm;
import self.srr.jast.service.GitService;

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
        //model.addAttribute("repoSettingForm", adminFacade.getSettingForm());
        return "/page/settings/general";
    }




 /*   @RequestMapping(value = "/setting/repo", method = RequestMethod.POST)
    @ResponseBody
    RepoSettingResponse repoSetting(@Validated ProductivityRepoSettingForm repoSettingForm, BindingResult bindingResult) {
        RepoSettingResponse repoSettingResponse = new RepoSettingResponse();
        if (bindingResult.hasErrors()) {
            log.info("repoSettingForm has errors: " + repoSettingForm.toString());
            repoSettingResponse.setStatus(false);
            repoSettingResponse.setMessage("bingingResult.hasErrors()");
        } else {
            repoSettingResponse = adminFacade.saveRepoSettingResponse(repoSettingForm);
        }
        return repoSettingResponse;
    }

    @RequestMapping(value = "/repo/refresh", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse repoRefresh() {
        BaseResponse baseResponse = new BaseResponse();

        baseResponse = adminFacade.refreshRepo(adminFacade.getSettingForm());

        return baseResponse;

    }*/
}
