package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import self.srr.jast.form.RepoSettingForm;

import java.io.File;

/**
 * Git service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class GitService {


    public void clone(RepoSettingForm repoSettingForm) throws GitAPIException {

        File localPath = new File(repoSettingForm.getRepoLocalPath().replace("\\", "\\\\"));

        localPath.delete();

        Git result = Git.cloneRepository()
                .setURI(repoSettingForm.getRepoAddress())
                .setBranch(repoSettingForm.getRepoBranch())
                .setDirectory(localPath)
                .call();

        result.close();
    }
}
