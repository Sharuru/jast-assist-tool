package self.srr.jast.facade;

import jdk.internal.instrumentation.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.model.form.RepoSettingForm;
import self.srr.jast.model.response.BaseResponse;
import self.srr.jast.service.SettingService;

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

    public RepoSettingForm getRepoSettingForm(){
        RepoSettingForm repoSettingForm = settingService.getConfig(TracerConstant.SETTING_GROUP_GIT, RepoSettingForm.class);
        repoSettingForm = repoSettingForm == null ? new RepoSettingForm() : repoSettingForm;
        return repoSettingForm;
    }


    public BaseResponse saveRepoSettingResponse(RepoSettingForm reposettingForm){
        BaseResponse baseResponse = new BaseResponse();

        reposettingForm = reposettingForm.trim();
        // setting default branch
        if(reposettingForm.getRepoBranch().isEmpty()){
            reposettingForm.setRepoBranch(TracerConstant.DEFAULT_BRANCH);
        }
        // persist to database
        try{
            settingService.saveConfig(TracerConstant.SETTING_GROUP_GIT, reposettingForm);
            baseResponse.setStatus(true);
        }catch (Exception e){
            log.error("Error happened in `saveRepoSettingResponse`: " + e.getMessage());
            e.printStackTrace();
            baseResponse.setStatus(false);
            baseResponse.setMessage(e.getMessage());
        }

        return baseResponse;
    }


}
