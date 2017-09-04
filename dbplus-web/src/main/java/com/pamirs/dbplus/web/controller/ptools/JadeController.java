package com.pamirs.dbplus.web.controller.ptools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.service.JadeConfService;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/7/6
 */

@Path("/jade")
@Controller
public class JadeController {

    private static final Logger logger = LoggerFactory.getLogger(JadeController.class);

    @Autowired
    private JadeConfigManager jadeConfigManager;

    @Autowired
    private JadeConfService jadeConfService;

    @GET
    @Path("/sync/{environment}/{app_name_pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    public String modifySchedules(@Context HttpServletRequest request,
                                  @PathParam("environment") String environment,
                                  @PathParam("app_name_pattern") String appNamePattern
    ) {
        Result<Void> result = jadeConfService.syncAppNameRule(environment, appNamePattern);

        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

}
