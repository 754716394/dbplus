package com.pamirs.dbplus.configure.utils;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/24
 */
public interface ToolFactory {

    boolean isSingleton();

    Object createTool() throws Exception;

}
