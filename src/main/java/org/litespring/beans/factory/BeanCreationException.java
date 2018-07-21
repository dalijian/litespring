package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/*
 * 自定义异常
 */
public class BeanCreationException extends BeansException {
	/*
	 * 异常名
	 */
	private String beanName;
	
	/*
	 * 自定义BeanCreateException异常， 创建bean异常
	 */
	public BeanCreationException(String msg) {
		super(msg);
		
	}
	public BeanCreationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BeanCreationException(String beanName, String msg) {
		super("Error creating bean with name '" + beanName + "': " + msg);
		this.beanName = beanName;
	}
	
	public BeanCreationException(String beanName, String msg, Throwable cause) {
		this(beanName, msg);
		initCause(cause);
	}
	public String getBeanName(){
		return this.beanName;
	}
	


}
