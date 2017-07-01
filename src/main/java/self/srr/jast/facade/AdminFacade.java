package self.srr.jast.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.model.entity.TblTracerSetting;
import self.srr.jast.model.form.RepoSettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.model.response.RepoSettingResponse;
import self.srr.jast.service.GitService;
import self.srr.jast.service.SettingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin facade
 * <p>
 * Created by Sharuru on 2017/7/1 0001.
 */
@Service
@Slf4j
public class AdminFacade {

    @Autowired
    SettingService settingService;

    @Autowired
    GitService gitService;

    public RepoSettingForm getRepoSettingForm() {
        RepoSettingForm repoSettingForm = settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class);
        repoSettingForm = repoSettingForm == null ? new RepoSettingForm() : repoSettingForm;
        return repoSettingForm;
    }

    public RepoSettingResponse saveRepoSettingResponse(RepoSettingForm reposettingForm) {
        RepoSettingResponse repoSettingResponse = new RepoSettingResponse();

        reposettingForm = reposettingForm.trim();
        // setting default branch
        if (reposettingForm.getRepoBranch().isEmpty()) {
            reposettingForm.setRepoBranch(TracerConstant.DEFAULT_BRANCH);
        }
        // persist to database
        try {
            settingService.saveConfig(TracerConstant.SETTING_GROUP_GIT, reposettingForm);
            repoSettingResponse.setRepoAddress(reposettingForm.getRepoAddress());
            repoSettingResponse.setRepoBranch(reposettingForm.getRepoBranch());
            repoSettingResponse.setRepoLocalPath(reposettingForm.getRepoLocalPath());
        } catch (Exception e) {
            log.error("Error happened in `saveRepoSettingResponse`: " + e.getMessage());
            e.printStackTrace();
            repoSettingResponse.setStatus(false);
            repoSettingResponse.setMessage(e.getMessage());
        }

        return repoSettingResponse;
    }

    public BaseResponse refreshRepo(RepoSettingForm repoSettingForm) {
        BaseResponse baseResponse = new BaseResponse();

        List<String> fileNames = new ArrayList<>();

        try {
            // clone then refresh database
            gitService.cloneRepoToLocal(repoSettingForm);
            fileNames = gitService.getGitFileList(repoSettingForm.getRepoLocalPath(), repoSettingForm.getRepoBranch());
            for (String fileName : fileNames) {

            }

        } catch (Exception e) {
            log.error("Error happened in `refreshRepo`: " + e.getMessage());
            e.printStackTrace();
            baseResponse.setStatus(false);
            baseResponse.setMessage(e.getMessage());
        }


        return baseResponse;
    }


}
