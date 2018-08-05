package org.litespring.core.type.classreading;



import java.util.Map;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;



/**
 * final 定义的类不能被继承 AnnotationAttribute属性获取
 * 
 */
final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

	private final String annotationType;

/*
 * attributesMap 没有提供get方法 外部类无法调用，
 * 
 * 只能通过构造方法将此属性传递出去
 */
	private final Map<String, AnnotationAttributes> attributesMap;

	AnnotationAttributes attributes = new AnnotationAttributes();


	public AnnotationAttributesReadingVisitor(
			String annotationType, Map<String, AnnotationAttributes> attributesMap) {
		super(SpringAsmInfo.ASM_VERSION);
		
		this.annotationType = annotationType;
		this.attributesMap = attributesMap;
		
	}
	/**
	 * 读取结束后将annotation 属性注册到Map中，key是annotationtype 就是自定义的Annotation 例如Compenetion
	 * 次方法由ASM 调用  从而将属性封装到Map中
	 */
	@Override
	public final void visitEnd(){
		this.attributesMap.put(this.annotationType, this.attributes);
	}
	
	public void visit(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}


}
