package self.srr.jast.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RepoSettingResponse
 */
@Data
@EqualsAndHashCode(callSuper = true)

public class RepoSettingResponse extends BaseResponse {

    String repoAddress;

    String repoBranch;

    String repoLocalPath;
}
