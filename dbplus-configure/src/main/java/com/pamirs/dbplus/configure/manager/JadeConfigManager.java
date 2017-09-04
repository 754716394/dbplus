package com.pamirs.dbplus.configure.manager;

import com.pamirs.configure.sdkapi.ConfigureSDKManager;
import com.pamirs.dbplus.configure.jade.JadeConfigureConfig;
import com.pamirs.dbplus.configure.jade.JadeContentDO;
import com.pamirs.dbplus.configure.jade.JadeContentResult;
import com.pamirs.dbplus.configure.jade.JadePageResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Jade的管理接口
 */
public interface JadeConfigManager {

	/**
	 * 取得configure服务器地址
	 * 
	 * @param environment   对应的环境
	 * @return
	 */
	public String getConfigureIp(String environment);

	/**
	 * 分页模糊查询配置对象
	 * 
	 * @param dataIdPattern
	 *            dataId查询匹配串
	 * @param groupNamePattern
	 *            groupName查询匹配串
	 * @param environment
	 *            对应的环境
	 * @param currentPage
	 *            当前页号
	 * @param sizeOfPerPage
	 *            每页大小
	 * @return
	 * @throws SQLException
	 */
	public JadePageResult<JadeContentDO> queryBy(String dataIdPattern,
												 String groupNamePattern, String environment, long currentPage,
												 long sizeOfPerPage);

	public JadePageResult<JadeContentDO> queryBy(String dataIdPattern,
                                                 String groupNamePattern, String contentPattern, String environment,
                                                 long currentPage, long sizeOfPerPage);

	/**
	 * 精确查询
	 * 
	 * @param dataId
	 *            dataId
	 * @param groupName
	 *            groupName
	 * @param environment
	 *            对应的环境
	 * @return
	 * @throws SQLException
	 */
	public JadeContentResult<JadeContentDO> queryByDataIdAndGroupName(
            String dataId, String groupName, String environment);

	/**
	 * 推送配置数据
	 * 
	 * @param jadeContentDO
	 *            基本配置类型
	 * @param environment
	 *            推送到的环境
	 * @return
	 */
	JadeContentResult<JadeContentDO> publish(JadeContentDO jadeContentDO,
                                             String environment);

	/**
	 * 重新推送配置
	 * 
	 * @param jadeContentDO
	 *            基本配置类型
	 * @param environment
	 *            推送到的环境
	 * @return
	 */
	JadeContentResult<JadeContentDO> publishAfterModified(
            JadeContentDO jadeContentDO, String environment);

	/**
	 * 直接根据其在数据库中的id进行删除
	 * 
	 * @param dataId
	 * @param groupName
	 * @param environment
	 * @return
	 */
	JadeContentResult<JadeContentDO> remove(String dataId, String groupName,
											String environment);

	/**
	 * get Configure Server Environment Setting
	 * 
	 * @return
	 */
	public Map<String, JadeConfigureConfig> getJadeConfigureConfigMap();

	/**
	 * get ConfigureSDKManager for some more work easy
	 * 
	 * @return
	 */
	public ConfigureSDKManager getConfigureSDKManager(String env);

	/**
	 * @return
	 */
	public Map<String, String> getServerEnvironments();

	/**
	 * @param groupName
	 * @param dataIds
	 *            想要批量查询的dataId List。
	 * @param envId
	 * @return
	 */
	public Map<String,JadeContentResult<JadeContentDO>> batchQuery(String groupName,
                                                                   List<String> dataIds, String envId);

	/**
	 * @param jadeContentDOs
	 * @param envId
	 * @param groupName
	 * @return
	 */
	public Map<String, JadeContentResult<JadeContentDO>> batchCreateOrUpdate(
            List<JadeContentDO> jadeContentDOs, String envId, String groupName);

}
