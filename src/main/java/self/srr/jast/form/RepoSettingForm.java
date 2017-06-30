package self.srr.jast.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Repo setting form
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Data
public class RepoSettingForm extends Object {

    @NotBlank
    private String repoAddress;

    private String repoBranch;

    @NotBlank
    private String repoLocalPath;
}
