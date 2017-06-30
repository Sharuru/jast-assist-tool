package self.srr.jast.model.response;

import lombok.Data;

/**
 * Basic json response
 * <p>
 * Created by Sharuru on 2017/7/1 0001.
 */
@Data
public class BaseResponse {

    boolean status;

    String message;
}
