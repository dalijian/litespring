package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/*
 * 将bean 的property属性 从RuntimeBeanReference,TypeStringValue 转换成对应的类型
 */
public class BeanDefinitionValueResolver {
	
	/*
	 * 依赖BeanFactory
	 */
	private final DefaultBeanFactory beanFactory;
	
	public BeanDefinitionValueResolver(
			DefaultBeanFactory beanFactory) {

		this.beanFactory = beanFactory;
	}
	
	
	/*
	 * 将RuntimeBeanReference 转换成响应的对象,现在仅支持RuntimeBeanReference和String类型
	 * 其他的不支持并抛出RuntimeException;
	 */
	public Object resolveValueIfNecessary(Object value) {
		
		
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;			
			String refName = ref.getBeanName();			
			Object bean = this.beanFactory.getBean(refName);				
			return bean;
			
		}else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else{
			//TODO
			throw new RuntimeException("the value " + value +" has not implemented");
		}		
	}
}
