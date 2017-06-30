package self.srr.jast.web;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.form.RepoSettingForm;
import self.srr.jast.service.GitService;
import self.srr.jast.service.SettingService;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/repo", method = RequestMethod.POST)
    @ResponseBody
    Boolean repoSetting(@Validated RepoSettingForm repoSettingForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("repoSettingForm has errors: " + repoSettingForm.toString());
            return false;
        } else {
            if (repoSettingForm.getRepoBranch().trim().isEmpty()) {
                repoSettingForm.setRepoBranch(TracerConstant.DEFAULT_BRANCH);
            }
            return settingService.saveConfig(TracerConstant.SETTING_GROUP_GIT, repoSettingForm);
        }
    }

    @RequestMapping(value = "/repo/refresh", method = RequestMethod.POST)
    @ResponseBody
    Boolean repoRefresh() {
        try {
            gitService.clone(settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
