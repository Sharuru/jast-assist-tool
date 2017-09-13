package self.srr.jast.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.jast.common.AstConstant;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.form.BaseForm;
import self.srr.jast.model.form.ProductivitySettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.model.response.RepoSettingResponse;
import self.srr.jast.service.GitService;
import self.srr.jast.service.SettingService;
import self.srr.jast.service.TracerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Setting facade
 */
@Component
@Slf4j
public class SettingFacade {

    @Autowired
    SettingService settingService;

    @Autowired
    GitService gitService;

    @Autowired
    TracerService tracerService;

    public <T> T getSettingForm(String settingGroup, Class<T> formType) {
        T settingForm = settingService.getSetting(settingGroup, formType);

        try {
            settingForm = settingForm == null ? formType.newInstance() : settingForm;
        } catch (Exception e) {
            log.error("Error happened in `getSettingForm`: " + e.getMessage());
            e.printStackTrace();
            settingForm = (T) new BaseForm();
        }
        return settingForm;
    }

    public RepoSettingResponse saveRepoSettingResponse(ProductivitySettingForm repoSettingForm) {
        RepoSettingResponse repoSettingResponse = new RepoSettingResponse();

        repoSettingForm = repoSettingForm.trim();
        // setting default branch
        if (repoSettingForm.getRepoBranch().isEmpty()) {
            repoSettingForm.setRepoBranch(AstConstant.DEFAULT_BRANCH);
        }
        // persist to database
        try {
            settingService.saveSetting(AstConstant.SETTING_PROD_GIT, repoSettingForm);
            repoSettingResponse.setRepoAddress(repoSettingForm.getRepoAddress());
            repoSettingResponse.setRepoBranch(repoSettingForm.getRepoBranch());
            repoSettingResponse.setRepoLocalPath(repoSettingForm.getRepoLocalPath());
        } catch (Exception e) {
            log.error("Error happened in `saveRepoSettingResponse`: " + e.getMessage());
            e.printStackTrace();
            repoSettingResponse.setStatus(false);
            repoSettingResponse.setMessage(e.getMessage());
        }

        return repoSettingResponse;
    }

    public BaseResponse refreshRepo(ProductivitySettingForm repoSettingForm) {
        BaseResponse baseResponse = new BaseResponse();

        List<GitFile> gitFiles = new ArrayList<>();

        try {
            // refresh local repo
            gitService.refreshLocalRepo(repoSettingForm);
            gitFiles = gitService.getGitFilePathList(repoSettingForm.getRepoLocalPath(), repoSettingForm.getRepoBranch());

            for (GitFile file : gitFiles) {
                if (!tracerService.isFileInTrack(file.getFilePath())) {
                    // add to track
                    tracerService.addFileToTrackQueue(file);
                }
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
