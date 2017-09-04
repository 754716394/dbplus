package com.pamirs.dbplus.configure.jade;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class JadeMatrixDO extends JadeBaseDO {
    private String appName;
    private List<String> groupList;

    public String toJsonString() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("appName", appName);
            jsonObj.put("groupName", groupName);
            jsonObj.put("groupList", groupList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj.toString();
    }

    public JadeMatrixDO() {

    }

    public JadeMatrixDO(String appName) {
        this.appName = appName;
    }

    public void setByKeyValue(String key, String value) {
        if (key == null || value == null)
            return;
        key = key.trim();
        value = value.trim();
        super.setByKeyValue(key, value);

        if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_APPNAME)) {
            this.setAppName(value);
        }
    }

    /**
     * 得到JadeMatrixDO的content对象
     *
     * @return
     */
    public String getContent() {
        if (null != groupList) {
            StringBuffer sb = new StringBuffer();
            for (String s : groupList) {
                sb.append(s).append(",");
            }
            if (sb.length() > 0)
                return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof JadeMatrixDO))
            return false;
        JadeMatrixDO jadeMatrixDO = (JadeMatrixDO) obj;

        if ((groupList == null || groupList.isEmpty())
                && (jadeMatrixDO.getGroupList() == null || jadeMatrixDO.groupList.isEmpty())) {
            return StringUtils.equals(appName, jadeMatrixDO.getAppName())
                    && StringUtils.equals(groupName, jadeMatrixDO.getGroupName());
        }
        if ((groupList == null || groupList.isEmpty())
                || (jadeMatrixDO.getGroupList() == null || jadeMatrixDO.groupList.isEmpty())) {
            return false;
        }
        if (groupList.size() != jadeMatrixDO.groupList.size()) {
            return false;
        }

        for (int i = 0; i < groupList.size(); i++) {
            if (!StringUtils.equals(groupList.get(i), jadeMatrixDO.getGroupList().get(i)))
                return false;
        }
        return StringUtils.equals(appName, jadeMatrixDO.getAppName())
                && StringUtils.equals(groupName, jadeMatrixDO.getGroupName());

    }

    /**
     * 检查是否有空值
     *
     * @return true 含有空值，false不含有空值
     */
    public boolean checkHasNull() {
        if (StringUtils.isBlank(appName)) {
            return true;
        }
        if (StringUtils.isBlank(groupName)) {
            return true;
        }
        return false;

    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return toJsonString();
    }


}
