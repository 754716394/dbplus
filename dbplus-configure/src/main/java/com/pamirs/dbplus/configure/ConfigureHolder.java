package com.pamirs.dbplus.configure;

import com.pamirs.dbplus.configure.ao.JadeMatrixAO;
import com.pamirs.dbplus.configure.jade.JadeContentResult;
import com.pamirs.dbplus.configure.jade.JadeMatrixDO;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/22
 */
public class ConfigureHolder {

    static private JadeMatrixAO jadeMatrixAO;

    public static void main(String[] args) {

        JadeContentResult<JadeMatrixDO> result = jadeMatrixAO.query("", "", "online");


    }

    public static void setJadeMatrixAO(JadeMatrixAO jadeMatrixAO) {
        ConfigureHolder.jadeMatrixAO = jadeMatrixAO;
    }
}
