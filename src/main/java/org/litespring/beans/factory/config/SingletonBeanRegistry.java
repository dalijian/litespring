package org.litespring.beans.factory.config;

/*
 * 单例bean注册接口
 * 提供registrySingleton方法
 * 提供getSingleton方法
 */
public interface SingletonBeanRegistry {
	
	void registerSingleton(String beanName, Object singletonObject);
	
	Object getSingleton(String beanName);
}
