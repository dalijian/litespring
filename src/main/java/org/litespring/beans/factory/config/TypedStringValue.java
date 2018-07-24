package org.litespring.beans.factory.config;

/*
 * bean 属性 String 对象
 */
public class TypedStringValue {
	private String value;
	public TypedStringValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
}
