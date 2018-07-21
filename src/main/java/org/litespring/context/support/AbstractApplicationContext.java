package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;
/*
 * 适应模板设计模式，封装算法 ，方法给客户调用
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
/*
 * 依赖DefaultBeanfactory,由于接口不能定义成员变量所以放在 模板类中定义
 */
	private DefaultBeanFactory factory = null;
	
	/*
	 * ConfigurationBean方法操作调用的属性
	 */
	private ClassLoader beanClassLoader;
	
	/*
	 * 初始化AbstractApplicationContext
	 * 传入配置文件名
	 */
	public AbstractApplicationContext(String configFile){
		factory = new DefaultBeanFactory();
		/*
		 * 调用XmlBeanDefinitionReader 解析factory
		 * 为什么要解析factory 而不直接解析configFile
		 */
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);	
		/*
		 * 由子类决定configFile 来源
		 */
		Resource resource = this.getResourceByPath(configFile);
		/*
		 * XmlBeanDefinitionReader 中的 BeanDefinitionRegistry 注册 bean信息
		 */
		reader.loadBeanDefinitions(resource);
		/*
		 * 向factory中设置ClassLoader类型
		 */
		
		factory.setBeanClassLoader(this.getBeanClassLoader());
	}
	
	public Object getBean(String beanID) {
		
		return factory.getBean(beanID);
	}
	/*
	 * 具体实现有子类决定
	 */
	protected abstract Resource getResourceByPath(String path);
	
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}

}
