package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.stereotype.Service;
import self.srr.jast.model.CommitterModel;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Git service
 */
@Service
@Slf4j
public class GitService {

    /**
     * refresh local git repo by clone/pull with reset hard
     *
     * @param localPath   local repo location
     * @param remotePath  remote repo location
     * @param trackBranch tracking branch name
     * @throws Exception exception
     */
    public void refreshLocalRepo(String localPath, String remotePath, String trackBranch) throws Exception {
        try {
            Git git = Git.open(new File(localPath + "\\.git"));
            git.close();
            pullRepoToLocal(localPath, remotePath, trackBranch);
        } catch (Exception e) {
            // local git repo not exist, clone
            cloneRepoToLocal(localPath, remotePath, trackBranch);
        }
    }

    /**
     * pull repo from remote
     *
     * @param localPath   local repo location
     * @param remotePath  remote repo location
     * @param trackBranch tracking branch name
     * @throws Exception exception
     */
    private void pullRepoToLocal(String localPath, String remotePath, String trackBranch) throws Exception {
        Git git = Git.open(new File(localPath + "\\.git"));

        if (!trackBranch.equals(git.getRepository().getBranch())) {
            log.info("Track branch not same! " + "Local: " + git.getRepository().getBranch() + " Track: " + trackBranch);
            throw new Exception();
        }

        log.info("Starting pull repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath + " at branch: " + git.getRepository().getBranch());

        git.reset().setMode(ResetCommand.ResetType.HARD).call();
        git.pull().call();
        git.close();

        log.info("Pull repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath + "finished");
    }

    /**
     * clone repo from remote
     *
     * @param localPath   local repo location
     * @param remotePath  remote repo location
     * @param trackBranch tracking branch name
     * @throws Exception exception
     */
    private void cloneRepoToLocal(String localPath, String remotePath, String trackBranch) throws Exception {
        File localDir = new File(localPath);
        FileUtils.deleteDirectory(localDir);

        log.info("Starting clone repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localPath);

        Git git = Git.cloneRepository()
                .setURI(remotePath)
                .setBranch(trackBranch)
                .setDirectory(localDir)
                .call();
        git.close();

        log.info("Clone repo from: " + remotePath + " at branch: " + trackBranch + " to: " + localDir + "finished");
    }

    /**
     * get local repo
     *
     * @param localPath local repo location
     * @return repository object
     * @throws IOException exception
     */
    private Repository getRepository(String localPath) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        return builder.setGitDir(new File(localPath + "\\.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }

    /**
     * visit local repo commits
     *
     * @param localPath local repo location
     * @param refMark   visit refs(branch name)
     * @param filter    visit filter(since, until, etc.)
     * @return visited commit object list
     * @throws Exception exception
     */
    public List<RevCommit> getLocalCommits(String localPath, String refMark, RevFilter filter) throws Exception {

        List<RevCommit> commits = new ArrayList<>();
        Repository repository = getRepository(localPath);
        RevWalk revWalk = new RevWalk(repository);
        Ref ref = repository.findRef(refMark);

        if (filter != null) {
            revWalk.setRevFilter(filter);
        }

        // get head commit
        RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());
        revWalk.markStart(headCommit);

        for (RevCommit revCommit : revWalk) {
            log.info("Get commit: " + revCommit.getShortMessage() + " by " + revCommit.getAuthorIdent().getName() + " at " + new Date(revCommit.getCommitTime() * 1000L));
            commits.add(revCommit);
        }

        return commits;
    }

    // TODO refactor
    public Map<String, CommitterModel> geneCommitterMap(String localPath, List<RevCommit> commits) throws IOException {

        Map<String, CommitterModel> comMap = new HashMap<>();

        Repository repository = getRepository(localPath);

        ObjectId currentHead;
        ObjectId previousHead;

        for (int i = 0; i < commits.size(); i++) {

            // TODO merged node check
            log.info("Processing commit: " + commits.get(i).getParentCount() + "xxxxxxxx " + ((commits.get(i).getParentCount() == 1) ? "" : "X - ") + commits.get(i).getShortMessage() + " by " + commits.get(i).getCommitterIdent().getName() + " at " + new Date(commits.get(i).getCommitTime() * 1000L));

            String comMapKey = commits.get(i).getCommitterIdent().getName() + "#" + commits.get(i).getCommitterIdent().getEmailAddress();

            // TODO last record will be thrown
            currentHead = commits.get(i).getTree();
            if (commits.get(i).getParentCount() != 0) {
                previousHead = commits.get(i).getParent(0).getTree();
            } else {
                previousHead = currentHead;
            }


            try (ObjectReader reader = repository.newObjectReader()) {

                CanonicalTreeParser previousTree = new CanonicalTreeParser();
                previousTree.reset(reader, previousHead);
                CanonicalTreeParser currentTree = new CanonicalTreeParser();
                currentTree.reset(reader, currentHead);

                DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
                df.setRepository(repository);
                df.setDiffComparator(RawTextComparator.DEFAULT);
                df.setDetectRenames(true);

                List<DiffEntry> diffs = df.scan(previousHead, currentHead);

                log.info("File changed: " + diffs.size());

                int lineAdded = 0;
                int lineDeleted = 0;
                List<String> touchedFile = new ArrayList<>();

                for (DiffEntry diff : diffs) {
                    for (Edit edit : df.toFileHeader(diff).toEditList()) {
                        if (diff.getNewPath().endsWith(".csv") || diff.getNewPath().endsWith(".xml")) {
                            log.info("Fuck you!" + diff.getNewPath());
                        } else {
                            lineDeleted += edit.getEndA() - edit.getBeginA();
                            lineAdded += edit.getEndB() - edit.getBeginB();
                            if (diff.getChangeType().equals(DiffEntry.ChangeType.ADD) || diff.getChangeType().equals(DiffEntry.ChangeType.MODIFY)) {
                                if (!touchedFile.contains(diff.getNewPath())) {
                                    if (!diff.getNewPath().endsWith(".csv") && !diff.getNewPath().endsWith(".xml")) {
                                        touchedFile.add(diff.getNewPath());
                                    }
                                } else {
                                    log.info("Contained!!!!!!!!!!!!!11");
                                }
                            }
                        }

                    }
                }

                log.info("Del: " + lineDeleted + " Add: " + lineAdded);

                // TODO commits.get(i).getParentCount() == 1 skipped merged node

                if (comMap.get(comMapKey) == null && commits.get(i).getParentCount() == 1) {
                    // new
                    CommitterModel model = new CommitterModel();
                    model.setCommitterName(commits.get(i).getAuthorIdent().getName());
                    model.setCommitterEmail(commits.get(i).getAuthorIdent().getEmailAddress());
                    model.setFileChanged(touchedFile.size());
                    model.setLinesAdded(lineAdded);
                    model.setLinesDeleted(lineDeleted);
                    comMap.put(comMapKey, model);
                } else if (commits.get(i).getParentCount() == 1) {
                    comMap.get(comMapKey).addFileChanged(diffs.size());
                    comMap.get(comMapKey).addLinesAdded(lineAdded);
                    comMap.get(comMapKey).addLinesDeleted(lineDeleted);
                }

            }

        }

        return comMap;


    }

}
