package org.litespring.beans;

public class PropertyValue {
	/*
	 * 属性名 
	 */
	private final String name;
/*
 * 属性值
 */
	private final Object value;
/*
 *是否  从String name  转换成 真正的bean对象
 */
	private boolean converted = false;

	/*
	 * 转换的bean对象
	 */
	private Object convertedValue;
	/*
	 * <bean> <property name="helloWorld" ref="myspringbean"></property>
	 */
	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object getValue() {
		return this.value;
	}
	/*
	 * 同步锁
	 */
	public synchronized boolean isConverted() {
		return this.converted;
	}

	
	public synchronized void setConvertedValue(Object value) {
		this.converted = true;
		this.convertedValue = value;
	}
	
	public synchronized Object getConvertedValue() {
		return this.convertedValue;
	}

}