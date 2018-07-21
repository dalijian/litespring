package org.litespring.util;

/*
 * 自定义的工具类Assert 用于判断对象==null时，抛出异常语句
 */
public abstract class Assert {
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
