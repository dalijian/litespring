package org.litespring.core.type;

import java.util.Set;

import org.litespring.core.annotation.AnnotationAttributes;

/**
 * 处理Annotation的方法接口
 * 
 */
public interface AnnotationMetadata extends ClassMetadata{
	/**
	 * 得到class字节码Annotation 类型
	 * @return Annotation String 集合
	 */
	Set<String> getAnnotationTypes();

/**
 * 传递一个annotation  String 判断是否是Annotation
 * 
 */
	boolean hasAnnotation(String annotationType);
	/**
	 * 通过annotationType 拿到AnnotationAttributes 例如 传递 Component 拿到 Value=“李健”
	 * @param annotationType annotation类型
	 * @return   定义annotation时的属性
	 */
	 AnnotationAttributes getAnnotationAttributes(String annotationType);
}
