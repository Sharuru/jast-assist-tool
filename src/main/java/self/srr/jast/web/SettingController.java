package self.srr.jast.web;

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
 * Admin controller
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Controller
@Slf4j
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    SettingFacade settingFacade;

    @Autowired
    GitService gitService;

    @RequestMapping(value = "/general", method = RequestMethod.GET)
    String general(Model model) {
        model.addAttribute("page", "setting");
        //model.addAttribute("repoSettingForm", adminFacade.getSettingForm());
        return "/page/setting/general";
    }

    @RequestMapping(value = "/productivity", method = RequestMethod.GET)
    String productivity(Model model) {
        model.addAttribute("page", "setting");
        model.addAttribute("repoSettingForm", settingFacade.getSettingForm(AstConstant.SETTING_PROD_GIT, ProductivityRepoSettingForm.class));
        return "/page/setting/productivity";
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
