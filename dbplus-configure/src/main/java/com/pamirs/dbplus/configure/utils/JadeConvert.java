package com.pamirs.dbplus.configure.utils;

import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.*;
import com.pamirs.pddl.securety.PasswordCoderPamirsImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Vector;

/**
 * Jade转换工具类
 */
public class JadeConvert {

	private static final Logger log = LoggerFactory.getLogger(JadeConvert.class);

	private static PasswordCoderPamirsImpl passwordCoder = null;  
	
    public static PasswordCoderPamirsImpl getPasswordCoder() {
        if (passwordCoder == null) {    
        	passwordCoder = new PasswordCoderPamirsImpl();  
        }    
       return passwordCoder;  
   } 
	/**
	 * 从JadeDBDO对象转换成jadeContentDO对象 
	 * @param jadeDBDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeDBDO jadeDBDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if(jadeDBDO==null){
			return jadeContentDO;
		}
		jadeDBDO.setDbKey(StringUtils.replace(jadeDBDO.getDbKey(), ".", "_"));
		jadeDBDO.setDbName(StringUtils.replace(jadeDBDO.getDbName(), ".", "_"));

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(JadeConstants.JADE_GLOBAL_IP).append("=").append(StringUtils.trim(jadeDBDO.getIp())).append("\r\n");
			sb.append(JadeConstants.JADE_GLOBAL_PORT).append("=").append(StringUtils.trim(jadeDBDO.getPort())).append("\r\n");
			sb.append(JadeConstants.JADE_GLOBAL_DBNAME).append("=").append(StringUtils.trim(jadeDBDO.getDbName())).append("\r\n");
			sb.append(JadeConstants.JADE_GLOBAL_DBTYPE).append("=").append(StringUtils.trim(jadeDBDO.getDbType())).append("\r\n");
			sb.append(JadeConstants.JADE_GLOBAL_DBSTATUS).append("=").append(StringUtils.trim(jadeDBDO.getDbStatus()));
			jadeContentDO.setContent(sb.toString());// 变成字符串
			jadeContentDO.setGroupName(StringUtils.trim(jadeDBDO.getGroupName()));
			jadeContentDO.setDataId(JadeConstants.JADE_GLOBAL_DATA_ID + StringUtils.trim(jadeDBDO.getDbKey()));
			jadeContentDO.setId(jadeDBDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeDBDO.getDbKey() + ":JadeDBDO->jadeContentDO失败！" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 从JadeAppDO对象转换成jadeContentDO对象
	 * 
	 * @param jadeAppDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeAppDO jadeAppDO) {

		JadeContentDO jadeContentDO = new JadeContentDO();
		if(jadeAppDO==null){
			return jadeContentDO;
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(JadeConstants.JADE_APP_USERNAME).append("=").append(StringUtils.trim(jadeAppDO.getUserName())).append("\r\n");
			if (jadeAppDO.getMinPoolSize() != null)
				sb.append(JadeConstants.JADE_APP_MINPOOLSIZE).append("=").append(jadeAppDO.getMinPoolSize().trim())
						.append("\r\n");
			if (jadeAppDO.getMaxPoolSize() != null)
				sb.append(JadeConstants.JADE_APP_MAXPOOLSIZE).append("=").append(jadeAppDO.getMaxPoolSize().trim())
						.append("\r\n");
			if (jadeAppDO.getIdleTimeout() != null)
				sb.append(JadeConstants.JADE_APP_IDLETIMEOUT).append("=").append(StringUtils.trim(jadeAppDO.getIdleTimeout()))
						.append("\r\n");
			if (jadeAppDO.getBlockingTimeout() != null)
				sb.append(JadeConstants.JADE_APP_BLOCKINGTIMEOUT).append("=").append(jadeAppDO.getBlockingTimeout().trim())
						.append("\r\n");
			if (jadeAppDO.getPreparedStatementCacheSize() != null)
				sb.append(JadeConstants.JADE_APP_PERPAREDSTATEMENTCACHESIZE).append("=")
						.append(jadeAppDO.getPreparedStatementCacheSize().trim()).append("\r\n");
			if (jadeAppDO.getConnectionProperties() != null)
				sb.append(JadeConstants.JADE_APP_CONNECTIONPROPERTIES).append("=")
						.append(jadeAppDO.getConnectionProperties().trim()).append("\r\n");
			if (!StringUtils.isEmpty(jadeAppDO.getOracleConType())) {
				sb.append(JadeConstants.JADE_APP_ORACLECONTYPE).append("=").append(jadeAppDO.getOracleConType().trim())
						.append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getReadRestrictTimes())) {
				sb.append(JadeConstants.JADE_APP_READRESTRICTTIMES).append("=")
						.append(jadeAppDO.getReadRestrictTimes().trim()).append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getWriteRestrictTimes())) {
				sb.append(JadeConstants.JADE_APP_WRITERESTRICTTIMES).append("=")
						.append(jadeAppDO.getWriteRestrictTimes().trim()).append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getThreadCountRestrict())) {
				sb.append(JadeConstants.JADE_APP_THREADCOUNTRESTRICT).append("=")
						.append(jadeAppDO.getThreadCountRestrict().trim()).append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getTimeSliceInMillis())) {
				sb.append(JadeConstants.JADE_APP_TIMESLICEINMILLIS).append("=")
						.append(jadeAppDO.getTimeSliceInMillis().trim()).append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getMaxConcurrentReadRestrict())) {
				sb.append(JadeConstants.JADE_APP_MAXCONCURRENTREADRESTRICT).append("=")
						.append(jadeAppDO.getMaxConcurrentReadRestrict().trim()).append("\r\n");
			}
			if (!StringUtils.isEmpty(jadeAppDO.getMaxConcurrentWriteRestrict())) {
				sb.append(JadeConstants.JADE_APP_MAXCONCURRENTWRITERESTRICT).append("=")
						.append(jadeAppDO.getMaxConcurrentWriteRestrict().trim()).append("\r\n");
			}
			jadeContentDO.setContent(sb.toString());// 变成字符串
			jadeContentDO.setGroupName(StringUtils.trim(jadeAppDO.getGroupName()));

			jadeAppDO.setAppName(StringUtils.replace(StringUtils.trim(jadeAppDO.getAppName()), ".", "_"));
			jadeAppDO.setDbKey(StringUtils.replace(StringUtils.trim(jadeAppDO.getDbKey()), ".", "_"));

			jadeContentDO.setDataId(JadeConstants.JADE_APP_DATA_ID + StringUtils.trim(jadeAppDO.getAppName()) + "."
					+ StringUtils.trim(jadeAppDO.getDbKey()));
			jadeContentDO.setId(jadeAppDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeAppDO.getDbKey() + ":JadeAppDO->jadeContentDO" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 从jadeContentDO对象转换成jadeDBDO对象
	 * 
	 * @param jadeContentDO
	 * @return
	 */
	public static JadeDBDO toDBDO(JadeContentDO jadeContentDO) {
		
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}

