package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

/*
 *  实现bean工厂
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements ConfigurableBeanFactory, BeanDefinitionRegistry {
	/*
	 * map 来存放 所有 bean 的信息 BeanDefinition 
	 * 包括  scope 和BeanClassName
	 */

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	/*
	 * 存放类加载器
	 */
	private ClassLoader beanClassLoader;

	public DefaultBeanFactory() {

	}

	/*
	 * 将bean信息注册到ApplicationContext中 (non-Javadoc)
	 * 
	 * @see org.litespring.beans.factory.support.BeanDefinitionRegistry#
	 * registerBeanDefinition(java.lang.String,
	 * org.litespring.beans.BeanDefinition)
	 */
	public void registerBeanDefinition(String beanID, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanID, bd);
	}

	/*
	 * 得到根据beanID得到bean信息 (non-Javadoc)
	 * 
	 * @see org.litespring.beans.factory.support.BeanDefinitionRegistry#
	 * getBeanDefinition(java.lang.String)
	 */
	public BeanDefinition getBeanDefinition(String beanID) {

		return this.beanDefinitionMap.get(beanID);
	}

	/*
	 * 拿到bean 实例化对象
	 *  (non-Javadoc)
	 * 
	 * @see org.litespring.beans.factory.BeanFactory#getBean(java.lang.String)
	 */
	public Object getBean(String beanID) {
		/*
		 * 根据BeanID判断是否有对应BeanDefinition信息
		 */
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if (bd == null) {
			return null;
		}
		/*
		 * 单例设计模式中的懒汉设计模式
		 */
		if (bd.isSingleton()) {
			Object bean = this.getSingleton(beanID);//从getSingleton 中拿到bean实例
			if (bean == null) {
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		}
		return createBean(bd);
	}
/*
 * 生成bean的方法
 */
	private Object createBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
		}
	}

	/*实现ConfigurableBeanFactory方法
	 * (non-Javadoc)
	 * @see org.litespring.beans.factory.config.ConfigurableBeanFactory#setBeanClassLoader(java.lang.ClassLoader)
	 */
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}
