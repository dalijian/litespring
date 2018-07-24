package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry 
	implements ConfigurableBeanFactory,BeanDefinitionRegistry{

	
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	private ClassLoader beanClassLoader;
	
	public DefaultBeanFactory() {
		
	}

	public void registerBeanDefinition(String beanID,BeanDefinition bd){
		this.beanDefinitionMap.put(beanID, bd);
	}
	public BeanDefinition getBeanDefinition(String beanID) {
			
		return this.beanDefinitionMap.get(beanID);
	}

	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if(bd == null){
			return null;
		}
		
		if(bd.isSingleton()){
			Object bean = this.getSingleton(beanID);
			if(bean == null){
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		} 
		return createBean(bd);
	}
	private Object createBean(BeanDefinition bd) {
		//创建实例
		Object bean = instantiateBean(bd);
		//设置属性
		populateBean(bd, bean);
		
		return bean;		
		
	}
	private Object instantiateBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {			
			throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
		}	
	}
	/*
	 * 使用java.Beans拿到bean的属性并赋值
	 */
	protected void populateBean(BeanDefinition bd, Object bean){
		List<PropertyValue> pvs = bd.getPropertyValues();
		//判断是否有属性
		if (pvs == null || pvs.isEmpty()) {
			return;
		}
		
		
		BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
		
		SimpleTypeConverter converter = new SimpleTypeConverter(); 
		try{
			//使用java.beans.Introsepector 拿到bean 的 BeanInfo 对象
			//通过BeanInfo拿到属性
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			for (PropertyValue pv : pvs){
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				//将RuntimeReference 转换成 实际的bean对象
				Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);			
				//得到属性对应的bean对象后赋值给bean
				for (PropertyDescriptor pd : pds) {
					if(pd.getName().equals(propertyName)){
						Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
						pd.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
				}
 
				
			}
		}catch(Exception ex){
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
		}	
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}