		JadeDBDO jadeDBDO = new JadeDBDO();
		try {
			String[] res = jadeContentDO.getContent().trim().split("\r\n");
			for (String s : res) {
				int sp = s.indexOf("=");
				String key = s.substring(0, sp);
				String value = s.substring(sp + 1);
				jadeDBDO.setByKeyValue(key, value);
			}
			jadeDBDO.setDbKey(jadeContentDO.getDataId().substring(JadeConstants.JADE_GLOBAL_DATA_ID.length()));// 转换成名字简称
			jadeDBDO.setId(jadeContentDO.getId());
			jadeDBDO.setGroupName(jadeContentDO.getGroupName());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId() + ":jadeContentDO->JadeDBDO失败。" + e.getMessage());
		}
		return jadeDBDO;
	}

	public static JadeAppDO toAppDO(JadeContentDO jadeContentDO) {
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}
		JadeAppDO jadeAppDO = new JadeAppDO();
		try {
			String[] res = jadeContentDO.getContent().trim().split("\r\n");
			for (String s : res) {
				int sp = s.indexOf("=");
				String key = s.substring(0, sp);
				String value = s.substring(sp + 1);
				jadeAppDO.setByKeyValue(key, value);
			}
			String stemp = jadeContentDO.getDataId().substring(JadeConstants.JADE_APP_DATA_ID.length());
			String[] sp = stemp.split("\\.");
			jadeAppDO.setAppName(sp[0]);
			jadeAppDO.setDbKey(sp[1]);
			jadeAppDO.setGroupName(jadeContentDO.getGroupName());
			jadeAppDO.setId(jadeContentDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId() + ":jadeContentDO->JadeAppDo失败！" + e.getMessage());
		}
		return jadeAppDO;
	}

	/**
	 * 从jadeBadeDO对象转换成jadeUserDO对象
	 * 
	 * @param jadeContentDO
	 * @return
	 */
	public static JadeUserDO toUserDO(JadeContentDO jadeContentDO) {
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}
		JadeUserDO jadeUserDO = new JadeUserDO();
		String[] res = jadeContentDO.getContent().trim().split("\r\n");
		try {
			String encPasswd = null;
			for (String s : res) {
				int sp = s.indexOf("=");
				String key = s.substring(0, sp);
				String value = s.substring(sp + 1);
				if (StringUtils.equals(key, JadeConstants.JADE_USER_KEY_ENCPASSWD)) {
					encPasswd = value;
				}
				jadeUserDO.setByKeyValue(key, value);
			}
			try {
				PasswordCoderPamirsImpl passwordCoder = getPasswordCoder();
				if (jadeUserDO.getEncKey() != null && !jadeUserDO.getEncKey().trim().equals("")) {

					jadeUserDO.setPassword(passwordCoder.decode(jadeUserDO.getEncKey(), encPasswd));
				} else {
					// SecureIdentityLoginModule.decode( secret)
					String pwd = passwordCoder.decode(encPasswd);
					jadeUserDO.setPassword(pwd);
				}
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String tempstr = jadeContentDO.getDataId().trim().substring(JadeConstants.JADE_USER_DATA_ID.length());
			String[] sp = tempstr.trim().split("\\.");
			jadeUserDO.setDbName(sp[0].trim());// 数据库名
			jadeUserDO.setDbType(sp[1].trim());// 数据库类型
			jadeUserDO.setUserName(sp[2].trim());// 用户名
			jadeUserDO.setGroupName(jadeContentDO.getGroupName().trim());
			jadeUserDO.setId(jadeContentDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId() + "：jadeContentDO->JadeUserDO失败" + e.getMessage());
		}
		return jadeUserDO;
	}

	/**
	 * 从
	 * 
	 * @param jadeUserDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeUserDO jadeUserDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if(jadeUserDO==null){
			return jadeContentDO;
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(JadeConstants.JADE_USER_KEY_ENCPASSWD).append("=").append(jadeUserDO.getEncPasswd());
			if (jadeUserDO.getEncKey() != null && !jadeUserDO.getEncKey().trim().equals("")) {
				sb.append("\r\n").append(JadeConstants.JADE_USER_KEY_ENCKEY).append("=").append(StringUtils.trim(jadeUserDO.getEncKey()));
			}
			jadeContentDO.setContent(sb.toString());

			jadeUserDO.setDbName(StringUtils.replace(StringUtils.trim(jadeUserDO.getDbName()), ".", "_"));
			jadeUserDO.setDbType(StringUtils.replace(StringUtils.trim(jadeUserDO.getDbType()), ".", "_"));
			jadeUserDO.setUserName(StringUtils.replace(StringUtils.trim(jadeUserDO.getUserName()), ".", "_"));

			jadeContentDO.setDataId(JadeConstants.JADE_USER_DATA_ID + StringUtils.trim(jadeUserDO.getDbName()) + "."
					+ StringUtils.trim(jadeUserDO.getDbType()) + "." +StringUtils.trim( jadeUserDO.getUserName())); // 构建dataId
			jadeContentDO.setGroupName(StringUtils.trim(jadeUserDO.getGroupName()));
			jadeContentDO.setId(jadeUserDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeUserDO.getUserName() + ":JadeUserDO->jadeContentDO失败!" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 把JadeGroupDO类型的对象转换成jadeContentDO类型
	 * 
	 * @param jadeGroupDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeGroupDO jadeGroupDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if(jadeGroupDO==null){
			return jadeContentDO;
		}
		try {
			List<GroupItem> list = jadeGroupDO.getGroupItems();
			StringBuffer sb = new StringBuffer();
			if (list != null) {
				for (GroupItem li : list) {

					sb.append(li.getDb()).append(":");
					sb.append("r").append(li.getR());
					sb.append("w").append(li.getW());
					sb.append("p").append(li.getP());
					sb.append(",");
				}
			}
			jadeGroupDO.setGroupKey(StringUtils.replace(StringUtils.trim(jadeGroupDO.getGroupKey()), ".", "_"));
			jadeContentDO.setDataId(JadeConstants.JADE_GROUP_DATA_ID + StringUtils.trim(jadeGroupDO.getGroupKey()));
			jadeContentDO.setGroupName(StringUtils.trim(jadeGroupDO.getGroupName()));
			if (sb.length() > 0)
				jadeContentDO.setContent(sb.substring(0, sb.length() - 1));
			jadeContentDO.setId(jadeGroupDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeGroupDO.getGroupKey() + ":JadeGroupDO->jadeContentDO失败!" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 把基本类型转换成JadeGroupDO类型
	 * 
	 * @param jadeContentDO
	 * @return
	 */
	public static JadeGroupDO toGroupDO(JadeContentDO jadeContentDO) {
		JadeGroupDO jadeGroupDO = new JadeGroupDO();
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}
		try {
			jadeGroupDO.setGroupKey(jadeContentDO.getDataId().trim().substring(JadeConstants.JADE_GROUP_DATA_ID.length()));
			jadeGroupDO.setGroupName(jadeContentDO.getGroupName().trim());
			if (jadeContentDO.getContent() == null)
				return jadeGroupDO;
			String res[] = jadeContentDO.getContent().trim().split(",");
			List<GroupItem> groupItems = new Vector<GroupItem>();
			for (int i = 0; i < res.length; i++) {
				String rs[] = res[i].trim().split(":");
				GroupItem groupItem = new GroupItem(0, 0, 0, null);
				groupItem.setIndex(i);
				groupItem.setDb(rs[0].trim());
				if (rs.length < 2) {
					groupItems.add(groupItem);
					continue;
				}
				String s = null;
				s = rs[1].trim();
				long rWeight = JadeConvert.getWeight(s, 'r');
				groupItem.setR(rWeight);
				long wWeight = JadeConvert.getWeight(s, 'w');
				groupItem.setW(wWeight);
				long pWeight = JadeConvert.getWeight(s, 'p');
				groupItem.setP(pWeight);
				groupItems.add(groupItem);
			}
			jadeGroupDO.setGroupItems(groupItems);
			jadeGroupDO.setId(jadeContentDO.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId() + ":jadeContentDO->JadeGroupDO失败！" + e.getMessage());
		}
		return jadeGroupDO;
	}

	/**
	 * JadeMatridDO 转换成jadeContentDO
	 * 
	 * @param jadeMatrixDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeMatrixDO jadeMatrixDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if (null==jadeMatrixDO) {
			return jadeContentDO;
		}
		try {
			StringBuffer sb = new StringBuffer();
			List<String> list = jadeMatrixDO.getGroupList();
			if (list != null) {
				for (String s : list) {
					sb.append(s).append(",");
				}
			}
			jadeMatrixDO.setAppName(StringUtils.replace(StringUtils.trim(jadeMatrixDO.getAppName()), ".", "_"));
			jadeContentDO.setDataId(JadeConstants.JADE_MATRIX_DATA_ID + StringUtils.trim(jadeMatrixDO.getAppName())
					+ JadeConstants.JADE_MATRIX_DATA_ID_TAIL);
			jadeContentDO.setGroupName(StringUtils.trim(jadeMatrixDO.getGroupName()));
			if (sb.length() > 0)
				jadeContentDO.setContent(sb.substring(0, sb.length() - 1));
			jadeContentDO.setId(jadeMatrixDO.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeMatrixDO.getAppName() + "：JadeMatrixDO->jadeContentDO失败！" + e.getMessage());
		}
		return jadeContentDO;
	}


	/**
	 * JadeAppNameDO 转换成jadeContentDO
	 * 
	 * @param jadeAppNameDO
	 * @return
	 */
	public static JadeContentDO toAppNameDO(JadeAppNameDO jadeAppNameDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if (null==jadeAppNameDO) {
			return jadeContentDO;
		}
		try {
			StringBuffer sb = new StringBuffer();
			List<String> list = jadeAppNameDO.getGroupList();
			if (list != null) {
				for (String s : list) {
					sb.append(s).append(",");
				}
			}
			jadeAppNameDO.setAppName(StringUtils.replace(
					StringUtils.trim(jadeAppNameDO.getAppName()), ".", "_"));
			jadeContentDO.setDataId(JadeConstants.JADE_APPNAME_OPSFREENAME_DATA_ID
					+ StringUtils.trim(jadeAppNameDO.getAppName())+"."
					+ StringUtils.trim(jadeAppNameDO.getOpsfreeName()));
			jadeContentDO.setGroupName(StringUtils.trim(jadeAppNameDO
					.getGroupName()));
			if (sb.length() > 0)
				jadeContentDO.setContent(sb.substring(0, sb.length() - 1));
			jadeContentDO.setId(jadeAppNameDO.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeAppNameDO.getAppName()
					+ "：JadeAppNameDO->jadeContentDO失败！" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 
	 * @param jadeAppSingleRuleDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeAppSingleRuleDO jadeAppSingleRuleDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if (null==jadeAppSingleRuleDO) {
			return jadeContentDO;
		}
		try {
			jadeContentDO.setContent(StringUtils.trim(jadeAppSingleRuleDO.getRuleStr()));
			jadeAppSingleRuleDO.setAppName(StringUtils.replace(StringUtils.trim(jadeAppSingleRuleDO.getAppName()), ".", "_"));
			String dataId=new MessageFormat(JadeConstants.JADE_RULE_LE_DATA_ID_FORMAT).format(new Object[]{StringUtils.trim(jadeAppSingleRuleDO.getAppName()),StringUtils.trim(jadeAppSingleRuleDO.getVersion())});
			jadeContentDO.setDataId(dataId);
			jadeContentDO.setGroupName(StringUtils.trim(jadeAppSingleRuleDO.getGroupName()));
			jadeContentDO.setId(jadeAppSingleRuleDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeAppSingleRuleDO.getAppName() + ":JadeParallelDO->jadeContentDO失败！" + e.getMessage());
		}
		return jadeContentDO;
	}
	
	/**
	 * 
	 * @param jadeAppRuleVersionDO
	 * @return
	 */
	public static JadeContentDO toBaseDO(JadeAppRuleVersionDO jadeAppRuleVersionDO) {
		JadeContentDO jadeContentDO = new JadeContentDO();
		if (null==jadeAppRuleVersionDO) {
			return jadeContentDO;
		}
		try {
			jadeContentDO.setContent(StringUtils.trim(jadeAppRuleVersionDO.getVersionStr()));
			jadeAppRuleVersionDO.setAppName(StringUtils.replace(StringUtils.trim(jadeAppRuleVersionDO.getAppName()), ".", "_"));
			String dataId=new MessageFormat(JadeConstants.JADE_RULE_LE_VERSIONS_DATA_ID_FORMAT).format(new Object[]{StringUtils.trim(jadeAppRuleVersionDO.getAppName())});
			jadeContentDO.setDataId(dataId);
			jadeContentDO.setGroupName(StringUtils.trim(jadeAppRuleVersionDO.getGroupName()));
			jadeContentDO.setId(jadeAppRuleVersionDO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeAppRuleVersionDO.getAppName() + ":JadeParallelDO->jadeContentDO失败！" + e.getMessage());
		}
		return jadeContentDO;
	}

	/**
	 * 从jadeContentDO转换成JadeMatrixDO
	 * 
	 * @param jadeContentDO
	 * @return
	 */
	public static JadeMatrixDO toMatrixDO(JadeContentDO jadeContentDO) {
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}
		JadeMatrixDO jadeMatrixDO = new JadeMatrixDO();
		try {
			jadeMatrixDO.setAppName(jadeContentDO.getDataId().trim().substring(JadeConstants.JADE_MATRIX_DATA_ID.length(),
					jadeContentDO.getDataId().trim().indexOf(JadeConstants.JADE_MATRIX_DATA_ID_TAIL)));
			jadeMatrixDO.setGroupName(jadeContentDO.getGroupName().trim());
			if (StringUtils.isBlank(jadeContentDO.getContent()))
				return jadeMatrixDO;
			String sp[] = jadeContentDO.getContent().trim().split(",");
			List<String> list = new Vector<String>();
			for (String s : sp) {
				list.add(s);
			}
			jadeMatrixDO.setGroupList(list);
			jadeMatrixDO.setId(jadeContentDO.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId() + "：jadeContentDO->JadeMatrixDO失败！" + e.getMessage());
		}
		return jadeMatrixDO;
	}

	/**
	 * 从jadeContentDO转换成JadeAppNameDO
	 * 
	 * @param jadeContentDO
	 * @return
	 */
	public static JadeAppNameDO toAppNameDO(JadeContentDO jadeContentDO) {
		if (null==jadeContentDO||jadeContentDO.getContent() == null) {
			return null;
		}
		JadeAppNameDO jadeAppNameDO = new JadeAppNameDO();
		try {
			String dataId=jadeContentDO.getDataId().trim();
			String appNameAndOpsFreeName=dataId.substring(JadeConstants.JADE_APPNAME_OPSFREENAME_DATA_ID.length());
			
			String[] pieces=appNameAndOpsFreeName.split("\\.");
			jadeAppNameDO.setAppName(pieces[0]);
			jadeAppNameDO.setOpsfreeName(pieces[1]);
			
			jadeAppNameDO
					.setGroupName(jadeContentDO.getGroupName() == null ? JadeConstants.JADE_DEFAULT_GROUP_NAME
							: jadeContentDO.getGroupName().trim());
			if (StringUtils.isBlank(jadeContentDO.getContent()))
				return null;
			String sp[] = jadeContentDO.getContent().trim().split(",");
			List<String> list = new Vector<String>();
			for (String s : sp) {
				list.add(s);
			}
			jadeAppNameDO.setGroupList(list);
			jadeAppNameDO.setId(jadeContentDO.getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.warn(jadeContentDO.getDataId()
					+ "：jadeContentDO->JadeAppNameDO失败！" + e.getMessage());
			return jadeAppNameDO;
		}
		return jadeAppNameDO;
	}
	
	/**
	 * 
	 * 解析字符串中的r,w值
	 * 
	 * @param s
	 * @param ch
	 * @return
	 */
	public static long getWeight(String s, char ch) {
		int index = s.indexOf(ch);
		if (index == -1) // 如果没有这个字母。则返回-1，
			return 0;
		int len = s.length();
		if (index + 1 == len)
			return 10;// 表示有这个值，但是取值位空
		if (!Character.isDigit(s.charAt(index + 1)))
			return 10;
		int i;
		for (i = index + 1; i < len && Character.isDigit(s.charAt(i)); i++) {
			;
		}
		long retv = Long.valueOf(s.substring(index + 1, i));
		return retv;
	}

}
