package self.srr.jast.facade;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.jast.model.form.ProductivityToolForm;
import self.srr.jast.service.GitService;

import java.io.IOException;
import java.util.Date;

/**
 * Productivity facade
 */
@Component
@Slf4j
public class ProductivityFacade {

    @Autowired
    GitService gitService;


    public void calculateProductivity(ProductivityToolForm form) throws IOException {
        RevFilter between = CommitTimeRevFilter.between(new Date(), new Date());

        gitService.getCommits(form.getPath(), form.getRefMark(), between);
    }
}
