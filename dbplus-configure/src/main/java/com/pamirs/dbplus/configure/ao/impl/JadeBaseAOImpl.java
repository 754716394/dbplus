package com.pamirs.dbplus.configure.ao.impl;


import com.pamirs.dbplus.configure.ao.JadeBaseAO;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;

/**
 * 所有AO操作的基类
 */
public class JadeBaseAOImpl implements JadeBaseAO {

    protected JadeConfigManager jadeConfigManager;

    public JadeConfigManager getJadeConfigManager() {
        return jadeConfigManager;
    }

    public void setJadeConfigManager(JadeConfigManager jadeConfigManager) {
        this.jadeConfigManager = jadeConfigManager;
    }

}
