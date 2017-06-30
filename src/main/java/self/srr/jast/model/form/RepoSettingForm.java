package self.srr.jast.model.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Repo setting form
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Data
public class RepoSettingForm {

    @NotBlank
    @NotEmpty
    private String repoAddress;

    private String repoBranch;

    @NotBlank
    @NotEmpty
    private String repoLocalPath;

    public RepoSettingForm trim() {
        RepoSettingForm form = new RepoSettingForm();

        form.setRepoAddress(this.repoAddress.trim());
        form.setRepoBranch(this.repoBranch.trim());
        form.setRepoLocalPath(this.repoLocalPath.trim());

        return form;
    }
}
