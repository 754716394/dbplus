package com.pamirs.dbplus.api.service;

import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.configure.jade.JadeAppRuleDO;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/25
 */
public interface JadeConfService {


    Result<Void> syncAppNameRule(String environment, String appNamePattern);

    ResultList<JadeAppRuleDO> getAppNameRuleFromConfigServer(String environment, String appNamePattern);

}
