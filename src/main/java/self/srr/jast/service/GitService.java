package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.stereotype.Service;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.form.ProductivitySettingForm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Git service
 */
@Service
@Slf4j
public class GitService {

    public void refreshLocalRepo(String localPath, String remotePath, String trackBranch) throws Exception {
        try {
            Git git = Git.open(new File(localPath + "\\.git"));
            git.close();
            pullRepoToLocal(localPath, remotePath, trackBranch);
        } catch (Exception e) {
            cloneRepoToLocal(localPath, remotePath, trackBranch);
        }
    }

    private void pullRepoToLocal(String localPath, String remotePath, String trackBranch) throws Exception {
        log.info("Starting pull repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath);

        Git git = Git.open(new File(localPath + "\\.git"));
        git.reset().setMode(ResetCommand.ResetType.HARD).call();
        git.pull().call();
        git.close();

        log.info("Pull repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath + "finished");
    }


    private void cloneRepoToLocal(String localPath, String remotePath, String trackBranch) throws Exception {
        log.info("Starting clone repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath);

        File localDir = new File(localPath);
        FileUtils.deleteDirectory(localDir);

        Git git = Git.cloneRepository()
                .setURI(remotePath)
                .setBranch(trackBranch)
                .setDirectory(localDir)
                .call();
        git.close();

        log.info("Clone repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localDir + "finished");
    }

    private Repository getRepository(String localPath) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        return builder.setGitDir(new File(localPath + "\\.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }

    public List<RevCommit> getLocalCommits(String localPath, String refMark, RevFilter filter) throws Exception {

        List<RevCommit> commits = new ArrayList<>();
        Repository repository = getRepository(localPath);
        RevWalk revWalk = new RevWalk(repository);
        Ref ref = repository.findRef(refMark);

        if (filter != null) {
            revWalk.setRevFilter(filter);
        }

        RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());
        revWalk.markStart(headCommit);

        ObjectId currentHead = headCommit.getTree();
        ObjectId previousHead = currentHead;

        for (RevCommit revCommit : revWalk) {

            currentHead = revCommit.getTree();

            try (ObjectReader reader = repository.newObjectReader()) {

                CanonicalTreeParser previousTree = new CanonicalTreeParser();
                previousTree.reset(reader, previousHead);
                CanonicalTreeParser currentTree = new CanonicalTreeParser();
                currentTree.reset(reader, currentHead);

                DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
                df.setRepository(repository);
                df.setDiffComparator(RawTextComparator.DEFAULT);
                df.setDetectRenames(true);

                List<DiffEntry> diffs = df.scan(currentHead, previousHead);

                log.info("File changed: " + diffs.size());
                for (DiffEntry diff : diffs) {
                    int lineAdded = 0;
                    int lineDeleted = 0;
                    for (Edit edit : df.toFileHeader(diff).toEditList()) {
                        lineDeleted += edit.getEndA() - edit.getBeginA();
                        lineAdded += edit.getEndB() - edit.getBeginB();
                    }
                    log.info("Del: " + lineDeleted + " Add: " + lineAdded);
                }

            }

            previousHead = currentHead;


            //log.info("Get commit: " + revCommit.getShortMessage() + " by " + revCommit.getCommitterIdent().getName() + " at " + new Date(revCommit.getCommitTime() * 1000L));
            //commits.add(revCommit);


        }


        return commits;
    }

}
