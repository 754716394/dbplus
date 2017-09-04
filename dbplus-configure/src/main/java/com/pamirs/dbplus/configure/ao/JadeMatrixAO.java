package com.pamirs.dbplus.configure.ao;


import com.pamirs.dbplus.configure.jade.JadeContentResult;
import com.pamirs.dbplus.configure.jade.JadeMatrixDO;
import com.pamirs.dbplus.configure.jade.JadeMoveResult;
import com.pamirs.dbplus.configure.jade.JadePageResult;

import java.util.List;
import java.util.Map;

public interface JadeMatrixAO extends JadeBaseAO{
	
	public JadeContentResult<JadeMatrixDO> publish(JadeMatrixDO jadeMatrixDO,
												   String environment);

	public JadeContentResult<JadeMatrixDO> publishAfterModified(
            JadeMatrixDO jadeMatrixDO, String environment);

	public JadeContentResult<JadeMatrixDO> query(String appName,
                                                 String groupName, String environment);

	public JadePageResult<JadeMatrixDO> pagequery(String appNamePattern,
												  String groupNamePattern, String environment, long currentPage,
												  long sizeOfPerPage);
	public List<JadeMoveResult> move(String fromEnvironment,
                                     String targetEnvironment, String groupName, boolean overwrite,
                                     String... appNames);
	
	public JadeContentResult<JadeMatrixDO> remove(String appName,
                                                  String groupName, String environment);
	
	/**批量修改
	 * @param jadeDOs
	 * @param envId
	 * @param groupName
	 * @return
	 */
	public Map<String, JadeContentResult<JadeMatrixDO>> batchCreateOrUpdate(
            List<JadeMatrixDO> jadeDOs, String envId, String groupName);

	/**批量查询
	 * @param groupName
	 * @param dataIds
	 * @param envId
	 * @return
	 */
	public Map<String /*appName*/, JadeContentResult<JadeMatrixDO>> batchQuery(
            String groupName, List<String> dataIds, String envId);
	
	/**批量迁移环境
	 * @param fromEnvironment
	 * @param targetEnvironment
	 * @param groupName
	 * @param overwrite
	 * @param keys
	 * @return
	 */
	public List<JadeMoveResult> moveByBatch(String fromEnvironment,
											String targetEnvironment, String groupName, boolean overwrite,
											String... keys);

	/**根据groupKey查询Matrix
	 * @param content
	 * @param groupName
	 * @param environment
	 * @param currentPage
	 * @param sizeOfPerPage
	 * @return
	 */
	public JadePageResult<JadeMatrixDO> pagequeryByContent(String content,
                                                           String groupName, String environment, long currentPage,
                                                           long sizeOfPerPage);

	/**
	 * @param dataId
	 * @return
	 */
	public String fromDataId2AppName(String dataId);

}

