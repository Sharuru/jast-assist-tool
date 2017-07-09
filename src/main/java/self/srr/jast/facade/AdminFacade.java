package self.srr.jast.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.form.RepoSettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.model.response.RepoSettingResponse;
import self.srr.jast.service.GitService;
import self.srr.jast.service.SettingService;
import self.srr.jast.service.TracerService;

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

    @Autowired
    TracerService tracerService;

    public RepoSettingForm getRepoSettingForm() {
        RepoSettingForm repoSettingForm = settingService.getSetting(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class);
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
            settingService.saveSetting(TracerConstant.SETTING_GROUP_GIT, reposettingForm);
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
