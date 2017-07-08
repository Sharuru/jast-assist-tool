package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.stereotype.Service;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.form.RepoSettingForm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Git service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class GitService {


    public void refreshLocalRepo(RepoSettingForm repoSettingForm) throws Exception {

        try {
            Git git = Git.open(new File(repoSettingForm.getRepoLocalPath() + "\\.git"));
            git.close();
            pullRepoToLocal(repoSettingForm);
        } catch (Exception e) {
            cloneRepoToLocal(repoSettingForm);
        }
    }

    private void pullRepoToLocal(RepoSettingForm repoSettingForm) throws Exception {

        Git git = Git.open(new File(repoSettingForm.getRepoLocalPath() + "\\.git"));
        git.reset().setMode(ResetCommand.ResetType.HARD).call();
        git.pull().call();
        log.info("Pull repo from: " + repoSettingForm.getRepoAddress() + " at branch: " + repoSettingForm.getRepoBranch() + " to: " + repoSettingForm.getRepoLocalPath());
        git.close();
    }


    private void cloneRepoToLocal(RepoSettingForm repoSettingForm) throws Exception {

        File localPath = new File(repoSettingForm.getRepoLocalPath());

        FileUtils.deleteDirectory(localPath);

        log.info("Clone repo from: " + repoSettingForm.getRepoAddress() + " at branch: " + repoSettingForm.getRepoBranch() + " to: " + localPath);

        Git git = Git.cloneRepository()
                .setURI(repoSettingForm.getRepoAddress())
                .setBranch(repoSettingForm.getRepoBranch())
                .setDirectory(localPath)
                .call();

        git.close();
    }

    public List<GitFile> getGitFilePathList(String path, String refMark) throws Exception {

        List<GitFile> fileList = new ArrayList<>();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        Repository repository = builder.setGitDir(new File(path + "\\.git"))
                .readEnvironment()
                .findGitDir()
                .build();

        log.info("Set repo at: " + repository.getDirectory());

        Ref ref = repository.findRef(refMark);

        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(ref.getObjectId());
        log.info("Ref commit id is: " + commit);

        RevTree tree = walk.parseTree(commit.getTree().getId());
        log.info("Ref commit's tree id is: " + tree);

        TreeWalk treeWalk = new TreeWalk(repository);

        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);

        log.info("Making file list start.");
        while (treeWalk.next()) {
            if (treeWalk.isSubtree()) {
                log.info("Visiting dir: " + treeWalk.getPathString());
                treeWalk.enterSubtree();
            } else {
                GitFile gitFile = new GitFile();

                String filePath = treeWalk.getPathString();
                int fileNameHead = filePath.lastIndexOf("/");
                fileNameHead = fileNameHead == -1 ? 0 : fileNameHead + 1;

                gitFile.setFileName(filePath.substring(fileNameHead));
                gitFile.setFilePath(filePath);
                gitFile.setRevisionId(commit.getId().getName());
                fileList.add(gitFile);
                log.info("Add file: " + treeWalk.getPathString());
            }
        }
        log.info("Making file list complete.");

        walk.dispose();
        repository.close();

        return fileList;
    }
}
