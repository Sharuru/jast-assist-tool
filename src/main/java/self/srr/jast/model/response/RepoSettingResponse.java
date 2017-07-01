package self.srr.jast.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RepoSettingResponse
 * <p>
 * Created by Sharuru on 2017/7/1 0001.
 */
@Data
@EqualsAndHashCode(callSuper = true)

public class RepoSettingResponse extends BaseResponse {

    String repoAddress;

    String repoBranch;

    String repoLocalPath;
}
