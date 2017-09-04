package com.pamirs.dbplus.configure.jade;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class JadeContentDO implements Serializable {

    private static final long serialVersionUID = 4565583412058951414L;

    private String dataId;// dataid
    private String groupName;// 组名
    private String content;// 配置内容
    private long id;

    public JadeContentDO() {
    }

    ;

    @Override
    public String toString() {
        return "[" + dataId + "," + groupName + "," + content + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof JadeContentDO)) {
            return false;
        }
        JadeContentDO jadeContentDO = (JadeContentDO) obj;

        return StringUtils.equals(dataId, jadeContentDO.getDataId()) &&
                StringUtils.equals(groupName, jadeContentDO.getGroupName()) &&
                StringUtils.equals(content, jadeContentDO.getContent()) &&
                id == jadeContentDO.getId();
    }

    /**
     * 构造方法
     *
     * @param dataId
     * @param groupName
     * @param content
     */
    public JadeContentDO(String dataId, String groupName, String content, long id) {
        super();
        this.dataId = dataId;
        this.groupName = groupName;
        this.content = content;
        this.id = id;
    }

    /**
     * 检查是否有空值
     *
     * @return
     */
    public boolean checkHasNull() {

        if (StringUtils.isBlank(dataId))
            return true;
        if (StringUtils.isBlank(groupName))
            return true;
        return false;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
