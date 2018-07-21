package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;
/*
 * singleton具体实现类
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	/*
	 * 将singleton bean对象存放在Map中 从而达到bean的唯一性，始终只有一个bean被创建
	 * 原本的Singleton设计模式使用static 声明变量，但现在将Singleton封装成接口，将他们封装在map中达到唯一性
	 */
	
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

	/*存放 SingletonObject是个Map存放String，和javabean对象
	 * (non-Javadoc)
	 * @see org.litespring.beans.factory.config.SingletonBeanRegistry#registerSingleton(java.lang.String, java.lang.Object)
	 */
	public void registerSingleton(String beanName, Object singletonObject) {
		
		/*
		 * 判断beanName是否为空
		 */
		Assert.notNull(beanName, "'beanName' must not be null");
		
		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null) {
			throw new IllegalStateException("Could not register object [" + singletonObject +
					"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
		}
		this.singletonObjects.put(beanName, singletonObject);
		
	}
/*
 * 从map中根据beanName 拿到javaBean对象
 * (non-Javadoc)
 * @see org.litespring.beans.factory.config.SingletonBeanRegistry#getSingleton(java.lang.String)
 */
	public Object getSingleton(String beanName) {
		
		return this.singletonObjects.get(beanName);
	}

}
