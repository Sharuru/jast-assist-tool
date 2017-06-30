package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import self.srr.jast.model.form.RepoSettingForm;

import java.io.File;
import java.io.IOException;

/**
 * Git service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class GitService {


    public void clone(RepoSettingForm repoSettingForm) throws GitAPIException, IOException {

        File localPath = new File(repoSettingForm.getRepoLocalPath().replace("\\", "\\\\"));

        FileUtils.deleteDirectory(localPath);

        log.info("Clone repo from: " + repoSettingForm.getRepoAddress() + " at branch: " + repoSettingForm.getRepoBranch() + " to: " + localPath);

        Git result = Git.cloneRepository()
                .setURI(repoSettingForm.getRepoAddress())
                .setBranch(repoSettingForm.getRepoBranch())
                .setDirectory(localPath)
                .call();

        result.close();
    }
}
