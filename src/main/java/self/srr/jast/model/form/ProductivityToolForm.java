package self.srr.jast.model.form;

import lombok.Data;

@Data
public class ProductivityToolForm extends BaseForm {

    String since;

    String until;

    String repoLocalPath;

    String repoBranch;

}
