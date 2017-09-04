package com.pamirs.dbplus.api.model;

import java.io.Serializable;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/26
 */
public class DisplayFiledDO implements Serializable {

    private static final long serialVersionUID = -2852969681759042400L;

    private Boolean display;

    private String name;

    private String type;

    private Integer rank;

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
