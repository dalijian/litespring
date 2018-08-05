package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * bean annotation 的抽象 把它与 beandefinition 分离 开
 * 
 * 
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
	/**
	 * 
	 * 处理Annotation的方法接口  
	 */
	
	AnnotationMetadata getMetadata();
}
