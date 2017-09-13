package self.srr.jast.facade;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.jast.model.form.ProductivitySettingForm;
import self.srr.jast.service.GitService;

import java.util.Date;
import java.util.List;

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
        gitService.refreshLocalRepo(form);

        // TODO from screen
        RevFilter between = CommitTimeRevFilter.between(new Date(117, 8, 10), new Date(117, 8, 30));

        List<RevCommit> commits = gitService.getLocalCommits(form.getRepoLocalPath(), form.getRepoBranch(), between);



    }
}
