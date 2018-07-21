package org.litespring.beans;

/*
 * 与bean相关信息的接口
 */
public interface BeanDefinition {
	/*
	 * 单例设置模式 scope 作用域 设置成两种 singleton，prototype 
	 */
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";
	public static final String SCOPE_DEFAULT = "";
	
	/*
	 *判断是否是singleton，prototype
	 */
	public boolean isSingleton();
	public boolean isPrototype();
	
	/*
	 * 得到scope
	 */
	String getScope();
	
	/*
	 * 设置scope
	 */
	void setScope(String scope);
	
	/*
	 * 得到beanName
	 */
	public String getBeanClassName();
}
