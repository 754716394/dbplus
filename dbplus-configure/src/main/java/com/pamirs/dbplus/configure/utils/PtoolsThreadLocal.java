package com.pamirs.dbplus.configure.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PtoolsThreadLocal {

	public static final String ATTRIBUTE_NICK = "ATTRIBUTE_NICK";

	public static final String ATTRIBUTE_REMOTE_IP = "ATTRIBUTE_REMOTE_IP";

	private static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

	private static final ReentrantLock lock = new ReentrantLock();

	public static void put(String key, Object value) {
		tryInit();
		THREAD_LOCAL.get().put(key, value);
	}

	public static Object get(String key) {
		tryInit();
		return THREAD_LOCAL.get().get(key);
	}

	private static void tryInit() {
		lock.lock();
		try {
			if (null == THREAD_LOCAL.get()) {
				Map<String, Object> THREAD_LOCAL_MAP = new HashMap<String, Object>();
				THREAD_LOCAL.set(THREAD_LOCAL_MAP);
			}
		} finally {
			lock.unlock();
		}
	}
}