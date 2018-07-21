package org.litespring.util;


/*
 * 定义类加载器
 */
public abstract class ClassUtils {
	/*
	 * 静态方法
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		/*
		 * 默认加载器Thread.currentThread().getContextClassLoader()
		 */
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}
}
