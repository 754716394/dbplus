package com.pamirs.dbplus.web.velocity.utils;

import org.joda.time.DateTime;

public class ConfigUtil {
    private String assetsVersion = null;

    private DateTime serverTimestamp = new DateTime();


    public String getAssetsVersion() {
        if (null == assetsVersion) {
            assetsVersion = serverTimestamp.toString("yyyyMMddHHmmss");
        }
        return assetsVersion;
    }

}
