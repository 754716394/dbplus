package com.pamirs.dbplus.web.velocity.utils;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class TemplateUtil {
    private static String rootPath = "/vmcommon";

    public String setTemplate(String filePath) throws IOException {
        return FileUtils.readFileToString(FileUtils.getFile(TemplateUtil.rootPath, filePath));
    }

    public String getRootPath() {
        return TemplateUtil.rootPath;
    }

    public void setRootPath(String rootPath) {
        TemplateUtil.rootPath = rootPath;
    }


}
