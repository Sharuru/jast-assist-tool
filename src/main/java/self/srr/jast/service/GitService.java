package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.stereotype.Service;
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

    public List<String> getGitFileList(String path, String ref) {

        List<String> fileList = new ArrayList<>();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        try {
            Repository repository = builder.setGitDir(new File(path + "\\.git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();

            log.info("Set repo at: " + repository.getDirectory());

            Ref head = repository.findRef(ref);
            log.info("Ref of " + ref + ": " + head + ": " + head.getName() + " - " + head.getObjectId().getName());

            RevWalk walk = new RevWalk(repository);
            RevCommit commit = walk.parseCommit(head.getObjectId());
            System.out.println("Commit: " + commit);

            RevTree tree = walk.parseTree(commit.getTree().getId());
            System.out.println("Found Tree: " + tree);

            TreeWalk treeWalk = new TreeWalk(repository);

            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);

            while (treeWalk.next()) {
                if (treeWalk.isSubtree()) {
                    log.info("Visiting dir: " + treeWalk.getPathString());
                    treeWalk.enterSubtree();
                } else {
                    fileList.add(treeWalk.getPathString());
                    log.info("Add file: " + treeWalk.getPathString());
                }
            }

            walk.dispose();
            repository.close();

        } catch (Exception e) {
            log.error("Error happened in `getGitFileList`: " + e.getMessage());
            e.printStackTrace();
        }

        return fileList;
    }
}
