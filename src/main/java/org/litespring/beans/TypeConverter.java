package org.litespring.beans;

/*
 * 类型转换接口
 */
public interface TypeConverter {
/**
 * 类型转换方法
 * @param value 转换值
 * @param requiredType 要转换的类型
 * @return	要转换的类型
 * @throws TypeMismatchException
 */

	<T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}
