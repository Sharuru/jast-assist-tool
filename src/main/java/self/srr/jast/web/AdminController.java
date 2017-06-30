package self.srr.jast.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.model.form.RepoSettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.service.GitService;
import self.srr.jast.service.SettingService;

/**
 * Admin controller
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SettingService settingService;

    @Autowired
    GitService gitService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String admin(Model model) {
        model.addAttribute("page", "admin");
        RepoSettingForm repoSettingForm = settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class);
        repoSettingForm = repoSettingForm == null ? new RepoSettingForm() : repoSettingForm;
        model.addAttribute("repoSettingForm", repoSettingForm);
        return "admin";
    }

    @RequestMapping(value = "/setting/repo", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse repoSetting(@Validated RepoSettingForm repoSettingForm, BindingResult bindingResult) {
        BaseResponse baseResponse = new BaseResponse();
        if (bindingResult.hasErrors()) {
            log.info("repoSettingForm has errors: " + repoSettingForm.toString());
            baseResponse.setStatus(false);
            baseResponse.setMessage("bingingResult.hasErrors()");
        } else {
            repoSettingForm = repoSettingForm.trim();
            if (repoSettingForm.getRepoBranch().isEmpty()) {
                repoSettingForm.setRepoBranch(TracerConstant.DEFAULT_BRANCH);
            }
            try {
                settingService.saveConfig(TracerConstant.SETTING_GROUP_GIT, repoSettingForm);
                baseResponse.setStatus(true);
            } catch (Exception e) {
                log.error("repoSetting has errors: " + e.getMessage());
                e.printStackTrace();
                baseResponse.setStatus(false);
                baseResponse.setMessage(e.getMessage());
            }
        }
        return baseResponse;
    }

    @RequestMapping(value = "/repo/refresh", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse repoRefresh() {
        BaseResponse baseResponse = new BaseResponse();
        try {
            gitService.clone(settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class));
            baseResponse.setStatus(true);
        } catch (Exception e) {
            log.error("repoRefresh has errors: " + e.getMessage());
            e.printStackTrace();
            baseResponse.setStatus(false);
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;

    }
}
