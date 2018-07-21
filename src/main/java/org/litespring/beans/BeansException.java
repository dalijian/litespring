package org.litespring.beans;

/*
 * 自定义了异常 的父类
 */
public class BeansException extends RuntimeException {
	public BeansException(String msg) {
		super(msg);	}

	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
