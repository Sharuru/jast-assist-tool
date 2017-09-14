package self.srr.jast.facade;

import groovy.util.MapEntry;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.jast.model.CommitterModel;
import self.srr.jast.model.form.ProductivitySettingForm;
import self.srr.jast.service.GitService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Productivity facade
 */
@Component
@Slf4j
public class ProductivityFacade {

    @Autowired
    GitService gitService;

    public void calculateProductivity(ProductivitySettingForm form) throws Exception {

        // check local path is legal
        gitService.refreshLocalRepo(form.getRepoLocalPath(), form.getRepoAddress(), form.getRepoBranch());

        // TODO from screen
        // last one record problem, add buffer zone ?
        Date since = new Date(117, 0, 1);
        Date until = new Date(117, 8, 30);


        RevFilter between = CommitTimeRevFilter.between(since, until);

        List<RevCommit> commits = gitService.getLocalCommits(form.getRepoLocalPath(), form.getRepoBranch(), between);

        Map<String, CommitterModel> comMap = gitService.geneCommitterMap(form.getRepoLocalPath(), commits);

        comMap.forEach((k, v) -> System.out.println(v.getCommitterName() + "," + v.getFileChanged() + "," + v.getLinesAdded() + "," + v.getLinesDeleted()));


    }
}
