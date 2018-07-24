package org.litespring.beans.factory.config;
/*
 * property bean属性对应的 ref类型
 */
public class RuntimeBeanReference {
	private final String beanName;
	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanName() {
		return this.beanName;
	}
}
