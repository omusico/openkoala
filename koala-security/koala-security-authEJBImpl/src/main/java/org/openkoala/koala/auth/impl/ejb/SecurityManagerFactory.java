package org.openkoala.koala.auth.impl.ejb;

/**
 * JdbcManager工厂类
 * @author zyb
 * 2013-7-2 上午11:53:26
 *
 */
public final class SecurityManagerFactory {
	
	private SecurityManagerFactory() {
		
	}

	/**
	 * 获取SecurityManager的实例
	 * @param config
	 * @return
	 */
	public static SecurityManager getSecurityManager(JdbcSecurityConfig config) {
		return new SecurityManagerImpl(config);
	}
	
}