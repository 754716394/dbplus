package com.pamirs.dbplus.configure.utils;

import com.pamirs.dbplus.configure.jade.JadeContentDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class JadeLog {

    private static final Logger log = LoggerFactory.getLogger(JadeLog.class);

    private static MessageFormat logFromate;
    private static MessageFormat logFromate2;

    static {
        String logTemplate = "{0}:{1},{2} [user: {3}] [remoteIp: {4}]] [environment:{5}][groupName: {6}] [dataId: {7}]  [data: {8}]";
        logFromate = new MessageFormat(logTemplate);
        String logTemplate2 = "{0}:{1},{2},[user: {3}] [remoteIp: {4}]] [environment:{5}][info：{6}]";
        logFromate2 = new MessageFormat(logTemplate2);
    }

    public static void doLog(String operate, boolean succes, String msg, String environment, JadeContentDO jadeContentDO) {
        String nick = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                .get(PtoolsThreadLocal.ATTRIBUTE_NICK), "nobody");
        String remoteIp = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                .get(PtoolsThreadLocal.ATTRIBUTE_REMOTE_IP), "unknowIp");

        List<Object> logFormatPrams = new ArrayList<Object>(8);

        logFormatPrams.add(operate);
        logFormatPrams.add(succes);
        logFormatPrams.add(msg);
        logFormatPrams.add(nick);
        logFormatPrams.add(remoteIp);
        logFormatPrams.add(environment);
        logFormatPrams.add(jadeContentDO.getGroupName());
        logFormatPrams.add(jadeContentDO.getDataId());
        logFormatPrams.add(jadeContentDO.getContent());
        String logStr = logFromate.format(logFormatPrams.toArray());
        log.warn(logStr);
    }

    public static void doLog(String operate, boolean succes, String msg, String environment, String info) {
        String nick = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                .get(PtoolsThreadLocal.ATTRIBUTE_NICK), "nobody");
        String remoteIp = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                .get(PtoolsThreadLocal.ATTRIBUTE_REMOTE_IP), "unknowIp");

        List<Object> logFormatPrams = new ArrayList<Object>(6);

        logFormatPrams.add(operate);
        logFormatPrams.add(succes);
        logFormatPrams.add(msg);
        logFormatPrams.add(nick);
        logFormatPrams.add(remoteIp);
        logFormatPrams.add(environment);
        logFormatPrams.add(info);
        String logStr = logFromate2.format(logFormatPrams.toArray());
        log.warn(logStr);
    }

}
