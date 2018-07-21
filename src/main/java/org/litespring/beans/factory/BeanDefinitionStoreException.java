package org.litespring.beans.factory;

import org.litespring.beans.BeansException;
/*
 * 自定义BeanDefinitionStoreException 读取xml文件时抛出异常
 */
public class BeanDefinitionStoreException extends BeansException {

	public BeanDefinitionStoreException(String msg, Throwable cause) {
		super(msg, cause);
		
	}
	
}
