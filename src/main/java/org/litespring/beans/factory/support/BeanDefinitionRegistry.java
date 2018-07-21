package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/*
 * bean注册到application上下文接口
 */
public interface BeanDefinitionRegistry {
	/*
	 * 依赖beanDefinition 从BeanDefinition中拿到bean信息
	 */
	BeanDefinition getBeanDefinition(String beanID);
	
	
	
	/*
	 * 注册bean信息到Application上下文，保存到Map集合 key为beanID，value为BeanDefinition
	 */
	void registerBeanDefinition(String beanID, BeanDefinition bd);
}
