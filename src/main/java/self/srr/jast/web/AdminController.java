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
import self.srr.jast.facade.AdminFacade;
import self.srr.jast.model.form.RepoSettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.model.response.RepoSettingResponse;
import self.srr.jast.service.GitService;

import java.util.List;

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
    AdminFacade adminFacade;

    @Autowired
    GitService gitService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    String admin(Model model) {
        model.addAttribute("page", "admin");
        model.addAttribute("repoSettingForm", adminFacade.getRepoSettingForm());
        return "admin";
    }

    @RequestMapping(value = "/setting/repo", method = RequestMethod.POST)
    @ResponseBody
    RepoSettingResponse repoSetting(@Validated RepoSettingForm repoSettingForm, BindingResult bindingResult) {
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

        baseResponse = adminFacade.refreshRepo(adminFacade.getRepoSettingForm());
        //gitService.cloneRepoToLocal(settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class));
        //List<String> fileList = gitService.getGitFileList("D:\\JFTtest", "refs/heads/master");
        //baseResponse.setStatus(true);

        return baseResponse;

    }
}
