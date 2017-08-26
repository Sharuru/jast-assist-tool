package self.srr.jast.model.response;

import lombok.Data;

/**
 * Basic json response
 */
@Data
public class BaseResponse {

    boolean status = true;

    String message = "Success";
}
