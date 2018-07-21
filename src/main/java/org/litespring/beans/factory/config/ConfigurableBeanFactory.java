package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/*
 * bean 配置工厂 提供classLoader ,将方法从BeanFactory中提取出来，
 * 影藏方法，使方法不被客户调用
 */
public interface ConfigurableBeanFactory extends BeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}
