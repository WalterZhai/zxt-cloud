package com.zxt.zxtcloud.common.distributedlock;

import com.zxt.zxtcloud.common.entity.JsonResult;
import com.zxt.zxtcloud.common.exception.UnimaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class LockController {

    static final Logger logger = LoggerFactory.getLogger(LockController.class);

    public JsonResult lockExceptionControllerMothed(String message) {
        return new JsonResult(new UnimaxException(message));
    }


}
