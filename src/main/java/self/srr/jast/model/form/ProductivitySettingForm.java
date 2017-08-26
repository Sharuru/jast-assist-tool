package self.srr.jast.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Repo setting form
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductivitySettingForm extends BaseForm {

    @NotBlank
    @NotEmpty
    private String repoAddress;

    private String repoBranch;

    @NotBlank
    @NotEmpty
    private String repoLocalPath;

    public ProductivitySettingForm trim() {
        ProductivitySettingForm form = new ProductivitySettingForm();

        form.setRepoAddress(this.repoAddress.trim());
        form.setRepoBranch(this.repoBranch.trim());
        form.setRepoLocalPath(this.repoLocalPath.trim());

        return form;
    }
}
