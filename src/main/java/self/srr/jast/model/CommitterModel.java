package self.srr.jast.model;

import lombok.Data;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Created by Sharuru on 2017/09/13.
 */
@Data
public class CommitterModel {

    String committerName;

    String committerEmail;

    int fileChanged = 0;

    int linesAdded = 0;

    int linesDeleted = 0;

    public void addFileChanged(int count) {
        this.fileChanged += count;
    }

    public void addLinesAdded(int count) {
        this.linesAdded += count;
    }

    public void addLinesDeleted(int count) {
        this.linesDeleted += count;
    }
}
